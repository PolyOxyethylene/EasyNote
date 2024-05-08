 package com.oxyethylene.easynote

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.king.android.ktx.core.isResultOk
import com.king.android.ktx.core.startActivityForResultLauncher
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.common.constant.EASYNOTE_BACKUP_FOLDER
import com.oxyethylene.easynote.domain.DialogSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme
import com.oxyethylene.easynote.util.DebugInfoDial
import com.oxyethylene.easynote.util.SettingUtil

class BackupSettingActivity : ComponentActivity() {

     val backupSettingList =
         listOf<SettingEntry>(
             DialogSetting(
                 settingName = "修改备份/导出文件夹"
             ) {
                 MessageDialog.build(MIUIStyle())
                     .setTitle("修改备份文件夹")
                     .setMessage("当前选择的文件夹为\n${SettingUtil.backupPath}\n需要修改吗?")
                     .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                     .setCancelButton("取消")
                     .setOkButton("确定")
                     .setOkButtonClickListener {
                             _, _ ->
                         // 跳转到原生文件管理器选择文件夹
                         val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                         getPathLauncher.launch(intent)
                         return@setOkButtonClickListener false
                     }
                     .show()
             },
             DialogSetting(
                 settingName = "恢复默认导出文件夹",
                 warning = "原备份文件夹下的文件将会保留，需要自行删除"
             ) {
                 MessageDialog.build(MIUIStyle())
                     .setTitle("恢复默认导出文件夹")
                     .setMessage("需要恢复默认导出文件夹吗?")
                     .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                     .setCancelButton("取消")
                     .setOkButton("确定")
                     .setOkButtonClickListener {
                             _, _ ->
                         // 恢复初始文件夹
                         SettingUtil.backupPath = EASYNOTE_BACKUP_FOLDER
                         return@setOkButtonClickListener false
                     }
                     .show()
             },
             DialogSetting(
                 settingName = "手动备份",
                 description = "将所有文章备份到指定的文件夹"
             ) {
               DebugInfoDial.todoDialog("手动备份")
             },
             DialogSetting(
                 settingName = "从本地恢复文章备份",
                 description = "将所有文章备份到指定的文件夹"
             ) {
                 DebugInfoDial.todoDialog("从本地恢复文章备份")
             },
         )

     private val getPathLauncher = startActivityForResultLauncher {
        if (it.isResultOk()) {
            val uri = it.data?.data
            uri?.let {pathUri ->
                SettingUtil.backupPath = with(pathUri.path.toString()) {
                    substring(indexOf(':') + 1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {

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
                                exit = shrinkVertically() + fadeOut()
                            ) {

                                SettingSubList(backupSettingList)

                            }

                        }

                    }

                }
            }
        }
    }
}