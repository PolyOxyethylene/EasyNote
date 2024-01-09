package com.oxyethylene.easynotedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.oxyethylene.easynotedemo.ui.editactivity.EditPageArea
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme
import com.oxyethylene.easynotedemo.util.NoteUtil

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Toast.makeText(this, "文章路径：${NoteUtil.getPath()}", Toast.LENGTH_LONG).show()

        NoteUtil.noteContent = NoteUtil.loadFile(this)

        setContent {
            EasyNoteTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    EditPageArea()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        NoteUtil.saveFile(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NoteUtil.saveFile(this)
    }

    override fun onStop() {
        super.onStop()
        NoteUtil.saveFile(this)
    }

}