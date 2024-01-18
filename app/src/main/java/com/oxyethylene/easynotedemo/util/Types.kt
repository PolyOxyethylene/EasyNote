package com.oxyethylene.easynotedemo.util

import androidx.compose.ui.graphics.toArgb
import com.kongzue.dialogx.util.InputInfo
import com.oxyethylene.easynotedemo.ui.theme.SkyBlue

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.util
 * @ClassName    : Types.java
 * @createTime   : 2024/1/6 0:25
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 存放一些指示类型的枚举类
 */
/**
 * 描述文件的类型
 */
enum class FileType {

    DIRECTORY, FILE

}

// 指定 App 中部分输入框的外观属性
val inputInfo = InputInfo().apply {
    setCursorColor(SkyBlue.toArgb())
}

/**
 *  描述App主界面的几种子页面
 */
object MainPageRouteConf {
    const val FOLDER = "folder_page"     // 目录界面
    const val EVENT = "event_page"      // 事件界面
}