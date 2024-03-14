package com.oxyethylene.easynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.oxyethylene.easynote.ui.settingactivity.SettingPageArea
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme

class SettingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EasyNoteTheme {
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

