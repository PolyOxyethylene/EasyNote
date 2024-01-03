package com.oxyethylene.easynotedemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.oxyethylene.easynotedemo.database.AppDatabase
import com.oxyethylene.easynotedemo.ui.mainactivity.FolderMenuArea
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme
import com.oxyethylene.easynotedemo.util.DIRECTORY_INIT_SUCCESS
import com.oxyethylene.easynotedemo.util.FILE_DELETE_SUCCESS
import com.oxyethylene.easynotedemo.util.FILE_RENAME_SUCCESS
import com.oxyethylene.easynotedemo.util.FileUtil
import com.oxyethylene.easynotedemo.util.FileUtil.initDirectory
import com.oxyethylene.easynotedemo.util.FileUtil.initFileUtil
import com.oxyethylene.easynotedemo.util.FileUtil.updateDirectory
import com.oxyethylene.easynotedemo.util.FileUtil.updateSelectedFile
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
                    updateDirectory()        // 刷新当前目录
                }
                FILE_RENAME_SUCCESS -> {
                    updateSelectedFile(msg.arg1)
                    updateDirectory()
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

        // 初始化目录结构
        initDirectory()

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    FolderMenuArea(Modifier, viewModel)
                }
            }

        }

    }

}

