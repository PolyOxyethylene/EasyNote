package com.oxyethylene.easynote.ui.eventactivity

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.InputInfo
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.Event
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.mainactivity.ListItem
import com.oxyethylene.easynote.ui.theme.GreyDarker
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui.eventactivity
 * @ClassName    : EventInfoPageUI.java
 * @createTime   : 2024/1/19 2:39
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Composable
fun EventInfoPageArea (event: Event, modifier: Modifier = Modifier) {

    var eventInfo by remember { mutableStateOf(event) }

    var noteList = remember { mutableStateListOf<NoteFile>() }

    noteList.addAll(FileUtil.getNotesByEventId(eventInfo.eventId))

    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SimpleTitleBar("事件详情", modifier) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray
                ),
                onClick = {
                    onAddNoteButtonClick(context, noteList, eventInfo.eventName)
                }) {
                Image(
                    painter = painterResource(R.mipmap.ic_add_note),
                    "",
                    modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                )
            }
        }

        Text(eventInfo.eventName, color = Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 22.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)

        Spacer(Modifier.size(10.dp))

        Row(
            Modifier.padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Transparent)
                .border(1.dp, GreyLighter, RoundedCornerShape(12.dp))
        ) {
            Text(eventInfo.description, color = GreyDarker, fontSize = 14.sp, lineHeight = 16.sp, maxLines = 5, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp))
        }

        Crossfade(
            targetState = noteList,
            modifier = Modifier.fillMaxSize(),
            animationSpec = tween(durationMillis = 250), label = ""
        ) {
            LazyColumn (Modifier.padding(top = 10.dp).fillMaxSize()) {

                itemsIndexed(it) {
                        index, item -> ListItem(item, context) { onAlterButtonClick(index, item, it) }
                }

            }
        }

    }

}


/**
 *  当事件列表的列表项的修改按钮点击时执行的方法
 *  @param item 列表项要使用的类对象
 *  @param context 应用上下文
 *  @param noteList 关联的事件列表
 */
fun onAlterButtonClick (itemIndex: Int, item: NoteFile, noteList: MutableList<NoteFile>) {

    BottomMenu.build(MIUIStyle())
        .setMenuList(arrayOf("重命名", "解除绑定"))
        .setTitle(item.fileName)
        .setTitleTextInfo(TextInfo().setMaxLines(1).setShowEllipsis(true).setBold(true))
        .setOnMenuItemClickListener(object : OnMenuItemClickListener<BottomMenu> {
            override fun onClick(
                dialog: BottomMenu?,
                text: CharSequence?,
                index: Int
            ): Boolean {
                when (text) {
                    "重命名" -> {
                        InputDialog.build(MIUIStyle())
                            .setTitle("重命名文件")
                            .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()))
                            .setCancelButton("取消")
                            .setOkButton("重命名")
                            .setOkButtonClickListener(OnInputDialogButtonClickListener {
                                    dialog, v, inputStr ->
                                if (inputStr.isBlank() || inputStr.isEmpty()) {
                                    PopNotification.build(MIUIStyle()).setMessage("请输入包含非空字符的文件名").show()
                                } else {
                                    // 重命名文件
                                    FileUtil.renameFile(item.fileId, inputStr.trim())
                                    noteList.set(itemIndex, item.clone())
                                }
                                return@OnInputDialogButtonClickListener false
                            })
                            .show()
                    }
                    "解除绑定" -> {
                        if (EventUtil.unbindNote(item)) {
                            noteList.remove(item)
                            PopNotification.build(MIUIStyle()).setMessage("解除绑定成功").show()
                        } else {
                            PopNotification.build(MIUIStyle()).setMessage("解绑失败，该文章可能没有绑定过文章").show()
                        }
                    }
                }
                return false
            }
        }).show()

}

/**
 *  事件详情页面右上角的新建文章按钮
 *  @param noteList 事件详情页面的文章列表
 */
fun onAddNoteButtonClick (context: Context, noteList: MutableList<NoteFile>, eventName: String) {

    InputDialog.build(MIUIStyle())
        .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()))
        .setTitle("新建文章")
        .setMessage("在此新建的文章会添加到\n根目录, 并绑定当前事件")
        .setCancelButton("取消")
        .setOkButton("创建")
        .setOkButtonClickListener(OnInputDialogButtonClickListener {
                dialog, v, inputStr ->
            if (inputStr.isBlank() || inputStr.isEmpty()) {
                PopNotification.build(MIUIStyle()).setMessage("请输入包含非空字符的文件名").show()
            } else {
                // 创建文章
                val newFile = FileUtil.createFile(inputStr.trim(), 2, context)
                newFile?.let {
                    EventUtil.bindNote2Event(eventName, it as NoteFile)
                    noteList.add(it)
                }
            }
            return@OnInputDialogButtonClickListener false
        })
        .show()

}
