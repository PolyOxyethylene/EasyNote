package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oxyethylene.easynote.common.arrays.backupSettingList
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : BackupPageUI.java
 * @createTime   : 2024/3/15 23:48
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 备份界面
 */
@Composable
fun BackupPageUI () {

    Column {

        SimpleTitleBar(
            title = "备份与恢复"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            SettingSubList(backupSettingList)

        }

    }

}