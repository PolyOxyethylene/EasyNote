package com.oxyethylene.easynote.common.arrays

import com.oxyethylene.easynote.domain.DropDownMenuSetting
import com.oxyethylene.easynote.domain.PlainSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.common.arrays
 * @ClassName    : SettingList.java
 * @createTime   : 2024/2/22 15:38
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
// 主设置界面的设置列表
val mainSettingList =
    listOf<SettingEntry>(
        PlainSetting("编辑器设置", "com.oxyethylene.EDITOR_SETTING", warning = "还没做, 点进去纯看样子"),
        PlainSetting("统计", "com.oxyethylene.STATISTICS"),
        PlainSetting("备份与恢复", "com.oxyethylene.BACKUP", warning = "还没做, 点进去纯看样子"),
        PlainSetting("实验性功能", "com.oxyethylene.LAB", warning = "还没做, 点进去纯看样子"),
        PlainSetting("关于应用", "com.oxyethylene.INFO")
    )

val editorSettingList =
    listOf(
        DropDownMenuSetting("主题外观", listOf()),
        DropDownMenuSetting("编辑器背景颜色", listOf()),
        DropDownMenuSetting("字体大小", listOf()),
        SwitchSetting("段首行缩进")
    )

val labSettingList =
    listOf<SettingEntry>(
        SwitchSetting("使用定位信息"),
        SwitchSetting("开启关键词提取", warning = "开启此功能会消耗更多内存以及要求设备具有较高的性能")
    )

val backupSettingList =
    listOf<SettingEntry>(
        SwitchSetting("开启备份", description = "开了也没用，我还没做呢")
    )

