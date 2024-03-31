package com.oxyethylene.easynote.ui.mainactivity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.domain.Dentry
import com.oxyethylene.easynote.domain.Dir
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.ui.components.FileIcon
import com.oxyethylene.easynote.ui.components.FolderIcon
import com.oxyethylene.easynote.ui.components.MoreIcon
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.NoteUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui.mainactivity
 * @ClassName    : FolderListItem.java
 * @createTime   : 2024/1/6 1:51
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 文件列表中的一个列表项
 */
/**
 *  普通的列表项
 *  @param item 列表项要使用的类对象
 *  @param context 应用上下文
 *  @param onAlterRequest 右侧按钮打开“更改”菜单的方法
 */
@Composable
fun ListItem(item: Dentry, context: Context, onAlterRequest: () -> Unit) {

    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp)
            .height(56.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                // 如果是目录，则切换当前操作目录
                if (item is Dir) {
                    FileUtil.updateDirectory(item.fileId)
                }
                // 如果是文件，就打开编辑界面
                else {
                    val intent = Intent("com.oxyethylene.EDIT")
                    intent.`package` = context.packageName
                    NoteUtil.beforeEdit(item.fileName, item.fileId)
                    context.startActivity(intent)
                }
            }
    ) {
        Box(modifier = Modifier.padding(start = 14.dp).fillMaxSize()) {

            // 左上角显示 文件类型图标 以及 文件名 的部分
            Row(modifier = Modifier.align(Alignment.TopStart)) {
                if (item is Dir) FolderIcon(
                    Modifier.padding(
                        top = 12.dp,
                    ).size(18.dp)
                ) else FileIcon(
                    Modifier.padding(top = 12.dp).size(18.dp)
                )
                Text(
                    text = item.fileName,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 12.dp, start = 6.dp)
                )
            }

            // 左下角显示 文件修改时间 的部分
            Text(
                if (item is NoteFile && item.updateTime != null)
                    "修改于 ${item.updateTime}"
                else
                    "创建于 ${item.createTime}",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 10.dp)
            )

            // 右侧正中间的 修改按钮
            Button(
                modifier = Modifier.align(Alignment.CenterEnd).wrapContentWidth().scale(0.8f),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Transparent,
                    containerColor = Color.Transparent
                ),
                onClick = {
                    onAlterRequest()
                }) {
                MoreIcon(Modifier.size(24.dp), Color.DarkGray)
            }

        }

    }

}


/**
 *  选择要为文章绑定的事件的列表菜单（单选）
 *  @param item 选择绑定的文章
 */
fun showEventBindingDialog (item: NoteFile) {

    // 列表
    val menuList = ArrayList<CharSequence>()
    menuList.add("先不绑定事件")
    menuList.addAll(EventUtil.getEventNames())

    BottomMenu.build(MIUIStyle())
        .setMenuList(menuList)
        .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(false){
            override fun getIcon(dialog: BottomMenu?, index: Int, menuText: String?): Int {
                return when(index) {
                    0 -> R.drawable.cancel_icon
                    else -> R.drawable.event_icon
                }
            }
        })
        .setOnMenuItemClickListener(object : OnMenuItemClickListener<BottomMenu> {
            override fun onClick(dialog: BottomMenu?, text: CharSequence?, index: Int): Boolean {
                when(text) {
                    "先不绑定事件" -> return false
                    else -> {
                        // 进行绑定
                        val res = text?.toString()?.let { EventUtil.bindNote2Event(it, item) }
                        if (res == true) {
                            PopNotification.build(MIUIStyle()).setMessage("绑定成功").show()
                        } else {
                            PopNotification.build(MIUIStyle()).setMessage("绑定失败").show()
                        }
                        return false
                    }
                }
            }
        }).show()

}


/**
 * 底部搜索对话框的搜索结果列表
 */
@Composable
fun SearchListItem (item: Dentry, context: Context, onItemClick: () -> Unit = {}) {

    Row (
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                // 如果是目录，则切换当前操作目录
                if (item is Dir) {
                    FileUtil.updateDirectory(item.fileId)
                }
                // 如果是文件，就打开编辑界面
                else {
//                    val intent = Intent("com.oxyethylene.EDIT")
//                    intent.`package` = context.packageName
//                    NoteUtil.beforeEdit(item.fileName, item.fileId)
//                    context.startActivity(intent)
                    FileUtil.updateDirectory(item.parent?.fileId?: FileUtil.root.fileId)
                }
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