package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList
import com.oxyethylene.easynote.util.SettingUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : PrivacySettingPageUI.java
 * @createTime   : 2024/4/20 14:40
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Composable
fun PrivacySettingPageUI () {

    Column {

        SimpleTitleBar("隐私类功能")

        SettingSubList(
            listOf(
                SwitchSetting (
                    settingName = "文章关联定位信息",
                    state = SettingUtil.enableLocation,
                    onValueChanged = { value ->
                        SettingUtil.enableLocation = value
                    },
                    description = "开启后文章将自动关联最后一次编辑时您的位置",
                    warning = "还没做，开了也没用"
                )
            )
        )

    }

}