package com.oxyethylene.easynote.util

import com.drake.serialize.serialize.serialLazy
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
     * 字号大小，取值为 1~7 的整数
     */
    private var fontSize: Int by serialLazy(1)

    /**
     * 获取字体大小
     */
    fun fontSize() = fontSize

    /**
     * 设置编辑器字体大小，取值为 1~7 的整数
     * @param size 字体大小
     */
    fun setFontSize(size: Int): Boolean {
        if (size in 1..7) {
            fontSize = size
            return true
        }
        return false
    }

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

}