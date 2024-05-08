package com.oxyethylene.easynote.ui.settingactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.common.arrays.mainSettingList
import com.oxyethylene.easynote.domain.DialogSetting
import com.oxyethylene.easynote.domain.DropDownMenuSetting
import com.oxyethylene.easynote.domain.PlainSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.ArrowRightIcon
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.components.VersionSketchDialog
import com.oxyethylene.easynote.ui.theme.GreyDarker
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.Pale
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.ui.theme.Tomato
import me.saket.cascade.CascadeDropdownMenu


/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui
 * @ClassName    : SettingActivityUI.java
 * @createTime   : 2023/12/13 20:22
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SettingPageArea () {

    val context = LocalContext.current

    val appVersion = context.resources.getString(R.string.app_version_code)

    Column {

        val state = rememberScrollState()

        SimpleTitleBar (
            centerContent = {
                Crossfade(
                    targetState = state.value in 0 .. 300,
                    label = "",
                    animationSpec = tween(durationMillis = 300)
                ) {
                    if (!it) Text(text = "设置", color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        ) {

        }

        Column(Modifier.verticalScroll(state)) {
            // 中间的 logo
            Column (
                modifier = Modifier.height(260.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // app logo
                Image(
                    painter = painterResource(R.drawable.app_title_image),
                    contentDescription = null,
                    modifier = Modifier.height(60.dp)
                )

                // 版本号
                Text(
                    text = appVersion,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    color = GreyDarker,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "版本更新日志",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline,
                    letterSpacing = 0.sp,
                    modifier = Modifier.clickable (
                            onClick = {
                                MessageDialog.build(MIUIStyle())
                                    .setTitle("版本更新内容")
                                    .setCustomView(object : OnBindView<MessageDialog>(R.layout.compose_layout){
                                        override fun onBind(dialog: MessageDialog?, v: View?) {
                                            val composeView = v?.findViewById<ComposeView>(R.id.compose_view)
                                            composeView?.setContent { VersionSketchDialog() }
                                        }
                                    })
                                    .setOkButton("确认")
                                    .show()
                            },
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        )
                )

            }

            // 主设置列表
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SettingSubList(mainSettingList.subList(0, 3))

                SettingSubList(mainSettingList.subList(3, 6))

                SettingSubList(mainSettingList.subList(6, 7), Modifier.padding(bottom = 20.dp))
            }
        }

    }

}


@Composable
fun SettingSubList (settingList: List<SettingEntry>, modifier: Modifier = Modifier) {
    Card (
        modifier = modifier.wrapContentHeight().padding(start = 20.dp, end = 20.dp, top = 25.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        settingList.forEach {
            SettingListItem(it)
        }
    }
}

/**
 *  针对不同的设置类型有不同的样式
 *  @param entry 设置项
 */
@Composable
fun SettingListItem (entry: SettingEntry) {
    when (entry) {
        is SwitchSetting -> SwitchSettingItem(entry)
        is PlainSetting -> PlainSettingItem(entry)
        is DialogSetting -> DialogSettingItem(entry)
        is DropDownMenuSetting -> DropDownMenuSettingItem(entry)
    }
}

/**
 *  开关设置项的外观
 *  @param entry 设置项
 */
@Composable
fun SwitchSettingItem (entry: SwitchSetting) {

    var checked by rememberSaveable { mutableStateOf(entry.state) }

    Box (
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                checked = !checked
                entry.state = checked
                entry.onValueChanged(checked)
            }
    ) {

        Column(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                .wrapContentHeight()
                .align(Alignment.CenterStart)
                .padding(start = 28.dp, end = 60.dp),
        ) {
            Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            entry.description?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = {
                entry.state = it
                entry.onValueChanged(it)
                checked = it
            },
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp).scale(0.7f),
            colors = SwitchDefaults.colors(
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent,
                checkedThumbColor = Pale,
                uncheckedThumbColor = GreyDarker,
                checkedTrackColor = SkyBlue,
                uncheckedTrackColor = GreyLighter
            )
        )
    }
}

/**
 *  普通设置项的外观，可以点按打开新 Activity
 *  @param entry 设置项
 */
@Composable
fun PlainSettingItem (entry: PlainSetting) {

    val context = LocalContext.current

    Box (
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .clickable {
                val intent = Intent(entry.actionName)
                intent.`package` = context.packageName
                entry.commonActivityTitle?.let { intent.putExtra("title", it) }
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                .wrapContentHeight()
                .width(180.dp)
                .align(Alignment.CenterStart)
                .padding(start = 28.dp),
        ) {
            Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            entry.description?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        ArrowRightIcon(Modifier.align(Alignment.CenterEnd).padding(end = 20.dp).size(16.dp))
    }

}

/**
 *  对话框设置项的外观，可以点按打开一个对话框
 *  @param entry 设置项
 */
@Composable
fun DialogSettingItem (entry: DialogSetting) {


    Box (
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .clickable {
                entry.dialogAction()
            }
    ) {

        Column(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, end = 50.dp)
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(start = 28.dp),
        ) {
            Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            entry.description?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        ArrowRightIcon(Modifier.align(Alignment.CenterEnd).padding(end = 20.dp).size(16.dp))
    }
}

/**
 *  下拉菜单设置项的外观，可以点按打开一个下拉菜单
 *  @param entry 设置项
 */
@Composable
fun DropDownMenuSettingItem (entry: DropDownMenuSetting, moreAction: (String) -> Unit = {}) {

    var settingValue by rememberSaveable { mutableStateOf(entry.value) }

    var expanded by remember { mutableStateOf(false) }

    Box (
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                expanded = !expanded
            }
    ) {

        Column(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                .wrapContentHeight()
                .width(180.dp)
                .align(Alignment.CenterStart)
                .padding(start = 28.dp),
        ) {
            Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            entry.description?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 3, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(Modifier.align(Alignment.CenterEnd)) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
                onClick = {
                    expanded = true
                }
            ) {
                Text(
                    text = settingValue,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            CascadeDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                shape = RoundedCornerShape(12.dp),
            ) {

                entry.menuList.forEach {

                    androidx.compose.material3.DropdownMenuItem(
                        leadingIcon = { if (it.first != -1) Image(painter = painterResource(it.first), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {Text(text = it.second, fontSize = 14.sp, color = Color.DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)},
                        onClick = {
                            expanded = false
                            entry.onValueChanged(it.second)
                            entry.value = it.second
                            settingValue = it.second
                            moreAction(it.second)
                        }
                    )

                }

            }
        }


    }

}