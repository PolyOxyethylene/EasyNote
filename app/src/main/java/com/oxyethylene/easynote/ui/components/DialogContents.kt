package com.oxyethylene.easynote.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.view.Gravity
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.NLPResult
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.KeywordUtil
import com.oxyethylene.easynote.util.NoteUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.components
 * @ClassName    : DialogContents.java
 * @createTime   : 2024/2/23 18:04
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 对话框的自定义布局
 */
/**
 * 主页权限请求框
 */
@Composable
fun PermissionDialog() {

    val context = LocalContext.current

    val permissionInfo = context.resources.getString(R.string.permission_request_info)

    // 请求的权限列表以及相关信息
    val permissionInfoList = context.resources.getStringArray(R.array.permission_request_list)

    Column (
        Modifier.padding(start = 30.dp, end = 30.dp, bottom = 20.dp).fillMaxSize()
    ) {

        Text(text = permissionInfo, fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 20.dp))

        permissionInfoList.forEach {
            info ->
            val i = info.indexOf('-')
            Text(info.substring(0, i), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 10.dp))
            Text(info.substring(i+1), fontSize = 14.sp, color = Color.Gray)
        }

        Column (
            Modifier.fillMaxSize()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(0.8.dp, Color.LightGray, RoundedCornerShape(10.dp))
        ) {
            Text(text = "\"管理所有文件的权限\"设置方式：\n在系统设置中搜索\"所有文件\"，根据下图指引设置权限(不同厂商的手机系统，权限名称可能有细微区别)", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp))
            Row (Modifier.padding(10.dp).fillMaxWidth().height(0.5.dp).background(Color.LightGray)) {  }
            Image(painter = painterResource(R.drawable.img_sp_permission_1), contentDescription = null, modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp))
            Row (Modifier.padding(10.dp).fillMaxWidth().height(0.5.dp).background(Color.LightGray)) {  }
            Image(painter = painterResource(R.drawable.img_sp_permission_2), contentDescription = null, modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp))
        }


    }

}


/**
 * 展示文章关联的关键词的对话框
 * @param keywordMap 关键词的映射集合，键为关键词名，值为关键词 id
 */
@Composable
fun ShowKeywordsDialog (keywordMap: HashMap<String, Int>) {

    if (keywordMap.isEmpty()) {
        Box(Modifier.fillMaxWidth().height(200.dp).padding(20.dp)) {
            Text(text = "暂无关联的关键词", fontSize = 14.sp, color = Color.LightGray, modifier = Modifier.align(Alignment.Center))
        }
    } else {
        LazyVerticalGrid (
            modifier = Modifier.fillMaxWidth().height(200.dp).padding(20.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Start
        ) {

            items(keywordMap.keys.toList()) {

                Row (Modifier.wrapContentSize(Alignment.CenterStart).padding(6.dp).clip(RoundedCornerShape(4.dp)).background(GreyLighter)) {
                    Text(text = it, color = Color.DarkGray, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp))
                }

            }

        }
    }


}

/**
 * 展示关键词关联的文章的对话框
 * @param keywordId 关键词的 id
 */
@Composable
fun ShowBindedNotesDialog (keywordId: Int) {

    val noteList = FileUtil.getNotes(KeywordUtil.getBindedNotes(keywordId))

    val context = LocalContext.current

    LazyColumn (
        modifier = Modifier.fillMaxWidth().height(260.dp).padding(20.dp),
        verticalArrangement = if (noteList.isEmpty()) Arrangement.Center else Arrangement.Top
    ) {

        itemsIndexed(noteList) {
            index, note->
            Row (
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        val intent = Intent("com.oxyethylene.EDIT")
                        NoteUtil.beforeEdit(note.fileName, note.fileId)
                        context.startActivity(intent)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                FileIcon(Modifier.padding(start = 10.dp).size(18.dp))

                Text(text = FileUtil.getNoteFilePath(note.fileId), maxLines = 1, fontSize = 16.sp, color = Color.DarkGray, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(start = 6.dp, end = 20.dp))

            }
            if (index < noteList.size - 1) Row(Modifier.fillMaxWidth().height(0.6.dp).background(GreyLighter)) {}

        }

        if (noteList.isEmpty()) {
            item {
                Box (Modifier.fillMaxSize()) {
                    Text(text = "暂无关联的文章", fontSize = 14.sp, color = Color.LightGray, modifier = Modifier.align(Alignment.Center))
                }
            }
        }

    }

}

/**
 * 展示文章摘要及关键词的对话框
 * @param extraction 文章的摘要结果
 * @param onKeywordUpdate 当执行关键词相关的更新操作时，执行的额外操作
 * @param onDialogDismiss 对话框关闭时执行的动作
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ShowExtractionDialog (extraction: NLPResult, onKeywordUpdate: () -> Unit = {}, onDialogDismiss: () -> Unit) {

    val keywords = remember { mutableStateListOf<String>() }

    keywords.addAll(extraction.keywords)

    KeywordUtil.getBindedKeywords(FileUtil.getNote(NoteUtil.getNoteId())!!.keywordList).keys.forEach {
        keywords.remove(it)
    }

    Column (
        Modifier.fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "分析结果",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Text(
                text = "关闭",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = SkyBlue,
                modifier = Modifier.clickable (
                        onClick = onDialogDismiss,
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    )
            )
        }

        Row (Modifier.fillMaxWidth().padding(top = 6.dp).height(0.6.dp).background(GreyLighter)) {  }

        Row (Modifier.padding(top = 20.dp)) {
            Text(
                text = "点击文章摘要可以创建以其为名的事件\n点击下方关键词可以快捷创建并添加到当前文章",
                fontSize = 10.sp,
                color = Color.Gray,
                lineHeight = 11.sp
            )
        }

        Row (Modifier.padding(top = 10.dp)) {
            Text(
                text = "文章摘要",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        Row (
            Modifier.fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(top = 10.dp, start = 6.dp, end = 6.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(GreyLighter.copy(alpha = 0.5f))
                .clickable {
                    MessageDialog.build(MIUIStyle())
                        .setTitle("创建事件")
                        .setMessage("将要创建以下事件\n\"${extraction.summarization}\"\n同时将当前文章绑定到新建事件")
                        .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                        .setCancelButton("取消")
                        .setOkButton("确认")
                        .setOkButtonClickListener {
                                dialog, v ->
                            if (FileUtil.isBinded(NoteUtil.getNoteId())) {
                                // 如果文章已经绑定事件，不允许新的绑定
                                PopNotification.build(MIUIStyle()).setMessage("当前文章已绑定过事件，请解绑后再执行此操作").show()
                            } else {
                                val res = EventUtil.createEvent(extraction.summarization)
                                if (!res) {
                                    // 事件创建失败
                                    PopNotification.build(MIUIStyle()).setMessage("已有同名事件，不能重复创建").show()
                                } else if (EventUtil.bindNote2Event(extraction.summarization, FileUtil.getNote(NoteUtil.getNoteId())!!)) {
                                    // 事件创建成功，文章也绑定成功
                                    PopNotification.build(MIUIStyle()).setMessage("绑定事件成功").show()
                                } else {
                                    // 未知异常
                                    PopNotification.build(MIUIStyle()).setMessage("未知异常，请联系开发者").show()
                                }
                            }
                            return@setOkButtonClickListener false
                        }
                        .show()
                }
        ) {
            Text(
                text = extraction.summarization,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Row (Modifier.padding(top = 20.dp)) {
            Text(
                text = "关键词",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        LazyVerticalGrid (
            modifier = Modifier.fillMaxWidth().height(300.dp).padding(top = 4.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Start
        ) {

            items(keywords) {

                Row (
                    Modifier.wrapContentSize(Alignment.CenterStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(GreyLighter)
                        .clickable {
                            MessageDialog.build(MIUIStyle())
                                .setTitle("添加关键词")
                                .setMessage("要将 \"$it\" 添加为关键词吗?\n同时将关键词绑定到当前文章")
                                .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                                .setCancelButton("取消")
                                .setOkButton("确认")
                                .setOkButtonClickListener {
                                        dialog, v ->
                                    var keywordId = KeywordUtil.addKeyword(it)
                                    // 如果这个关键词被创建过，在当前情况下可以直接与文章绑定
                                    if (keywordId == -1) {
                                        keywordId = KeywordUtil.getKeywordIdByName(it)
                                    }
                                    // 关键词和文章的绑定
                                    KeywordUtil.bindNote2Keyword(keywordId, NoteUtil.getNoteId())
                                    FileUtil.bindKeyword2Note(keywordId, NoteUtil.getNoteId())
                                    keywords.remove(it)
                                    onKeywordUpdate()
                                    return@setOkButtonClickListener false
                                }
                                .show()
                        }
                ) {
                    Text(
                        text = it,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                    )
                }

            }

        }
    }

}


@Composable
fun VersionSketchDialog () {

    val context = LocalContext.current

    val list = context.resources.getStringArray(R.array.version_sketch_list)

    Column (
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
    ) {

        list.forEach {

            val versionCode = it.substring(0, it.indexOf('^'))

            val infoList = it.substring(it.indexOf('^') + 1).split('-')

            Text(
                text = versionCode,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 20.dp)
            )

            infoList.forEach {
                versionInfo ->
                Text(
                    text = versionInfo,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(top = 6.dp, start = 6.dp)
                )
            }

        }

        Text(text = "")

    }

}