package com.oxyethylene.easynote.util

import android.content.Context

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : CommonUtils.java
 * @createTime   : 2024/2/23 21:04
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 将 dp 单位的数值转换为以 px 做单位的对应值
 * @param context 获取设备屏幕密度
 * @param dp 要转换的值
 */
fun dpToPx(context: Context, dp: Int): Float {
    val density = context.resources.displayMetrics.density
    return dp * density
}

/**
 * 将 px 单位的数值转换为以 dp 做单位的对应值
 * @param context 获取设备屏幕密度
 * @param px 要转换的值
 */
fun pxToDp(context: Context, px: Float): Int {
    val density = context.resources.displayMetrics.density
    return (px/density + 0.5f).toInt()
}