package com.oxyethylene.easynote

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.common.constant.DIRECTORY_INIT_SUCCESS
import com.oxyethylene.easynote.common.constant.EVENTLIST_INIT_SUCCESS
import com.oxyethylene.easynote.common.constant.EVENT_DELETE_SUCCESS
import com.oxyethylene.easynote.common.constant.EVENT_UPDATE_SUCCESS
import com.oxyethylene.easynote.common.constant.FILE_DELETE_SUCCESS
import com.oxyethylene.easynote.common.constant.FILE_RENAME_SUCCESS
import com.oxyethylene.easynote.common.constant.FILE_UPDATE_SUCCESS
import com.oxyethylene.easynote.common.constant.MainPageRouteConstant
import com.oxyethylene.easynote.database.AppDatabase
import com.oxyethylene.easynote.ui.components.MainPageNavBar
import com.oxyethylene.easynote.ui.mainactivity.EventPageArea
import com.oxyethylene.easynote.ui.mainactivity.FolderMenuArea
import com.oxyethylene.easynote.ui.mainactivity.TopMenuBar
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme
import com.oxyethylene.easynote.util.EventUtil.initEventList
import com.oxyethylene.easynote.util.EventUtil.initEventUtil
import com.oxyethylene.easynote.util.EventUtil.updateEventList
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.FileUtil.initDirectory
import com.oxyethylene.easynote.util.FileUtil.initFileUtil
import com.oxyethylene.easynote.util.FileUtil.updateDirectory
import com.oxyethylene.easynote.util.PermissionUtil
import com.oxyethylene.easynote.util.SearchBoxUtil
import com.oxyethylene.easynote.util.SearchBoxUtil.initSearchBoxUtil
import com.oxyethylene.easynote.viewmodel.MainViewModel
import com.oxyethylene.easynote.viewmodel.factory.MainViewModelFactory


class MainActivity : FragmentActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var database: AppDatabase

    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            // 在这里可以进行UI操作
            when (msg.what) {
                DIRECTORY_INIT_SUCCESS -> {
                    updateDirectory(0)  // 刷新根目录
                }
                FILE_DELETE_SUCCESS -> {
                    PopNotification.build(MIUIStyle()).setMessage("文件 \"${msg.obj}\" 删除成功").show()
                    updateDirectory()        // 刷新当前目录
                }
                FILE_RENAME_SUCCESS, FILE_UPDATE_SUCCESS -> {
                    updateDirectory()        // 刷新当前目录
                }
                EVENT_UPDATE_SUCCESS, EVENT_DELETE_SUCCESS, EVENTLIST_INIT_SUCCESS -> {
                    updateEventList()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MainViewModelFactory(FileUtil.getCurrentDir())).get(MainViewModel::class.java)

        database = AppDatabase.getDatabase(this)

        // 初始化工具类
        initFileUtil(viewModel, database, handler)
        initEventUtil(viewModel, database, handler)
        initSearchBoxUtil(handler)

        // 初始化目录结构
        initDirectory()
        initEventList()

        // 初始化 DialogX
        DialogX.init(this)
        DialogX.autoShowInputKeyboard = false

        // 进行权限检查/请求
        PermissionUtil.init(this)

        // 底部搜索框初始化
        handler.postDelayed({ SearchBoxUtil.init() }, 100)

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    val currentPage = rememberSaveable { mutableStateOf(MainPageRouteConstant.FOLDER) }

                    Box {
                        Column(
                            Modifier.align(Alignment.TopCenter).padding(bottom = 60.dp)
                        ) {
                            TopMenuBar()
                            Crossfade(
                                targetState = currentPage.value,
                                animationSpec = tween(durationMillis = 250), label = ""
                            ) {
                                page ->
                                when (page) {
                                    MainPageRouteConstant.FOLDER -> FolderMenuArea(Modifier, viewModel)
                                    MainPageRouteConstant.EVENT -> EventPageArea(Modifier, viewModel)
                                }
                            }
                        }

                        MainPageNavBar(currentPage, Modifier.align(Alignment.BottomCenter))
                    }

                }
            }

        }

    }

    override fun onRestart() {
        super.onRestart()
//        PermissionUtil.checkAgain(this)
    }

}

