package com.oxyethylene.easynote.ui.mainactivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.InputInfo
import com.kongzue.dialogx.util.TextInfo
import com.leinardi.android.speeddial.compose.FabWithLabel
import com.leinardi.android.speeddial.compose.SpeedDial
import com.leinardi.android.speeddial.compose.SpeedDialState
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.domain.Dentry
import com.oxyethylene.easynote.domain.Dir
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.ui.components.BackIcon
import com.oxyethylene.easynote.ui.components.FolderIcon
import com.oxyethylene.easynote.ui.components.MoreIcon
import com.oxyethylene.easynote.ui.components.MultiSelectIcon
import com.oxyethylene.easynote.ui.components.SearchIcon
import com.oxyethylene.easynote.ui.components.SettingIcon
import com.oxyethylene.easynote.ui.components.TitleBar
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.FileUtil.isRootDir
import com.oxyethylene.easynote.util.FileUtil.toParentDir
import com.oxyethylene.easynote.util.SearchBoxUtil
import com.oxyethylene.easynote.viewmodel.MainViewModel
import me.saket.cascade.CascadeDropdownMenu

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote
 * @ClassName    : FolderPageUI.kt
 * @createTime   : 2023/12/12 1:35
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 文件目录界面
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun FolderMenuArea(
    modifier: Modifier,
    viewModel: MainViewModel = MainViewModel(),
) {

    val context = LocalContext.current

    Column (modifier) {

        FileControllerBar(viewModel)

        var speedDialState by rememberSaveable { mutableStateOf(SpeedDialState.Collapsed) }

        Box (
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures {
                    if (speedDialState.isExpanded()) {
                        speedDialState = speedDialState.toggle()
                    }
                }
            }
        ) {
            // 文件列表
            FileList(viewModel)

            /**
             *  悬浮按钮，功能是新建 文件 或者 目录
             */
//            Button(
//                modifier = Modifier.navigationBarsPadding().padding(bottom = 10.dp)
//                    .align(Alignment.BottomCenter),
//                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
//                onClick = {
//                    onAddFileFABClick(context)
//                }) {
//                Icon(Icons.Default.Add, "新建文件或者目录的按钮")
//            }

            SpeedDial(
                modifier = Modifier.navigationBarsPadding().padding(bottom = 20.dp, end = 30.dp).align(Alignment.BottomEnd),
                fabElevation = androidx.compose.material.FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
                state = speedDialState,
                onFabClick = { expanded ->
                    speedDialState = speedDialState.toggle()
                },
                fabOpenedBackgroundColor = Color.Black,
                fabClosedBackgroundColor = Color.Black,
                fabOpenedContentColor = Color.White,
                fabClosedContentColor = Color.White
            ) {
                item {
                    FabWithLabel(
                        onClick = {
                            speedDialState = speedDialState.toggle()
                            onAddFileFABClick(FileType.FILE.ordinal, context)
                        },
                        fabElevation = androidx.compose.material.FloatingActionButtonDefaults.elevation(defaultElevation = 1.dp),
                        fabBackgroundColor = Color.White,
                        labelContainerElevation = 1.dp,
                        labelContent = { Text(text = "新建文章") },
                    ) {
                        Icon(painter = painterResource(R.mipmap.ic_add_note), contentDescription = null, modifier = Modifier.size(22.dp))
                    }
                }
                item {
                    FabWithLabel(
                        onClick = {
                            speedDialState = speedDialState.toggle()
                            onAddFileFABClick(FileType.DIRECTORY.ordinal, context)
                        },
                        fabElevation = androidx.compose.material.FloatingActionButtonDefaults.elevation(defaultElevation = 1.dp),
                        fabBackgroundColor = Color.White,
                        labelContainerElevation = 1.dp,
                        labelContent = { Text(text = "新建目录") },
                    ) {
                        Icon(painter = painterResource(R.mipmap.ic_add_folder), contentDescription = null, modifier = Modifier.size(24.dp))
                    }
                }
            }

        }
    }

    // 统一处理该页面的
    BackHandler {
        if (!isRootDir()) {
            toParentDir()
        } else {
            (context as Activity).finish()
        }
    }

}


/**
 *  顶部的选项栏
 */
@Composable
fun TopMenuBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    TitleBar(
        modifier = modifier,
        leftContent = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray
                ),
                onClick = {
                    val intent = Intent("com.oxyethylene.SETTING")
                    context.startActivity(intent)
                }) {
                SettingIcon(Modifier.size(18.dp).align(Alignment.CenterVertically))
            }
        },
        rightContent = {
            /**
             *  顶部右侧的更多选项按钮，暂时没想到做什么功能
             *  TODO：后续不用的话可能删除
             */
            MoreFeatureButton()
        }
    ) {
        // 标题
        Text(text = "文档", color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }

}


/**
 *  此为文件列表上方的指示条，负责：
 *  1. 返回上一级目录
 *  2. 显示当前目录名称
 *
 *  TODO 3. 搜索某个目录
 *
 *  TODO 4. 文件的多选操作
 *
 *  @param viewModel 主活动的 viewModel，用于显示当前目录的名称
 */
@Composable
fun FileControllerBar(viewModel: MainViewModel) {

    val context = LocalContext.current
    // 当前的目录
    val currentFolder by viewModel.currentFolder.observeAsState(FileUtil.getCurrentDir())

    // 最上方的目录指示条
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row {

            /**
             *  返回上一级目录
             */
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray
                ),
                onClick = { if (!isRootDir()) toParentDir() }
            ) {
                BackIcon(Modifier.size(20.dp).align(Alignment.CenterVertically))
            }
            Spacer(Modifier.size(12.dp))
            FolderIcon(Modifier.align(Alignment.CenterVertically).size(22.dp))
            Spacer(Modifier.size(6.dp))

            // 当前目录的名字
            Crossfade(
                targetState = currentFolder,
                modifier = Modifier.align(Alignment.CenterVertically),
                animationSpec = tween(durationMillis = 250), label = ""
            ) {
                Text(it.fileName, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
            }
        }

        Row {
            /**
             *  搜索功能按钮
             *  TODO：搜索功能待优化
             */
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray
                ),
                modifier = Modifier.wrapContentWidth(),
                onClick = {
//                    Toast.makeText(context, "搜索(还没做)", Toast.LENGTH_SHORT).show()
                    SearchBoxUtil.show()
                }
            ) {
                SearchIcon(Modifier.size(18.dp).align(Alignment.CenterVertically))
            }

            /**
             *  多选按钮，删除文件用的
             *  TODO：做完基础功能再做这个吧
             */
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray
                ),
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    Toast.makeText(context, "多选(还没做)", Toast.LENGTH_SHORT).show()
                }
            ) {
                MultiSelectIcon(Modifier.size(20.dp).align(Alignment.CenterVertically))
            }
        }

    }

}

/**
 *  当前目录的文件列表
 *  @param viewModel 主活动的 viewModel，用于显示文件列表
 *  @param onAlterRequest 当点击文件右侧修改按钮时应执行的方法
 */
@Composable
fun FileList(viewModel: MainViewModel = MainViewModel()) {

    val context = LocalContext.current

    // 当前的目录
    val currentFolder by viewModel.currentFolder.observeAsState(FileUtil.getCurrentDir())

    /**
     *  文件列表
     *  TODO：现在只是做了个样子，编辑功能还没做
     */
    Crossfade(
        targetState = currentFolder,
        modifier = Modifier.fillMaxSize().wrapContentWidth(Alignment.CenterHorizontally),
        animationSpec = tween(durationMillis = 250), label = ""
    ) {

        if (it.getFileList().size == 0) {
            Text(
                text = "当前目录没有文件",
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 30.dp)
            )
        }
    }

    Crossfade(
        targetState = currentFolder,
        modifier = Modifier.fillMaxSize(),
        animationSpec = tween(durationMillis = 250), label = ""
    ) {

        // 文件列表
        LazyColumn() {
            items(it.getFileList()) {
                item -> ListItem(item, context) { onAlterButtonClick(item, context) }
            }
        }

    }

}

/**
 *  点击目录界面添加按钮执行的事件
 */
fun onAddFileFABClick (context: Context) {

    BottomMenu.build(MIUIStyle())
        .setTitle("新建文件")
        .setMenuList(arrayOf("目录", "文章"))
        .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(false) {
            override fun getIcon(dialog: BottomMenu?, index: Int, menuText: String?): Int {
                when(menuText) {
                    "目录" -> return R.drawable.folder_icon
                    "文章" -> return R.drawable.note_icon
                }
                return 0
            }
        })
        .setOnMenuItemClickListener(object : OnMenuItemClickListener<BottomMenu> {
            override fun onClick(dialog: BottomMenu?, text: CharSequence?, index: Int): Boolean {
                InputDialog.build(MIUIStyle())
                    .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()).setMultipleLines(true))
                    .setTitle(if (index == 0) "新建目录" else "新建文章")
                    .setMessage("请输入名称")
                    .setCancelButton("取消")
                    .setOkButton("创建")
                    .setOkButtonClickListener(OnInputDialogButtonClickListener {
                            dialog, v, inputStr ->
                        if (inputStr.isBlank() || inputStr.isEmpty()) {
                            PopNotification.build(MIUIStyle()).setMessage("请输入包含非空字符的文件名").show()
                        } else {
                            // 创建文章
                            FileUtil.createFile(inputStr.trim(), index + 1, context)
                        }
                        return@OnInputDialogButtonClickListener false
                    })
                    .show()
                return false
            }
        })
        .show()

}

/**
 *  点击目录界面添加按钮执行的事件
 */
fun onAddFileFABClick (fileType: Int, context: Context) {

    InputDialog.build(MIUIStyle())
        .setInputInfo(InputInfo().setCursorColor(SkyBlue.toArgb()).setMultipleLines(true))
        .setTitle(if (fileType == FileType.FILE.ordinal) "新建文章" else "新建目录")
        .setMessage("请输入名称")
        .setCancelButton("取消")
        .setOkButton("创建")
        .setOkButtonClickListener(OnInputDialogButtonClickListener {
                dialog, v, inputStr ->
            if (inputStr.isBlank() || inputStr.isEmpty()) {
                PopNotification.build(MIUIStyle()).setMessage("请输入包含非空字符的文件名").show()
            } else {
                // 创建文章
                FileUtil.createFile(inputStr.trim(), fileType, context)
            }
            return@OnInputDialogButtonClickListener false
        })
        .show()
}

/**
 *  当普通的列表项的修改按钮点击时执行的方法
 *  @param item 列表项要使用的类对象
 *  @param context 应用上下文
 */
fun onAlterButtonClick (item: Dentry, context: Context) {

    BottomMenu.build(MIUIStyle())
        .setMenuList(if(item is Dir) arrayOf("删除", "重命名") else arrayOf("删除", "重命名", "绑定事件", "解除绑定"))
        .setTitle(item.fileName)
        .setTitleTextInfo(TextInfo().setMaxLines(1).setShowEllipsis(true).setBold(true))
        .setOnMenuItemClickListener(object : OnMenuItemClickListener<BottomMenu> {
            override fun onClick(
                dialog: BottomMenu?,
                text: CharSequence?,
                index: Int
            ): Boolean {
                when (text) {
                    "删除" ->
                        if (item is Dir && !item.isEmpty()) {
                            PopNotification.build(MIUIStyle()).setMessage("目录 \"${item.fileName}\" 内部有其他文件，不能删除").show()
                        }
                        else if (item is NoteFile && item.eventId != 0) {
                            MessageDialog.build(MIUIStyle())
                                .setTitle("删除文章")
                                .setMessage("当前文章已绑定事件，是否删除?")
                                .setCancelButton("取消")
                                .setOkButton("确定")
                                .setOkButtonClickListener(
                                    OnDialogButtonClickListener {
                                        dialog, v ->
                                        EventUtil.unbindNote(item)
                                        FileUtil.deleteFileEntry(item.fileId, context)
                                        return@OnDialogButtonClickListener false
                                    }
                                ).show()
                        } else {
                            FileUtil.deleteFileEntry(item.fileId, context)
                        }
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
                                }
                                return@OnInputDialogButtonClickListener false
                            })
                            .show()
                    }
                    "绑定事件" -> {
                        showEventBindingDialog(item as NoteFile)
                    }
                    "解除绑定" -> {
                        if (EventUtil.unbindNote(item as NoteFile)) {
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
 * 主页右上角的更多选项菜单
 */
@Composable
fun MoreFeatureButton () {

    var expanded by remember { mutableStateOf(false) }

    Box{
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.DarkGray
            ),
            onClick = {expanded = true}
        ) {
            MoreIcon(Modifier.size(24.dp), Color.DarkGray)
        }
        CascadeDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(12.dp),
        ) {

            // 关键词选项
            DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_keywords), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "关键词", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                onClick = {
                    expanded = false
                    PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                },
                contentPadding = PaddingValues(10.dp)
            )

            // 导出文件选项
            DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_export), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "导出为", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                contentPadding = PaddingValues(10.dp),
                children = {

                    // 导出为 PDF
                    DropdownMenuItem(
                        leadingIcon = { Image(painter = painterResource(R.drawable.pdf_icon), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "导出为 PDF", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                            }
                        },
                        onClick = {
                            expanded = false
                            PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                        },
                        contentPadding = PaddingValues(10.dp)
                    )

                    // 导出为图片
                    DropdownMenuItem(
                        leadingIcon = { Image(painter = painterResource(R.drawable.image_icon), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "导出为图片", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                            }
                        },
                        onClick = {
                            expanded = false
                            PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                        },
                        contentPadding = PaddingValues(10.dp)
                    )

                    // 导出为纯文本
                    DropdownMenuItem(
                        leadingIcon = { Image(painter = painterResource(R.drawable.txt_icon), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "导出为 txt", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                            }
                        },
                        onClick = {
                            expanded = false
                            PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                        },
                        contentPadding = PaddingValues(10.dp)
                    )

                }
            )

            // 常见问题
            DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_qa), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Q&A", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                onClick = {
                    expanded = false
                    PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                },
                contentPadding = PaddingValues(10.dp)
            )

            // 权限检查
            DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_permission_check), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "应用权限检查", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                onClick = {
                    expanded = false
                    PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                },
                contentPadding = PaddingValues(10.dp)
            )
        }

    }

}