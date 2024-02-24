package com.oxyethylene.easynote.ui.appinfoactivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.DialogSetting
import com.oxyethylene.easynote.domain.PlainSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.DialogSettingItem
import com.oxyethylene.easynote.ui.settingactivity.PlainSettingItem
import com.oxyethylene.easynote.ui.theme.GreyDarker

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui.appinfoactivity
 * @ClassName    : InfoPageUI.java
 * @createTime   : 2024/1/10 20:39
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Composable
fun InfoPageArea () {

    val context = LocalContext.current

    val aboutApp = context.resources.getString(R.string.app_info)

    val designInfo = context.resources.getString(R.string.app_design_info)

    Column(
        modifier = Modifier
            .fillMaxSize().navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {

        InfoPageTopBar("关于", Modifier)

        Text("作者", fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 20.dp, start = 26.dp))

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 6.dp)
                .fillMaxWidth().height(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(painter = painterResource(R.drawable.developer_avatar), "this is developer's GitHub account avatar.", modifier = Modifier.padding(start = 16.dp).size(40.dp).clip(CircleShape))
            Text("PolyOxyethylene", fontSize = 16.sp, modifier = Modifier.padding(start = 12.dp))
        }

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(aboutApp, fontSize = 16.sp, color = Color.DarkGray, modifier = Modifier.padding(20.dp))
        }

        Text("更新软件", fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 20.dp, start = 26.dp))

        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 6.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {

            DialogSettingItem(
                DialogSetting("更新软件") {
                    MessageDialog.build(MIUIStyle())
                        .setTitle("获取更新")
                        .setMessage(R.string.app_update_info)
                        .setOkButton("确认")
                        .show()
                }
            )

        }

        Text("法律信息", fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 20.dp, start = 26.dp))

        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 6.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {

            PlainSettingItem(PlainSetting("开放源代码许可", "com.oxyethylene.OPENSOURCE_INFO"))

        }

        Text(
            text = designInfo,
            fontSize = 12.sp,
            color = Color.Gray,
            lineHeight = 14.sp,
            letterSpacing = 0.sp,
            modifier = Modifier.padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp)
        )

    }

}

@Composable
fun InfoPageTopBar (title: String, modifier: Modifier) = SimpleTitleBar(title, modifier)