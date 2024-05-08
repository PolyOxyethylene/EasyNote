package com.oxyethylene.easynote.ui.eventactivity

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnBottomMenuButtonClickListener
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener
import com.kongzue.dialogx.interfaces.OnMenuItemSelectListener
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.InputInfo
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.common.enumeration.ComponentSize
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.domain.entity.Event
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.editactivity.KeywordSketch
import com.oxyethylene.easynote.ui.mainactivity.ListItem
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.KeywordUtil
import me.saket.cascade.CascadeDropdownMenu

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
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun EventInfoPageArea (event: Event, modifier: Modifier = Modifier) {

    var eventInfo by remember { mutableStateOf(event.clone()) }

    var noteList = remember { mutableStateListOf<NoteFile>() }

    val recycledCount = remember { mutableStateOf(0) }

    noteList.addAll(FileUtil.getNotesByEventId(eventInfo.eventId, recycledCount))

    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SimpleTitleBar(
            centerContent = {
                Text(
                    text = eventInfo.eventName,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(140.dp)
                        .clickable(
                            onClick = {
                            MessageDialog.build(MIUIStyle())
                                .setTitle("事件名称")
                                .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                                .setMessage(eventInfo.eventName)
                                .setOkButton("确定")
                                .show()
                            },
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        )
                )
            }
        ) {
            EventUtilButton(event, noteList)
        }

        Row(
            Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent)
                .border(1.dp, GreyLighter, RoundedCornerShape(8.dp))
                .clickable {
                    InputDialog.build(MIUIStyle())
                        .setTitle("修改事件描述")
                        .setInputText(event.description)
                        .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()).setMultipleLines(true))
                        .setCancelButton("取消")
                        .setOkButton("修改")
                        .setOkButtonClickListener(OnInputDialogButtonClickListener{
                                dialog, v, inputStr ->
                            EventUtil.updateEventDesc(event.eventId, inputStr)
                            eventInfo = event.clone()
                            return@OnInputDialogButtonClickListener false
                        })
                        .show()
                }
        ) {
            val isDesEmpty = eventInfo.description.isEmpty()

            Text(
                text = if (isDesEmpty) "此事件还没有描述，点击可添加描述" else eventInfo.description,
                color = if (isDesEmpty) Color.LightGray else Color.Gray,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(10.dp)
            )
        }

        Crossfade(
            targetState = noteList,
            modifier = Modifier.fillMaxSize(),
            animationSpec = tween(durationMillis = 250), label = ""
        ) {
            LazyColumn (
                modifier = Modifier.padding(top = 10.dp)
                    .fillMaxSize(),
                verticalArrangement = if (it.isNotEmpty()) Arrangement.Top else Arrangement.Center
            ) {

                if (it.isNotEmpty()) {
                    itemsIndexed(it) {
                            index, item ->
                        if (item.fileId > 0)
                            ListItem(
                                item = item,
                                context = context,
                                appendix = {
                                    KeywordSketch(KeywordUtil.getBindedKeywords(item.keywordList), Modifier.padding(start = 14.dp), keywordSize = ComponentSize.SMALL)
                                }
                            ) { onAlterButtonClick(index, item, it) }
                    }
                } else {

                    item {
                        Column (
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.empty_archive_image),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp)
                            )
                            Text(
                                text = "当前事件没有可编辑的文章\n右上角\"绑定文章\"添加新的文章\n\n当前事件有 ${recycledCount.value} 篇文章在回收站",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                lineHeight = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }

                }

            }
        }

    }

}

@Composable
fun EventUtilButton (event: Event, noteList: MutableList<NoteFile>, onEventUpdate: () -> Unit = {}) {

    var expended by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box {

        Button (
            onClick = {expended = true},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = "绑定文章",
                fontSize = 14.sp,
                color = SkyBlue
            )
        }

        CascadeDropdownMenu(
            expanded = expended,
            onDismissRequest = {expended = false},
            shape = RoundedCornerShape(12.dp)
        ) {

            // 绑定已有关键词
            androidx.compose.material3.DropdownMenuItem(
                text = {Text(text = "新建文章并绑定", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)},
                onClick = {
                    expended = false
                    onAddNoteButtonClick(context, noteList, event.eventName)
                }
            )

            // 新建一个关键词并绑定
            androidx.compose.material3.DropdownMenuItem(
                text = {Text(text = "绑定已有文章", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)},
                onClick = {
                    expended = false
                    val noteMap = FileUtil.getEventUnbindedNotes(true)
                    var selectedNotes: Array<out CharSequence>? = null
                    BottomMenu.build(MIUIStyle())
                        .setTitle("绑定已有文章")
                        .setMenuList(noteMap.keys.toList())
                        .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(false){
                            override fun getIcon(dialog: BottomMenu?, index: Int, menuText: String?): Int = R.drawable.note_icon
                        })
                        .setOnMenuItemClickListener(object : OnMenuItemSelectListener<BottomMenu>() {
                            override fun onMultiItemSelect(
                                dialog: BottomMenu?,
                                text: Array<out CharSequence>?,
                                indexArray: IntArray?
                            ) {
                                selectedNotes = text
                            }
                        })
                        .setMultiSelection()
                        .setOkButton("确认添加",
                            OnBottomMenuButtonClickListener { _, _ ->
                                selectedNotes?.apply {
                                    if (isNotEmpty()) {
                                        forEach {
                                            val note = FileUtil.getNote(noteMap[it]!!)!!
                                            EventUtil.bindNote2Event(event.eventName, note)
                                            noteList.add(note)
                                        }
                                    }
                                }
                                return@OnBottomMenuButtonClickListener false
                            })
                        .setCancelButton("取消")
                        .show()
                }
            )

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
        .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
        .setTitle("新建文章")
        .setMessage("在此新建的文章会添加到以下目录\n${FileUtil.getNoteFilePath(FileUtil.getCurrentDir().fileId)}\n并绑定当前事件")
        .setCancelButton("取消")
        .setOkButton("创建")
        .setOkButtonClickListener(OnInputDialogButtonClickListener {
                dialog, v, inputStr ->
            if (inputStr.isBlank() || inputStr.isEmpty()) {
                PopNotification.build(MIUIStyle()).setMessage("请输入包含非空字符的文件名").show()
            } else {
                // 创建文章
                val newFile = FileUtil.createFile(inputStr.trim(), 1, context)
                newFile.let {
                    EventUtil.bindNote2Event(eventName, it as NoteFile)
                    noteList.add(it)
                }
            }
            return@OnInputDialogButtonClickListener false
        })
        .show()

}
