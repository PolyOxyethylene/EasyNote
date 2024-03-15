package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oxyethylene.easynote.common.arrays.labSettingList
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : LabSettingPageUI.java
 * @createTime   : 2024/3/15 23:28
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 实验性功能界面
 */
@Composable
fun LabSettingPageUI () {

    Column {

        SimpleTitleBar(
            title = "实验性功能"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            SettingSubList(labSettingList)

        }

    }
    
}
