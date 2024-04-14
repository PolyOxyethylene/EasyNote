package com.oxyethylene.easynote.common.arrays

import com.oxyethylene.easynote.common.constant.FONT_FAMILY_DEFAULT
import com.oxyethylene.easynote.common.constant.FONT_FAMILY_SMILEYSANS
import com.oxyethylene.easynote.domain.DropDownMenuSetting
import com.oxyethylene.easynote.domain.PlainSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.util.SettingUtil

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
        PlainSetting("编辑器设置", "com.oxyethylene.COMMON", "editor-setting"),
        PlainSetting("回收站", "com.oxyethylene.COMMON", "recycle-bin"),
        PlainSetting("统计", "com.oxyethylene.COMMON", "statistics"),
        PlainSetting("备份与导出", "com.oxyethylene.BACKUP"),
        PlainSetting("实验性功能", "com.oxyethylene.COMMON", "lab"),
        PlainSetting("关于应用", "com.oxyethylene.INFO")
    )

val editorSettingList =
    listOf(
        DropDownMenuSetting(
            settingName = "字体大小",
            menuList = listOf(
                Pair(-1, "1"),
                Pair(-1, "2"),
                Pair(-1, "3"),
                Pair(-1, "4"),
                Pair(-1, "5"),
                Pair(-1, "6"),
                Pair(-1, "7")
            ),
            value = SettingUtil.fontSize().toString(),
            onValueChanged = { SettingUtil.setFontSize(it.toInt()) },
            warning = "有 Bug，暂时不可用"),

        DropDownMenuSetting(
            settingName = "行高",
            menuList = listOf(
                Pair(-1, "1.0"),
                Pair(-1, "1.5")
            ),
            value = SettingUtil.lineHeight.toString(),
            onValueChanged = { SettingUtil.lineHeight = it.toFloat() },
        ),

        SwitchSetting(
            settingName = "图片进行 1:1 裁切",
            state = SettingUtil.clipMode,
            onValueChanged = { SettingUtil.clipMode = it },
            description = "向文章插入图片时将图片裁切成正方形"
        ),

        SwitchSetting(
            settingName = "编辑工具栏显示提示信息",
            state = SettingUtil.showEditBarTip,
            onValueChanged = { SettingUtil.showEditBarTip = it },
            description = "提醒你工具栏可滑动，觉得碍眼就关闭"
        ),

        DropDownMenuSetting(
            settingName = "字体选择",
            menuList = listOf(
                Pair(-1, FONT_FAMILY_DEFAULT),
                Pair(-1, FONT_FAMILY_SMILEYSANS),
            ),
            value = SettingUtil.fontFamily,
            onValueChanged = { SettingUtil.fontFamily = it }
        )
    )




