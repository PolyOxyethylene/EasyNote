package com.oxyethylene.easynote.util

import com.drake.serialize.serialize.serialLazy
import com.oxyethylene.easynote.common.constant.DEFAULT_LOCATION
import com.oxyethylene.easynote.common.constant.EASYNOTE_BACKUP_FOLDER
import com.oxyethylene.easynote.common.constant.EXTRACTION_MODEL_DEFAULT
import com.oxyethylene.easynote.common.constant.FONT_FAMILY_DEFAULT

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : SettingUtil.java
 * @createTime   : 2024/3/31 16:02
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 统一管理所有设置的值
 */
object SettingUtil {

    /**
     * 行高，默认为 1 倍，可选 1.5 倍
     */
    var lineHeight by serialLazy(1.0f)

    /**
     * 是否开启关键词自动提取功能
     */
    var autoExtraction by serialLazy(false)

    /**
     * 是否开启裁切模式
     */
    var clipMode by serialLazy(false)

    /**
     * 是否在编辑工具栏上方显示提示信息，默认开启
     */
    var showEditBarTip by serialLazy(true)

    /**
     * 是否开启备份和导出功能
     */
    var backupMode by serialLazy(false)

    /**
     * 编辑器字体，默认值是系统默认字体
     */
    var fontFamily by serialLazy(FONT_FAMILY_DEFAULT)

    /**
     * 文件导出的文件夹，默认为 Documents/EasyNote
     */
    var backupPath by serialLazy(EASYNOTE_BACKUP_FOLDER)

    /**
     * 文章分析使用的语言模型
     */
    var extractionModel by serialLazy(EXTRACTION_MODEL_DEFAULT)

    /**
     * 开启自动定位，默认关闭
     */
    var enableLocation by serialLazy(false)

    /**
     * 默认定位地址
     */
    var defaultLocation by serialLazy(DEFAULT_LOCATION)

}