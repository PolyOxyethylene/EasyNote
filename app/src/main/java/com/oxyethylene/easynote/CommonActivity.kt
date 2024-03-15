package com.oxyethylene.easynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.oxyethylene.easynote.ui.commonactivity.BackupPageUI
import com.oxyethylene.easynote.ui.commonactivity.EditorSettingPageUI
import com.oxyethylene.easynote.ui.commonactivity.LabSettingPageUI
import com.oxyethylene.easynote.ui.commonactivity.LicenseInfoPageUI
import com.oxyethylene.easynote.ui.commonactivity.StatisticsPageUI
import com.oxyethylene.easynote.ui.commonactivity.ThanksPageUI
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme

/**
 * 负责显示一些简单的单例活动，通过传入的参数来区分要显示的 UI
 * @see com.oxyethylene.easynote.domain.PlainSetting
 */
class CommonActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val UITitle = intent.getStringExtra("title") ?: ""

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {

                    when (UITitle) {
                        // 统计页面
                        "statistics" -> StatisticsPageUI()
                        // 实验性功能页面
                        "lab" -> LabSettingPageUI()
                        // 编辑器设置界面
                        "editor-setting" -> EditorSettingPageUI()
                        // 备份界面
                        "backup" -> BackupPageUI()
                        // 开放源代码许可界面
                        "opensource" -> LicenseInfoPageUI()
                        // 内测鸣谢界面
                        "thanks" -> ThanksPageUI()

                        else -> {}
                    }

                }
            }
        }
    }
}
