package com.oxyethylene.easynote.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.Dentry
import com.oxyethylene.easynote.ui.mainactivity.SearchListItem
import com.oxyethylene.easynote.ui.theme.GreyLighter
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
 * 主页搜索框
 */
@Composable
fun SearchBox () {

    val input = remember { mutableStateOf("") }

    var resultList by remember { mutableStateOf(ArrayList<Dentry>()) }

    val manager = LocalFocusManager.current

    val context = LocalContext.current

    Column (
        modifier = Modifier.navigationBarsPadding().fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        InputText(
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp).fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            inputText = input,
            singleLine = false,
            hint = "搜索文件/事件..."
        ) {
            resultList = FileUtil.searchFileByName(input.value) as ArrayList<Dentry>
        }


        if (resultList.isEmpty()) {
            Text(
                text = "暂无搜索结果",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
            )
        }

        Column (
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {

            resultList.forEachIndexed {
                index, dentry ->
                SearchListItem(dentry, context) {
                    input.value = ""
                    resultList = ArrayList()
                    manager.clearFocus()
//                    SearchBoxUtil.show()
                }
                if (index < resultList.size - 1) {
                    Row(Modifier.fillMaxWidth().height(0.5.dp).background(GreyLighter)) {}
                }
            }

        }

    }

}

/**
 * 展示文章关联的关键词的对话框
 * @param keywordMap 关键词的映射集合，键为关键词名，值为关键词 id
 */
@Composable
fun ShowKeywordsDialog (keywordMap: HashMap<String, Int>) {

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