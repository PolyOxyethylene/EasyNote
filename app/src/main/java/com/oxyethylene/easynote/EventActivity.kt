package com.oxyethylene.easynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.oxyethylene.easynote.ui.eventactivity.EventInfoPageArea
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.viewmodel.EventViewModel
import com.oxyethylene.easynote.viewmodel.factory.EventViewModelFactory

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
