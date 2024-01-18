package com.oxyethylene.easynotedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynotedemo.ui.appinfoactivity.InfoPageArea
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme

class AppInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DialogX.init(this)

        DialogX.globalStyle = MIUIStyle()

        setContent {
            EasyNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    InfoPageArea()
                }
            }
        }
    }
}