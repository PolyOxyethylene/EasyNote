package com.oxyethylene.easynote.ui.commonactivity

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.domain.DialogSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList
import com.oxyethylene.easynote.util.SettingUtil

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

    // 是否显示选择备份目录的设置项
    var enableBackup by remember { mutableStateOf(SettingUtil.backupMode) }

    val density = LocalDensity.current

    val backupSettingEnableList =
        listOf<SettingEntry>(
            SwitchSetting(
                settingName = "开启备份",
                state = SettingUtil.backupMode,
                onValueChanged = {
                    SettingUtil.backupMode = it
                    enableBackup = it
                },
                description = "使用导出功能须先开启该选项",
                warning = "还没做完，开了也没用"
            )
        )

    val backupSettingList =
        listOf<SettingEntry>(
            DialogSetting(
                SettingName = "备份/导出文件夹",
                description = "当前选中文件夹: ${SettingUtil.backupPath}"
            ) {
                MessageDialog.build(MIUIStyle())
                    .setTitle("修改备份文件夹")
                    .setMessage("当前选择的文件夹为\n${SettingUtil.backupPath}\n需要修改吗?")
                    .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                    .setCancelButton("取消")
                    .setOkButton("确定")
                    .show()
            },
        )

    Column {

        SimpleTitleBar(
            title = "备份与恢复"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            SettingSubList(backupSettingEnableList)

            AnimatedVisibility(
                visible = enableBackup,
                enter = slideInVertically { with(density) { -25.dp.roundToPx() } } // 从顶部 40dp 的地方开始滑入
                        + expandVertically(expandFrom = Alignment.Top)  // 从顶部开始展开
                        + fadeIn(initialAlpha = 0.3f),   // 从初始透明度 0.3f 开始淡入
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {

                SettingSubList(backupSettingList)

            }

        }

    }

}