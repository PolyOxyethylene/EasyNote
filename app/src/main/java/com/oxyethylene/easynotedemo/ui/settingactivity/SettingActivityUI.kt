package com.oxyethylene.easynotedemo.ui.settingactivity

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.R
import com.oxyethylene.easynotedemo.domain.DialogSetting
import com.oxyethylene.easynotedemo.domain.PlainSetting
import com.oxyethylene.easynotedemo.domain.SettingEntry
import com.oxyethylene.easynotedemo.domain.SwitchSetting
import com.oxyethylene.easynotedemo.domain.testSettings
import com.oxyethylene.easynotedemo.ui.components.ArrowRightIcon
import com.oxyethylene.easynotedemo.ui.components.MoreIcon
import com.oxyethylene.easynotedemo.ui.components.SimpleTitleBar
import com.oxyethylene.easynotedemo.ui.theme.GreyDarker
import com.oxyethylene.easynotedemo.ui.theme.GreyLighter
import com.oxyethylene.easynotedemo.ui.theme.Pale
import com.oxyethylene.easynotedemo.ui.theme.SkyBlue


/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui
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

        // 中间的 logo
        Column (
            modifier = Modifier.height(300.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row () {
                Text("Easy No", fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Text("te", fontSize = 34.sp, fontWeight = FontWeight.Bold , color = Color(0xFF2654FF))
            }
            Text(appVersion, fontSize = 16.sp, color = GreyDarker, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
        }

        SettingList(testSettings.SettingList, Modifier)

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
fun SettingList (settingList: ArrayList<SettingEntry>, modifier: Modifier) {

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingSubList(settingList.subList(0, 4), modifier)

        SettingSubList(settingList.subList(4, 5), modifier)
    }

}

@Composable
fun SettingSubList (settingList: MutableList<SettingEntry>, modifier: Modifier) {
    Card (
        modifier = Modifier.wrapContentHeight().padding(start = 20.dp, end = 20.dp, top = 25.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        LazyColumn (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(settingList) {
                    item ->  SettingListItem(item)
            }
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
        modifier = Modifier.fillMaxWidth().height(60.dp)
    ) {

        Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterStart).padding(start = 28.dp))

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
        modifier = Modifier.fillMaxWidth().height(60.dp)
            .clickable {
            val intent = Intent(entry.actionName)
            context.startActivity(intent)
        }
    ) {
        Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterStart).padding(start = 28.dp))

        ArrowRightIcon(Modifier.align(Alignment.CenterEnd).padding(end = 20.dp).size(16.dp))
    }

}

/**
 *  对话框设置项的外观，可以点按打开一个对话框
 *  @param entry 设置项
 */
@Composable
fun DialogSettingItem (entry: DialogSetting) {

    val context = LocalContext.current

    Box (
        modifier = Modifier.fillMaxWidth().height(60.dp)
    ) {

        Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterStart).padding(start = 28.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
            onClick = entry.dialogAction
        ) {
            MoreIcon(Modifier.size(24.dp), Color.DarkGray)
        }
    }
}
