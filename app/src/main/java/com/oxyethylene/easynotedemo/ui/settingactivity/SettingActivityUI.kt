package com.oxyethylene.easynotedemo.ui.settingactivity

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.oxyethylene.easynotedemo.domain.DialogSetting
import com.oxyethylene.easynotedemo.domain.PlainSetting
import com.oxyethylene.easynotedemo.domain.SettingEntry
import com.oxyethylene.easynotedemo.domain.SwitchSetting
import com.oxyethylene.easynotedemo.ui.components.BackIcon
import com.oxyethylene.easynotedemo.ui.components.MoreIcon
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

/**
 *  顶部导航栏
 *  @param title 顶部的标题
 *  @param modifier 设置导航栏外观
 */
@Composable
fun SettingPageTopBar (title : String, modifier: Modifier) {

    val context = LocalContext.current

    Box (
        modifier = modifier.fillMaxWidth().statusBarsPadding().padding(top = 12.dp),
    ) {

        Button(
            modifier = Modifier.align(Alignment.TopStart),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
            onClick = {
                (context as Activity).finish()
            }) {
            BackIcon(Modifier.size(20.dp).align(Alignment.CenterVertically))
        }

        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold,  modifier = Modifier.align(Alignment.Center))

    }

}

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

        SettingSubList(settingList.subList(4, 8), modifier)

        SettingSubList(settingList.subList(8, 10), modifier)
    }

}

@Composable
fun SettingSubList (settingList: MutableList<SettingEntry>, modifier: Modifier) {
    Card (
        modifier = Modifier.wrapContentHeight().padding(start = 30.dp, end = 30.dp, top = 25.dp).fillMaxWidth(),
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

@Composable
fun SettingListItem (entry: SettingEntry) {

    val context = LocalContext.current

    Box (
      modifier = Modifier.fillMaxWidth().height(60.dp)
          .padding(start = 18.dp, top = 2.dp)
    ) {

        Text(entry.settingName, color = Color.DarkGray, fontSize = 14.sp, modifier = Modifier.align(Alignment.CenterStart))

        when (entry) {
            is SwitchSetting -> {
                var _checked by rememberSaveable { mutableStateOf(entry.state) }
                Switch(
                    checked = _checked,
                    onCheckedChange = {
                        entry.state = it
                        _checked = it
                    },
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 20.dp).scale(0.7f),
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

            is PlainSetting -> {}

            is DialogSetting -> {
                Button(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
                    onClick = {
                        Toast.makeText(context, "打开更多菜单", Toast.LENGTH_SHORT).show()
                    }) {
                    MoreIcon(Modifier.size(24.dp), Color.DarkGray)
                }
            }
        }

    }


}