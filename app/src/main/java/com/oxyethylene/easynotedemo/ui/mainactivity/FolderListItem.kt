package com.oxyethylene.easynotedemo.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.domain.Dentry
import com.oxyethylene.easynotedemo.domain.Dir
import com.oxyethylene.easynotedemo.domain.NoteFile
import com.oxyethylene.easynotedemo.ui.components.AlterIcon
import com.oxyethylene.easynotedemo.ui.components.FileIcon
import com.oxyethylene.easynotedemo.ui.components.FolderIcon
import com.oxyethylene.easynotedemo.ui.components.MoreIcon
import com.oxyethylene.easynotedemo.ui.components.RecycleIcon
import com.oxyethylene.easynotedemo.util.FileUtil
import com.oxyethylene.easynotedemo.util.NoteUtil
import com.oxyethylene.easynotedemo.util.SwipeableItemStatus
import com.oxyethylene.easynotedemo.util.anchors

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.ui.mainactivity
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
fun ListItem (item: Dentry, context: Context, onAlterRequest: () -> Unit) {

    // 每个文件用一个单独的 Card 组件显示
    Card(
        modifier = Modifier.height(70.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
                .clickable {
                    // 如果是目录，则切换当前操作目录
                    if (item is Dir) {
                        FileUtil.updateDirectory(item.fileId)
                    }
                    // 如果是文件，就打开编辑界面
                    else {
                        val intent = Intent("com.oxyethylene.EDIT")
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
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = item.fileName,
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                // 左下角显示 文件修改时间 的部分
                Text(
                    "创建于 ${item.lastModifiedTime}",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 10.dp)
                )

                // 右侧正中间的 修改按钮
                Button(
                    modifier = Modifier.align(Alignment.CenterEnd).wrapContentWidth().scale(0.8f),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent, containerColor = Color.Transparent),
                    onClick = {
                        FileUtil.updateSelectedFile(item.fileId)
                        onAlterRequest.invoke()
                    }) {
                    MoreIcon(Modifier.size(24.dp), Color.DarkGray)
                }

            }

        }

    }

}

/**
 *  可左滑显示更多选项的列表项
 *  @param item 对应的列表项对象
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableListItem (modifier: Modifier, item : Dentry) {

    val swipeableState = rememberSwipeableState(initialValue = SwipeableItemStatus.HIDE)

    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                thresholds = {
                        from, to ->
                    if (from == SwipeableItemStatus.HIDE) {
                        FractionalThreshold(0.3f)
                    } else {
                        FractionalThreshold(0.5f)
                    }
                }
            )
    ) {

        // 背景的两个按钮：删除、重命名
        Row (modifier = Modifier.matchParentSize()) {
            // 每个文件用一个单独的 Card 组件显示
            Card(
                modifier = Modifier.height(70.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {

                    // 文件删除按钮
                    Row (
                        modifier = Modifier.fillMaxHeight().wrapContentWidth().clip(RoundedCornerShape(12.dp))
                            .clickable {
                                if (item is NoteFile || (item is Dir && item.isEmpty())) {
                                    FileUtil.deleteFileEntry(item.fileId, context)
                                } else {
                                    Toast.makeText(context, "目录\" ${item.fileName} \"内部有其他文件，不能删除", Toast.LENGTH_SHORT).show()
                                }
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RecycleIcon(Modifier.padding(start = 10.dp).size(18.dp))
                        Text("删除", fontSize = 10.sp, modifier = Modifier.padding(start = 4.dp, end = 10.dp))
                    }

                    // 文件重命名按钮
                    Row (
                        modifier = Modifier.fillMaxHeight().wrapContentWidth().clip(RoundedCornerShape(12.dp))
                            .clickable {
//                                FileUtil.setRenameRequest(item.fileId)
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        AlterIcon(Modifier.padding(start = 10.dp).size(18.dp))
                        Text("重命名", fontSize = 10.sp, modifier = Modifier.padding(start = 4.dp, end = 10.dp))
                    }
                }
            }
        }

        // 前景可左滑的按钮
        Row (modifier = Modifier.offset { IntOffset(swipeableState.offset.value.toInt(), 0) }) {
            // 每个文件用一个单独的 Card 组件显示
            Card(
                modifier = Modifier.height(70.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                // 点击目录项会切换当前展示的目录
                Box(modifier = Modifier.fillMaxSize().clickable {
                    // 切换当前操作目录
                    if (item is Dir) {
                        FileUtil.updateDirectory(item.fileId)
                    }
                }) {
                    // 左上角显示 文件类型图标 以及 文件名 的部分
                    Row(modifier = Modifier.align(Alignment.TopStart).padding(start = 14.dp)) {
                        if (item is Dir) FolderIcon(
                            Modifier.padding(
                                top = 12.dp,
                            ).size(18.dp)
                        ) else FileIcon(
                            Modifier.padding(top = 12.dp).size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = item.fileName,
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }

                    // 左下角显示 文件修改时间 的部分
                    Text(
                        "修改于 ${item.lastModifiedTime}",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 10.dp, start = 14.dp)
                    )

                }
            }
        }

    }

}