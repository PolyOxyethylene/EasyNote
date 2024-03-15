package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oxyethylene.easynote.common.arrays.editorSettingList
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : EditorSettingPageUI.java
 * @createTime   : 2024/3/15 23:46
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 编辑器设置界面
 */
@Composable
fun EditorSettingPageUI () {

    Column {

        SimpleTitleBar(
            title = "编辑器设置"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            SettingSubList(editorSettingList)

        }

    }

}