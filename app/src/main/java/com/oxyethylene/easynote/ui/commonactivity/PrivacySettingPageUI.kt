package com.oxyethylene.easynote.ui.commonactivity

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.drake.net.utils.TipUtils
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.common.constant.DEFAULT_LOCATION
import com.oxyethylene.easynote.domain.DialogSetting
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.LightBlue
import com.oxyethylene.easynote.ui.theme.Tomato
import com.oxyethylene.easynote.util.SettingUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : PrivacySettingPageUI.java
 * @createTime   : 2024/4/20 14:40
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Composable
fun PrivacySettingPageUI () {

    // 是否显示选择定位功能相关的设置项
    var visible by remember { mutableStateOf(SettingUtil.enableLocation) }

    val density = LocalDensity.current

    val context = LocalContext.current

    Column {

        SimpleTitleBar("隐私类功能")

        Column (Modifier.verticalScroll(rememberScrollState())) {

            Text(
                text = "请注意，Android 12 及以上的设备需要授予获取设备精确位置的权限\n否则定位功能无法正常使用(不影响应用运行)\n具体请前往应用的权限设置中进行授权",
                color = Tomato,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 13.sp,
                modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp)
            )

            SettingSubList(
                listOf(
                    SwitchSetting (
                        settingName = "文章关联定位信息",
                        state = SettingUtil.enableLocation,
                        onValueChanged = {
                            SettingUtil.enableLocation = it
                            visible = it
                        },
                        description = "开启后文章将自动关联最后一次编辑时您的位置",
                        warning = "获取位置时需要使用网络和设备定位功能\n且关闭后不会继续记录位置信息"
                    )
                )
            )

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically { with(density) { -25.dp.roundToPx() } } // 从顶部 40dp 的地方开始滑入
                        + expandVertically(expandFrom = Alignment.Top)  // 从顶部开始展开
                        + fadeIn(initialAlpha = 0.3f),   // 从初始透明度 0.3f 开始淡入
                exit = shrinkVertically() + fadeOut()
            ) {

                Column {

                    TestLocationBlockUI()

                    SettingSubList(
                        listOf(
                            DialogSetting(
                                settingName = "设置默认地址",
                                description = "当因为网络等原因未能定位成功时，将会优先使用默认地址作为记录"
                            ) {
                                val sb = StringBuffer()
                                if (SettingUtil.defaultLocation == DEFAULT_LOCATION) {
                                    sb.append("当前未设置默认地址\n")
                                } else {
                                    sb.append("当前默认地址为\n${SettingUtil.defaultLocation}\n")
                                }
                                sb.append("是否修改?")
                                MessageDialog.build(MIUIStyle())
                                    .setTitle("设置默认地址")
                                    .setMessage(sb.toString())
                                    .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                                    .setCancelButton("取消")
                                    .setOkButton("修改") {
                                            _, _ ->
                                        InputDialog.build(MIUIStyle())
                                            .setTitle("修改默认地址")
                                            .setInputText(SettingUtil.defaultLocation)
                                            .setCancelButton("取消")
                                            .setOkButton("确认修改")
                                            .setOkButtonClickListener {
                                                    _, _, input ->
                                                SettingUtil.defaultLocation = input
                                                return@setOkButtonClickListener false
                                            }
                                            .show()
                                        return@setOkButton false
                                    }
                                    .show()
                            }
                        )
                    )

                }

            }

        }

    }

}

/**
 * 测试定位功能的界面
 */
@Composable
fun TestLocationBlockUI () {

    val context = LocalContext.current

    var mLocationClient: LocationClient? = null

    var testLocation by remember { mutableStateOf("") }

    var testDescription by remember { mutableStateOf("") }

    try {
        mLocationClient = LocationClient(context)
    } catch (ex : Exception) {
        TipUtils.toast(ex.message)
    }

    val option = LocationClientOption().apply {
        setIsNeedAddress(true)
    }

    mLocationClient?.locOption = option

    mLocationClient?.registerLocationListener(
        object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                testLocation = location?.addrStr ?: "定位失败"
                if (location?.addrStr != null) {
                    testDescription = "定位成功"
                }
                mLocationClient.stop()
            }

            override fun onLocDiagnosticMessage(p0: Int, p1: Int, p2: String?) {

                testDescription = "$p0 $p1 $p2\n" + when (p0) {

                    61 -> "卫星定位成功"

                    62 -> when (p1) {
                        4 -> "定位失败，无法获取任何有效定位依据"
                        5 -> "定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位"
                        6 -> "定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试"
                        7 -> "定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试"
                        9 -> "定位失败，无法获取任何有效定位依据"
                        else -> "未知错误，请联系开发者"
                    }

                    63 -> "网络异常，请确认当前测试手机网络是否通畅，尝试重新请求定位"

                    69 -> "定位失败，请检查定位服务开关是否打开"

                    70 -> "定位失败，请检查是否授予定位权限"

                    71 -> "定位失败，请检查定位服务开关是否打开，以及是否授予定位权限"

                    161 -> when (p1) {
                        1 -> "网络定位成功，建议您打开卫星定位"
                        2 -> "网络定位成功，建议您打开Wi-Fi"
                        else -> "未知错误，请联系开发者"
                    }

                    167 -> "定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限"

                    else -> "未知错误，请联系开发者"

                }
            }
        }
    )

    Column (
        Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {

        Text(
            text = "建议先单击下方按钮测试当前设备是否支持开启本应用的定位功能\n定位失败也不影响应用的使用，但是不推荐继续开启定位功能",
            color = Color.Gray,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
        )

        Column (
            Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(GreyLighter.copy(alpha = 0.7f))
        ) {

            Text (
                text = "定位结果",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 10.dp)
            )

            Text(
                text = testLocation,
                fontSize = 12.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
            )

        }

        Column (
            Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(GreyLighter.copy(alpha = 0.7f))
        ) {

            Text (
                text = "测试结果说明",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 10.dp)
            )

            Text(
                text = testDescription,
                fontSize = 12.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
            )

        }

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(bottom = 20.dp),
            onClick = {
                mLocationClient?.start()
            }
        ) {
            Text("  测试定位  ", color = Color.White)
        }


    }

}