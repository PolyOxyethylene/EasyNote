package com.oxyethylene.easynote.util

import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : DateUtil.java
 * @createTime   : 2023/12/29 23:16
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object DateUtil {

    // 日期时间格式
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    // 获取当前系统时间（字符串）
    fun getCurrentDateTime() = dateTimeFormat.format(System.currentTimeMillis())


}