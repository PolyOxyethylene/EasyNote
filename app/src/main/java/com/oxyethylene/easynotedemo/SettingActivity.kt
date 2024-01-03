package com.oxyethylene.easynotedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.oxyethylene.easynotedemo.domain.testSettings
import com.oxyethylene.easynotedemo.ui.settingactivity.SettingList
import com.oxyethylene.easynotedemo.ui.settingactivity.SettingPageTopBar
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme

class SettingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    Column {
                        SettingPageTopBar("设置", Modifier)
                        SettingList(testSettings.SettingList, Modifier)
                    }
                }
            }
        }
    }
}

