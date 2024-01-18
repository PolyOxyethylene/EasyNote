package com.oxyethylene.easynotedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kongzue.dialogx.DialogX
import com.oxyethylene.easynotedemo.ui.settingactivity.SettingPageArea
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme

class SettingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 DialogX
        DialogX.init(this)

        setContent {
            EasyNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    SettingPageArea()
                }
            }
        }
    }
}

