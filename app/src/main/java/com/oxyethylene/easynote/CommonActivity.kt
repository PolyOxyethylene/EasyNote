package com.oxyethylene.easynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kongzue.dialogx.DialogX
import com.oxyethylene.easynote.ui.commonactivity.EditorSettingPageUI
import com.oxyethylene.easynote.ui.commonactivity.KeywordPageUI
import com.oxyethylene.easynote.ui.commonactivity.LabSettingPageUI
import com.oxyethylene.easynote.ui.commonactivity.LicenseInfoPageUI
import com.oxyethylene.easynote.ui.commonactivity.PrivacySettingPageUI
import com.oxyethylene.easynote.ui.commonactivity.QuestionPageUI
import com.oxyethylene.easynote.ui.commonactivity.RecyclePageUI
import com.oxyethylene.easynote.ui.commonactivity.SearchPageUI
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

        DialogX.init(applicationContext)

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
                        // 回收站界面
                        "recycle-bin" -> RecyclePageUI()
                        // 开放源代码许可界面
                        "opensource" -> LicenseInfoPageUI()
                        // 内测鸣谢界面
                        "thanks" -> ThanksPageUI()
                        // 常见问题界面
                        "questions" -> QuestionPageUI()
                        // 关键词界面
                        "keywords" -> KeywordPageUI()
                        // 搜索界面
                        "search" -> SearchPageUI()
                        // 隐私功能界面
                        "privacy" -> PrivacySettingPageUI()

                        else -> {}
                    }

                }
            }
        }
    }

}
