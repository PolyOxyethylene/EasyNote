package com.oxyethylene.easynote.ui.commonactivity

import android.view.Gravity
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.KeywordUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : RecyclePageUI.java
 * @createTime   : 2024/4/9 22:08
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 回收站界面
 */
@Composable
fun RecyclePageUI () {

    val context = LocalContext.current

    var recycleList = remember { mutableStateListOf<NoteFile>() }

    recycleList.addAll(FileUtil.getRecycledNotes())

    Column (
        modifier = Modifier.fillMaxSize()
    ) {

        SimpleTitleBar("回收站")

        LazyColumn (
            modifier = Modifier.padding(top = 20.dp).fillMaxSize()
        ) {

            items(recycleList) {

                RecycledListItem(it) {
                    note -> recycleList.remove(note)
                }

            }

            if (recycleList.isEmpty()) {

                item {
                    Column (Modifier.fillMaxWidth()) {
                        Text(
                            text = "回收站为空",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 100.dp)
                        )
                    }
                }

            }

        }

    }

}

/**
 *  回收站列表的的列表项
 *  @param item 列表项要使用的类对象
 *  @param onAlterCall 出现改动时执行的动作，例如文章的恢复或彻底删除
 */
@Composable
fun RecycledListItem(item: NoteFile, onAlterCall: (NoteFile) -> Unit) {

    val context = LocalContext.current

    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp)
            .height(56.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                MessageDialog.build(MIUIStyle())
                    .setTitle("${item.fileName}\n")
                    .setTitleTextInfo(TextInfo().setGravity(Gravity.CENTER).setFontSize(16).setBold(true))
                    .setButtonOrientation(LinearLayout.VERTICAL)
                    .setOkButton("恢复")
                    .setOkButtonClickListener {
                        _, _ ->
                        if (FileUtil.recoverNote(item.fileId)) {
                            PopNotification.build(MIUIStyle()).setMessage("恢复成功").show()
                            // 从显示列表中移除
                            onAlterCall(item)
                        } else {
                            PopNotification.build(MIUIStyle()).setMessage("未知异常，请联系开发者").show()
                        }
                        return@setOkButtonClickListener false
                    }
                    .setOtherButton("彻底删除")
                    .setOtherButtonClickListener {
                            _, _ ->
                        MessageDialog.build(MIUIStyle())
                            .setTitle("彻底删除")
                            .setMessage("此操作将会把文章彻底删除，是否继续?")
                            .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                            .setOkButton("确定")
                            .setOkButtonClickListener {
                                    _, _ ->
                                if (item.eventId != 0) {
                                    MessageDialog.build(MIUIStyle())
                                        .setTitle("删除文章")
                                        .setMessage("当前文章已绑定事件，是否删除?")
                                        .setCancelButton("取消")
                                        .setOkButton("确定")
                                        .setOkButtonClickListener(
                                            OnDialogButtonClickListener {
                                                    dialog, v ->
                                                EventUtil.unbindNote(item)
                                                if (item.hasBindedKeywords()) {
                                                    KeywordUtil.unbindNoteFromKw(item.keywordList, item.fileId)
                                                }
                                                FileUtil.deleteFileEntry(item.fileId, context)
                                                PopNotification.build(MIUIStyle()).setMessage("文件已彻底删除").show()
                                                // 从显示列表中移除
                                                onAlterCall(item)
                                                return@OnDialogButtonClickListener false
                                            }
                                        ).show()
                                } else {
                                    // 如果是文章，还要删除绑定的关键词
                                    if (item.hasBindedKeywords()) {
                                        KeywordUtil.unbindNoteFromKw(item.keywordList, item.fileId)
                                    }
                                    FileUtil.deleteFileEntry(item.fileId, context)
                                    // 从显示列表中移除
                                    onAlterCall(item)
                                    PopNotification.build(MIUIStyle()).setMessage("文件已彻底删除").show()
                                }
                                return@setOkButtonClickListener false
                            }
                            .setCancelButton("取消")
                            .show()
                        return@setOtherButtonClickListener false
                    }
                    .setCancelButton("取消")
                    .show()
            }
    ) {
        Box(modifier = Modifier.padding(start = 14.dp).fillMaxSize()) {

            // 左上角显示 文件类型图标 以及 文件名 的部分
            Row(modifier = Modifier.align(Alignment.TopStart)) {
                Text(
                    text = item.fileName,
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            // 左下角显示 文件修改时间 的部分
            Text(
                if (item.updateTime != null)
                    "修改于 ${item.updateTime}"
                else
                    "创建于 ${item.createTime}",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 10.dp)
            )

        }

    }

}