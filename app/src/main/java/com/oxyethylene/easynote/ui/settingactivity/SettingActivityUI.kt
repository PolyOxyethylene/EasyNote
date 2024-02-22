package com.oxyethylene.easynote.ui.settingactivity

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.common.arrays.mainSettingList
import com.oxyethylene.easynote.domain.DialogSetting
import com.oxyethylene.easynote.domain.DropDownMenuSetting
import com.oxyethylene.easynote.domain.PlainSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.ArrowRightIcon
import com.oxyethylene.easynote.ui.components.MoreIcon
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.GreyDarker
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.Pale
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.ui.theme.Tomato


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
@Composable
fun SettingPageArea () {

    val context = LocalContext.current

    val appVersion = context.resources.getString(R.string.app_version_code)

    Column {

        SettingPageTopBar("", Modifier)

        Column(Modifier.verticalScroll(rememberScrollState())) {
            // 中间的 logo
            Column (
                modifier = Modifier.height(300.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row () {
                    Text("Easy No", fontSize = 34.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.smileysans_oblique)))
                    Text("te", fontSize = 34.sp, fontWeight = FontWeight.Bold , color = Color(0xFF2654FF), fontFamily = FontFamily(Font(R.font.smileysans_oblique)))
                }
                Text(appVersion, fontSize = 16.sp, color = GreyDarker, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp), fontFamily = FontFamily(Font(R.font.smileysans_oblique)))

            }

            SettingList(mainSettingList)
        }

    }

}

/**
 *  顶部导航栏
 *  @param title 顶部的标题
 *  @param modifier 设置导航栏外观
 */
@Composable
fun SettingPageTopBar (title : String, modifier: Modifier = Modifier) = SimpleTitleBar(title, modifier)

/**
 *  设置列表
 */
@Composable
fun SettingList (settingList: List<SettingEntry>, modifier: Modifier = Modifier) {

    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingSubList(settingList.subList(0, 4))

        SettingSubList(settingList.subList(4, 5), Modifier.padding(bottom = 20.dp))
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

    var _checked by rememberSaveable { mutableStateOf(entry.state) }

    Box (
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
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
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Switch(
            checked = _checked,
            onCheckedChange = {
                entry.state = it
                _checked = it },
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
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
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
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                .wrapContentHeight()
                .width(180.dp)
                .align(Alignment.CenterStart)
                .padding(start = 28.dp),
        ) {
            Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            entry.description?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
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
fun DropDownMenuSettingItem (entry: DropDownMenuSetting) {

    Box (
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
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
                Text(it, color = GreyDarker, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
            entry.warning?.let {
                Spacer(Modifier.size(4.dp))
                Text(it, color = Tomato, fontSize = 8.sp, maxLines = 2, lineHeight = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Button(
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
            onClick = {
                // TODO 还没实现具体内容
                MessageDialog.build(MIUIStyle())
                    .setTitle(entry.settingName)
                    .setMessage("该功能开发中，敬请期待")
                    .setOkButton("确认")
                    .show()
            }
        ) {
            MoreIcon(Modifier.size(24.dp), Color.DarkGray)
        }
    }

}