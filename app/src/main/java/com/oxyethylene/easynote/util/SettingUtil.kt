package com.oxyethylene.easynote.util

import com.drake.serialize.serialize.serialLazy

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
     * 是否开启关键词自动提取功能
     */
    var autoExtraction by serialLazy(false)


}