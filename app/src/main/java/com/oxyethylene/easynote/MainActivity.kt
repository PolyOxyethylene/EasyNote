package com.oxyethylene.easynote

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.baidu.location.LocationClient
import com.drake.net.NetConfig
import com.drake.net.okhttp.setErrorHandler
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
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
import com.oxyethylene.easynote.database.AppDatabase
import com.oxyethylene.easynote.errorhandler.GPTErrorHandler
import com.oxyethylene.easynote.ui.mainactivity.EventPageArea
import com.oxyethylene.easynote.ui.mainactivity.FolderMenuArea
import com.oxyethylene.easynote.ui.mainactivity.TopMenuBar
import com.oxyethylene.easynote.ui.mainactivity.callPermissionCheck
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme
import com.oxyethylene.easynote.util.EventUtil.initEventList
import com.oxyethylene.easynote.util.EventUtil.initEventUtil
import com.oxyethylene.easynote.util.EventUtil.updateEventList
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.FileUtil.initDirectory
import com.oxyethylene.easynote.util.FileUtil.initFileUtil
import com.oxyethylene.easynote.util.FileUtil.updateDirectory
import com.oxyethylene.easynote.util.GPTUtil
import com.oxyethylene.easynote.util.NlpUtil
import com.oxyethylene.easynote.util.NoteUtil.initNoteUtil
import com.oxyethylene.easynote.viewmodel.MainViewModel
import com.oxyethylene.easynote.viewmodel.factory.MainViewModelFactory
import kotlinx.coroutines.launch
import java.util.Properties


class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var database: AppDatabase

    private val handler = object : Handler(Looper.getMainLooper()) {
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

    @SuppressLint("UnrememberedMutableInteractionSource")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MainViewModelFactory(FileUtil.getCurrentDir())).get(MainViewModel::class.java)

        database = AppDatabase.getDatabase(this)

        checkPermission(this)

        // 初始化工具类
        initFileUtil(viewModel, database, handler)
        initNoteUtil(database)
        initEventUtil(viewModel, database, handler)

        // 初始化定位工具
        val prop = Properties();
        prop.load(applicationContext.assets.open("auth.properties"))
        LocationClient.setKey(prop.getProperty("BD_MAP_SDK"))
        LocationClient.setAgreePrivacy(true)


        // 初始化默认备份目录
        FileUtil.initDefaultBackupDir()

        // 初始化 Net 全局配置
        NetConfig.initialize {
            connectTimeout(15, java.util.concurrent.TimeUnit.MINUTES)
            setErrorHandler(GPTErrorHandler())
        }

        // 初始化 hanlp 工具类
        NlpUtil.init(this)
        // 初始化 gpt 工具类
        GPTUtil.init(this)

        // 初始化目录结构
        initDirectory()
        initEventList()

        // 初始化 DialogX
        DialogX.init(applicationContext)
        DialogX.autoShowInputKeyboard = false
        DialogX.cancelableTipDialog = false

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    val pageState = rememberPagerState(initialPage = 0, pageCount = { 2 })

                    val coroutineScope = rememberCoroutineScope()

                    Column {
                        // 标题栏
                        TopMenuBar {
                            Text(
                                text = "文档",
                                color = if (pageState.currentPage == 0) Color.DarkGray else Color.LightGray,
                                fontSize = 16.sp,
                                fontWeight = if (pageState.currentPage == 0) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.clickable (
                                    onClick = {
                                        if (pageState.currentPage == 1) {
                                            coroutineScope.launch {
                                                pageState.animateScrollToPage(0)
                                            }
                                        }
                                    },
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                )
                            )
                            Text(
                                text = "事件",
                                color = if (pageState.currentPage == 1) Color.DarkGray else Color.LightGray,
                                fontSize = 16.sp,
                                fontWeight = if (pageState.currentPage == 1) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(start = 20.dp)
                                    .clickable (
                                        onClick = {
                                            if (pageState.currentPage == 0) {
                                                coroutineScope.launch {
                                                    pageState.animateScrollToPage(1)
                                                }
                                            }
                                        },
                                        indication = null,
                                        interactionSource = MutableInteractionSource()
                                    )
                            )
                        }

                        HorizontalPager(
                            state = pageState
                        ) {
                            page ->
                            when (page) {
                                0 -> FolderMenuArea(Modifier, viewModel)
                                1 -> EventPageArea(Modifier, viewModel)
                            }
                        }

                    }

                }
            }

        }

    }

    override fun onRestart() {
        super.onRestart()
        checkPermission(this)
    }

    fun checkPermission (context: Context) {

        val requestList= ArrayList<String>().apply {
            add(Permission.ACCESS_FINE_LOCATION)
            add(Permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S_V2) {
                add(Permission.MANAGE_EXTERNAL_STORAGE)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Permission.READ_MEDIA_AUDIO)
                add(Permission.READ_MEDIA_VIDEO)
                add(Permission.READ_MEDIA_IMAGES)
            }
        }

        XXPermissions.with(context)
            .permission(requestList)
            .request(
                object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {

                    }

                    override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                        callPermissionCheck(context, true)
                    }
                }
            )

    }

}

