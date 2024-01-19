package com.oxyethylene.easynotedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.oxyethylene.easynotedemo.ui.eventactivity.EventInfoPageArea
import com.oxyethylene.easynotedemo.ui.theme.BackGround
import com.oxyethylene.easynotedemo.ui.theme.EasyNoteTheme
import com.oxyethylene.easynotedemo.util.EventUtil
import com.oxyethylene.easynotedemo.viewmodel.EventViewModel
import com.oxyethylene.easynotedemo.viewmodel.factory.EventViewModelFactory

class EventActivity : ComponentActivity() {

    lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, EventViewModelFactory()).get(EventViewModel::class.java)

        val eventId = intent.extras?.getInt("event_id")

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    eventId?.let { EventUtil.getEvent(it)?.let { EventInfoPageArea(it) } }
                }
            }
        }
    }
}
