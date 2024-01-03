package com.oxyethylene.easynotedemo.ui.mainactivity

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynotedemo.R
import com.oxyethylene.easynotedemo.domain.Dir
import com.oxyethylene.easynotedemo.domain.FileType
import com.oxyethylene.easynotedemo.domain.NoteFile
import com.oxyethylene.easynotedemo.ui.components.AlterIcon
import com.oxyethylene.easynotedemo.ui.components.BackIcon
import com.oxyethylene.easynotedemo.ui.components.FileIcon
import com.oxyethylene.easynotedemo.ui.components.FolderIcon
import com.oxyethylene.easynotedemo.ui.components.InputText
import com.oxyethylene.easynotedemo.ui.components.MoreIcon
import com.oxyethylene.easynotedemo.ui.components.MultiSelectIcon
import com.oxyethylene.easynotedemo.ui.components.RecycleIcon
import com.oxyethylene.easynotedemo.ui.components.SearchIcon
import com.oxyethylene.easynotedemo.ui.components.SettingIcon
import com.oxyethylene.easynotedemo.ui.theme.GreyLighter
import com.oxyethylene.easynotedemo.ui.theme.SkyBlue
import com.oxyethylene.easynotedemo.util.FileUtil
import com.oxyethylene.easynotedemo.util.FileUtil.isRootDir
import com.oxyethylene.easynotedemo.util.FileUtil.root
import com.oxyethylene.easynotedemo.util.FileUtil.toParentDir
import com.oxyethylene.easynotedemo.util.imeVisible
import com.oxyethylene.easynotedemo.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo
 * @ClassName    : MainFolderPageUI.kt
 * @createTime   : 2023/12/12 1:35
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 文件目录界面
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FolderMenuArea(
    modifier: Modifier,
    viewModel: MainViewModel = MainViewModel(),
) {

    val context = LocalContext.current

    var dialogType = rememberSaveable { mutableStateOf(ADD_FILE_DIALOG) }

    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val imeVisible = imeVisible().value

    if (!state.isVisible) {
        LocalFocusManager.current.clearFocus()
    }

    var FAB1expended by rememberSaveable { mutableStateOf(true) }

    var FAB2expended by rememberSaveable { mutableStateOf(false) }

    FileDialog(
        type = dialogType,
        _sheetState = state,
        onCloseRequest = { scope.launch { state.hide() } },
        onRenameRequest = {
            dialogType.value = RENAME_FILE_DIALOG
            scope.launch { state.show() }
        },
        viewModel = viewModel
    ) {

        TopMenuBar(Modifier)

        FileControllerBar(viewModel)

        Box (modifier = Modifier) {
            FileList(viewModel, {
                dialogType.value = ALTER_FILE_DIALOG
                scope.launch { state.show() }
            })

            if (false) Column (
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.scale(0.8f)
//                        .border(2.dp, SkyBlue, CircleShape)
                    ,
                    icon = { FileIcon(Modifier.size(18.dp)) },
                    text = { Text("新建文章", color = Color.White) },
                    shape = CircleShape,
                    containerColor = Color.DarkGray,
                    expanded = FAB1expended,
                    onClick = {
                        if (FAB2expended) {
                            FAB1expended = true
                            FAB2expended = false
                        }
                    }
                )

                Spacer(Modifier.size(16.dp))

                ExtendedFloatingActionButton(
                    modifier = Modifier.scale(0.8f)
//                        .border(2.dp, SkyBlue, CircleShape)
                    ,
                    icon = { FolderIcon(Modifier.size(18.dp)) },
                    text = { Text("新建目录", color = Color.White) },
                    expanded = FAB2expended,
                    shape = CircleShape,
                    containerColor = Color.DarkGray,
                    onClick = {
                        if (FAB1expended) {
                            FAB2expended = true
                            FAB1expended = false
                        }
                    }
                )
            }


            /**
             *  悬浮按钮，功能是新建 文件 或者 目录
             */

            /**
             *  悬浮按钮，功能是新建 文件 或者 目录
             */
            FloatingActionButton(
                modifier = modifier.navigationBarsPadding().padding(end = 24.dp, bottom = 40.dp)
                    .size(60.dp)
                    .align(Alignment.BottomEnd),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
                shape = CircleShape,
                containerColor = Color.DarkGray,
                contentColor = Color.White,
                onClick = {
                    dialogType.value = ADD_FILE_DIALOG
                    scope.launch { state.show() }
                }) {
                Icon(Icons.Default.Add, "新建文件或者目录的按钮")
            }
        }
    }

    // 统一处理该页面的
    BackHandler {
        if (state.isVisible && !imeVisible) {
            scope.launch { state.hide() }
        } else if (!isRootDir()) {
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
fun TopMenuBar(modifier: Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier.statusBarsPadding().fillMaxWidth().padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /**
         *  左上角的设置按钮
         *  TODO：添加跳转到设置的 Activity
         */
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.DarkGray
            ),
            onClick = {
                Toast.makeText(context, "打开侧边菜单", Toast.LENGTH_SHORT).show()
                val intent = Intent("com.oxyethylene.SETTING")
                context.startActivity(intent)
            }) {
            SettingIcon(Modifier.size(18.dp).align(Alignment.CenterVertically))
        }

        // 标题
//        Text(text = "文档", color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Image(painter = painterResource(R.drawable.app_title), "应用标题", modifier = Modifier.height(50.dp).wrapContentWidth(), alignment = Alignment.Center)

        /**
         *  顶部右侧的更多选项按钮，暂时没想到做什么功能
         *  TODO：后续不用的话可能删除
         */
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.DarkGray
            ),
            onClick = {
                Toast.makeText(context, "打开更多菜单", Toast.LENGTH_SHORT).show()
            }) {
            MoreIcon(Modifier.size(24.dp), Color.DarkGray)
        }
    }

}


/**
 *  此为文件列表上方的指示条，负责：
 *  1. 返回上一级目录
 *  2. 显示当前目录名称
 *  TODO：3. 搜索某个目录
 *  TODO：4. 文件的多选操作
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
                onClick = {
                    Toast.makeText(context, "返回上一级目录", Toast.LENGTH_SHORT).show()
                    if (!isRootDir()) toParentDir()
                }
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
                animationSpec = tween(durationMillis = 250)
            ) {
                Text(it.fileName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Row {
            /**
             *  搜索功能按钮
             *  TODO：搜索功能还没添加
             */
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray
                ),
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(context, "多选", Toast.LENGTH_SHORT).show()
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
fun FileList(viewModel: MainViewModel = MainViewModel(), onAlterRequest: () -> Unit) {

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
        animationSpec = tween(durationMillis = 250)
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
        animationSpec = tween(durationMillis = 250)
    ) {

        // 文件列表
        LazyColumn() {
            itemsIndexed(it.getFileList()) { index, item ->
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
                                Toast.makeText(
                                    context,
                                    "文件: ${item.fileName}, id: ${item.fileId}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // 切换当前操作目录
                                if (item is Dir) {
                                    FileUtil.updateDirectory(item.fileId)
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
                                "修改于 ${item.lastModifiedTime}",
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
        }

    }

}

// 以下常量用于区分底部对话框的类型
// 表示添加新文件的对话框
const val ADD_FILE_DIALOG = 1

// 表示修改文件的对话框
const val ALTER_FILE_DIALOG = 2

// 表示重命名文件的对话框
const val RENAME_FILE_DIALOG = 3

/**
 * 主页底部弹出的对话框，依照类型 type 分为 添加文件 或 修改文件 两种对话框
 * @param type 对话框的类型，1 为 添加文件的对话框， 2 为 修改文件操作的对话框， 3 为 重命名文件的对话框
 * @param _sheetState 用于改变 bottomSheet 状态
 * @param onCloseRequest 关闭对话框调用的方法
 * @param viewModel 主活动的 viewModel
 * @param content 布局中的内容
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FileDialog(
    type: MutableState<Int>,
    _sheetState: ModalBottomSheetState,
    onCloseRequest: () -> Unit,
    onRenameRequest: () -> Unit,
    viewModel: MainViewModel,
    content: @Composable () -> Unit
) {

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = _sheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        scrimColor = Color.Black.copy(alpha = 0.1F),
        sheetContent = {

            when (type.value) {

                ADD_FILE_DIALOG -> {
                    AddFileDialog(onCloseRequest)
                }

                ALTER_FILE_DIALOG -> {
                    AlterFileDialog(viewModel, onCloseRequest, onRenameRequest)
                }

                RENAME_FILE_DIALOG -> {
                    RenameFileDialog(viewModel, onCloseRequest)
                }
            }

        }
    ) {
        Column {
            content()
        }
    }

}

/**
 *  描述添加文件的对话框
 *  @param onCloseRequest 关闭对话框调用的方法
 */
@Composable
fun AddFileDialog(onCloseRequest: () -> Unit) {

    val context = LocalContext.current

    var newFileName = rememberSaveable { mutableStateOf("") }

    var selectType by rememberSaveable { mutableStateOf(1) }

    Column(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "新建文件夹",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputText(
                Modifier.padding(start = 40.dp, end = 40.dp).fillMaxWidth().height(55.dp),
                newFileName,
                "请输入名称"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                OutlinedButton(
                    modifier = Modifier.scale(0.8f),
                    onClick = {selectType = 1},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectType == 1) SkyBlue else Color.White
                    )
                ) {
                    FolderIcon(
                        Modifier.size(16.dp).align(Alignment.CenterVertically))
                    Text("目录", fontSize = 14.sp,
                        color = if (selectType == 1) Color.White else Color.DarkGray,
                        modifier = Modifier.padding(start = 4.dp).align(Alignment.CenterVertically))
                }

                OutlinedButton(
                    modifier = Modifier.scale(0.8f),
                    onClick = {selectType = 2},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectType == 2) SkyBlue else Color.White
                    )
                ) {
                    FolderIcon(
                        Modifier.size(16.dp).align(Alignment.CenterVertically))
                    Text("文件", fontSize = 14.sp,
                        color = if (selectType == 2) Color.White else Color.DarkGray,
                        modifier = Modifier.padding(start = 4.dp).align(Alignment.CenterVertically))
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreyLighter),
                    onClick = onCloseRequest
                ) {
                    Text("取消", color = Color.Black)
                }

                Button(
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            0xff0c84ff
                        )
                    ),
                    onClick = {
                        if (newFileName.value.isBlank() || newFileName.value.isEmpty()) {
                            Toast.makeText(
                                context,
                                "请输入包含非空字符的文件名",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // 创建文章
                            FileUtil.createFile(newFileName.value, selectType)
                            onCloseRequest.invoke()
//                            if (selectType == 2) toEditPage()
                        }
                    }) {
                    Text("创建")
                }
            }
        }
    }

}

/**
 *  描述修改文件操作的对话框
 *  @param viewModel 获取主活动的 viewModel
 *  @param onCloseRequest 关闭对话框调用的方法
 *  @param onRenameRequest 打开重命名对话框调用的方法
 */
@Composable
fun AlterFileDialog(viewModel: MainViewModel, onCloseRequest: () -> Unit, onRenameRequest: () -> Unit) {

    val currentSelectedFile by viewModel.currentSelectedFile.observeAsState(root)

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            if (currentSelectedFile.type == FileType.DIRECTORY) {
                FolderIcon(Modifier.size(22.dp))
            } else {
                FileIcon(Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                currentSelectedFile.fileName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Column(
            modifier = Modifier.padding(start = 22.dp, end = 22.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(12.dp))
                    .clickable {
                        if (currentSelectedFile is NoteFile || (currentSelectedFile is Dir && (currentSelectedFile as Dir).isEmpty())) {
                            FileUtil.deleteFileEntry(currentSelectedFile.fileId)
                        } else {
                            Toast.makeText(context, "目录\" ${currentSelectedFile.fileName} \"内部有其他文件，不能删除", Toast.LENGTH_SHORT).show()
                        }
                        onCloseRequest.invoke()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                RecycleIcon(Modifier.padding(start = 16.dp).size(24.dp))

                Spacer(Modifier.size(10.dp))

                Text("删除", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            }

            Row (
                modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onCloseRequest.invoke()
                        onRenameRequest.invoke()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                AlterIcon(Modifier.padding(start = 16.dp).size(24.dp))

                Spacer(Modifier.size(10.dp))

                Text("重命名文件", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            }
        }

    }

}

/**
 *  重命名文件的对话框
 * @param viewModel 主活动的 viewModel
 */
@Composable
fun RenameFileDialog (viewModel: MainViewModel, onCloseRequest: () -> Unit) {

    var newFileName = rememberSaveable { mutableStateOf("") }

    val currentSelectedFile by viewModel.currentSelectedFile.observeAsState(root)

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth().height(220.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "重命名",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 12.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth().height(140.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputText(
                Modifier.padding(start = 40.dp, end = 40.dp).fillMaxWidth().height(55.dp),
                newFileName,
                currentSelectedFile.fileName
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreyLighter),
                    onClick = onCloseRequest
                ) {
                    Text("取消", color = Color.Black)
                }

                Button(
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            0xff0c84ff
                        )
                    ),
                    onClick = {
                        if (newFileName.value.isBlank() || newFileName.value.isEmpty()) {
                            Toast.makeText(
                                context,
                                "请输入包含非空字符的文件名",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // 重命名文件
                            FileUtil.renameFile(currentSelectedFile.fileId, newFileName.value)
                            onCloseRequest.invoke()
                        }
                    }) {
                    Text("修改")
                }
            }
        }
    }

}