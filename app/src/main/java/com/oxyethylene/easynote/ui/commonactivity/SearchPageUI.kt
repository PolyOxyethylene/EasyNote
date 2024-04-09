package com.oxyethylene.easynote.ui.commonactivity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.ui.components.FileIcon
import com.oxyethylene.easynote.ui.components.FolderIcon
import com.oxyethylene.easynote.ui.components.InputText
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.NoteUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : SearchPageUI.java
 * @createTime   : 2024/4/9 22:23
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 搜索文件界面
 */
@Composable
fun SearchPageUI() {

    Column (
        modifier = Modifier.fillMaxSize()
    ) {

        SimpleTitleBar("搜索")

        val input = remember { mutableStateOf("") }

        var resultList by remember { mutableStateOf(ArrayList<NoteFile>()) }

        val context = LocalContext.current

        Column (
            modifier = Modifier.navigationBarsPadding().fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InputText(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp).fillMaxWidth().height(40.dp),
                shape = RoundedCornerShape(20.dp),
                inputText = input,
                fontSize = 14.sp,
                singleLine = false,
                hint = "输入名称搜索文章…"
            ) {
                resultList = FileUtil.searchNoteByName(input.value) as ArrayList<NoteFile>
            }

            if (resultList.isEmpty()) {
                Text(
                    text = "暂无搜索结果",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 120.dp, bottom = 80.dp)
                )
            }

            Column (
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                    .navigationBarsPadding()
                    .fillMaxSize()
            ) {

                resultList.forEachIndexed {
                        index, note ->
                    SearchListItem(note, context)
                    if (index < resultList.size - 1) {
                        Row(Modifier.fillMaxWidth().height(0.5.dp).background(GreyLighter)) {}
                    }
                }

            }

        }

    }

}

/**
 * 底部搜索对话框的搜索结果列表
 * @param item 搜索结果，类型为文件
 * @param context 应用上下文
 * @param onItemClick 点击之后的额外操作
 */
@Composable
fun SearchListItem (item: NoteFile, context: Context, onItemClick: () -> Unit = {}) {

    Row (
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                // 打开文件编辑界面
                    val intent = Intent("com.oxyethylene.EDIT")
                    intent.`package` = context.packageName
                    NoteUtil.beforeEdit(item.fileName, item.fileId)
                    context.startActivity(intent)
                // 其他操作由外部定义
                onItemClick()
            }.padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 50.dp)
    ) {

        if (item.type == FileType.DIRECTORY) {
            FolderIcon(Modifier.size(18.dp))
        } else {
            FileIcon(Modifier.size(18.dp))
        }

        Text(
            text = FileUtil.getNoteFilePath(item.fileId),
            color = Color.DarkGray,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 10.dp)
        )

    }

}