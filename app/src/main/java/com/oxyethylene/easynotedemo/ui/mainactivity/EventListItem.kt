package com.oxyethylene.easynotedemo.ui.mainactivity

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.InputInfo
import com.oxyethylene.easynotedemo.domain.Event
import com.oxyethylene.easynotedemo.ui.components.EventIcon
import com.oxyethylene.easynotedemo.ui.theme.GreyDarker
import com.oxyethylene.easynotedemo.ui.theme.SkyBlue
import com.oxyethylene.easynotedemo.util.EventUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui.mainactivity
 * @ClassName    : EventListItem.java
 * @createTime   : 2024/1/17 1:57
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

@Composable
fun EventListItem (item: Event, context: Context) {

    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                PopNotification.build(MIUIStyle()).setMessage("打开事件界面（还没做）").show()
            }
            .padding(start = 14.dp)
    ) {

        Column(Modifier.fillMaxSize(0.7f)) {
            Row (Modifier.padding(top = 10.dp)){

                EventIcon(Modifier.size(22.dp))

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = item.eventName,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 1.dp)
                )

            }


            Text("关联了 ${item.noteCount} 篇文章", fontSize = 10.sp, color = Color.Gray)
            Spacer(Modifier.size(4.dp))
            if(item.description.isNotEmpty()) {
                Text(text = item.description, maxLines = 3, color = GreyDarker, fontSize = 10.sp, lineHeight = 14.sp, modifier = Modifier.width(200.dp).padding(bottom = 12.dp))
            } else {
                Spacer(Modifier.size(6.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier.padding(end = 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { updateEventDescDialog(item) }
            ){
                Icon(Icons.Default.Edit, "", Modifier.size(18.dp), Color.DarkGray)
            }
        }

    }

}

fun updateEventDescDialog (item: Event) {

    InputDialog.build(MIUIStyle())
        .setTitle("修改事件描述")
        .setInputText(item.description)
        .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()).setMultipleLines(true))
        .setCancelButton("取消")
        .setOkButton("修改")
        .setOkButtonClickListener(OnInputDialogButtonClickListener{
            dialog, v, inputStr ->
            EventUtil.updateEventDesc(item.eventId, inputStr)
            return@OnInputDialogButtonClickListener false
        })
        .show()

}