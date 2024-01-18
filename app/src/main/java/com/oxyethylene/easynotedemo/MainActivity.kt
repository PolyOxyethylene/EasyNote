package com.oxyethylene.easynotedemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.ComponentActivity
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
import androidx.lifecycle.ViewModelProvider
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynotedemo.database.AppDatabase
import com.oxyethylene.easynotedemo.ui.components.MainPageNavBar
import com.oxyethylene.easynotedemo.ui.mainactivity.EventPageArea
import com.oxyethylene.easynotedemo.ui.mainactivity.FolderMenuArea
import com.oxyethylene.easynotedemo.ui.mainactivity.TopMenuBar
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme
import com.oxyethylene.easynotedemo.util.DIRECTORY_INIT_SUCCESS
import com.oxyethylene.easynotedemo.util.EVENTLIST_INIT_SUCCESS
import com.oxyethylene.easynotedemo.util.EVENT_UPDATE_SUCCESS
import com.oxyethylene.easynotedemo.util.EventUtil.initEventList
import com.oxyethylene.easynotedemo.util.EventUtil.initEventUtil
import com.oxyethylene.easynotedemo.util.EventUtil.updateEventList
import com.oxyethylene.easynotedemo.util.FILE_DELETE_SUCCESS
import com.oxyethylene.easynotedemo.util.FILE_RENAME_SUCCESS
import com.oxyethylene.easynotedemo.util.FileUtil
import com.oxyethylene.easynotedemo.util.FileUtil.initDirectory
import com.oxyethylene.easynotedemo.util.FileUtil.initFileUtil
import com.oxyethylene.easynotedemo.util.FileUtil.updateDirectory
import com.oxyethylene.easynotedemo.util.MainPageRouteConf
import com.oxyethylene.easynotedemo.viewmodel.MainViewModel
import com.oxyethylene.easynotedemo.viewmodel.factory.MainViewModelFactory

class MainActivity : ComponentActivity() {

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
                FILE_RENAME_SUCCESS -> {
                    updateDirectory()        // 刷新当前目录
                }
                EVENT_UPDATE_SUCCESS -> {
                    updateEventList()
                }
                EVENTLIST_INIT_SUCCESS -> {
                    updateEventList()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MainViewModelFactory(FileUtil.getCurrentDir())).get(MainViewModel::class.java)

        database = AppDatabase.getDatabase(this)

        // 初始化工具类 FileUtil
        initFileUtil(viewModel, database, handler)
        initEventUtil(viewModel, database, handler)

        // 初始化目录结构
        initDirectory()
        initEventList()

        DialogX.init(this)

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
//                    Box {
                        // 控制页面的导航
//                        val navController = rememberNavController()

//                        Column(
//                            Modifier.align(Alignment.TopCenter).padding(bottom = 55.dp)
//                        ) {


//                            NavHost(
//                                navController = navController,
//                                startDestination = MainPageRouteConf.FOLDER
//                            ) {
//                                 目录界面
//                                composable(route = MainPageRouteConf.FOLDER) {

//                                }
//                                // 事件界面
//                                composable(route = MainPageRouteConf.EVENT) {
//                                    EventPageArea(Modifier, viewModel)
//                                }
//                            }

//                        }

                    val currentPage = rememberSaveable { mutableStateOf(MainPageRouteConf.FOLDER) }

                    Box {
                        Column(
                            Modifier.align(Alignment.TopCenter).padding(bottom = 55.dp)
                        ) {
                            TopMenuBar()
                            Crossfade(
                                targetState = currentPage.value,
                                animationSpec = tween(durationMillis = 250), label = ""
                            ) {
                                page ->
                                when (page) {
                                    MainPageRouteConf.FOLDER -> FolderMenuArea(Modifier, viewModel)
                                    MainPageRouteConf.EVENT -> EventPageArea(Modifier, viewModel)
                                }
                            }
                        }

                        MainPageNavBar(currentPage, Modifier.align(Alignment.BottomCenter))
                    }


                }
            }

        }

    }

}

