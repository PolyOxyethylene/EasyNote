package com.oxyethylene.easynotedemo.util

import androidx.compose.ui.unit.dp

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
 * 描述可以水平滑动的控件的状态
 */
enum class SwipeableItemStatus {

    HIDE,   // 隐藏一部分
    UNFOLD  // 展开隐藏的部分

}

/**
 * 描述滑动控件的锚点
 */
val anchors = mapOf(
    0f to SwipeableItemStatus.HIDE,
    -400.dp.value to SwipeableItemStatus.UNFOLD
)

/**
 * 描述文件的类型
 */
enum class FileType {

    DIRECTORY, FILE

}