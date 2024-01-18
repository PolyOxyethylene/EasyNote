package com.oxyethylene.easynotedemo.ui.mainactivity

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.InputInfo
import com.oxyethylene.easynotedemo.domain.EventList
import com.oxyethylene.easynotedemo.ui.theme.SkyBlue
import com.oxyethylene.easynotedemo.util.EventUtil
import com.oxyethylene.easynotedemo.viewmodel.MainViewModel

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui.mainactivity
 * @ClassName    : EventPageUI.java
 * @createTime   : 2024/1/15 14:36
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventPageArea (modifier: Modifier, viewModel: MainViewModel) {

    val context = LocalContext.current

    val eventList by viewModel.eventEntryList.observeAsState(EventList())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {



        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // 新增事件的按钮
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SkyBlue)
                    .clickable {
                        onAddEventFABClick(context)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("创建新的事件", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            // 事件列表
            LazyColumn (Modifier.fillMaxSize()) {
                items(eventList.getList()) {
                    item -> EventListItem(item, context)
                }
            }

        }

    }

}

/**
 *  点击悬浮按钮执行的事件
 */
fun onAddEventFABClick (context: Context) {

    InputDialog.build(MIUIStyle())
        .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()))
        .setAutoShowInputKeyboard(true)
        .setTitle("快速新建事件")
        .setMessage("请输入事件名称")
        .setCancelButton("取消")
        .setOkButton("创建")
        .setOkButtonClickListener(OnInputDialogButtonClickListener {
            dialog, v, inputStr ->
            if (inputStr.isBlank() || inputStr.isEmpty()) {
                PopNotification.build(MIUIStyle()).setMessage("请输入包含非空字符的事件名").show()
            } else {
                // 创建文章
                EventUtil.createEvent(inputStr)
            }
            return@OnInputDialogButtonClickListener false
        })
        .show()

}