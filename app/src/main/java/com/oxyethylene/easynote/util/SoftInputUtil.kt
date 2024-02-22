package com.oxyethylene.easynote.util

import android.graphics.Rect
import android.os.Build
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : SoftInputUtil.java
 * @createTime   : 2024/1/2 18:56
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 辅助软键盘输入的方法
 */

/**
 *  判断软键盘是否收起
 *  @return 软键盘的状态
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun imeVisible(): State<Boolean> {
    val isImeVisible = remember { mutableStateOf(false) }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        isImeVisible.value = WindowInsets.isImeVisible
    }else {
        val view = LocalView.current
        DisposableEffect(view) {
            val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
                val rect = Rect()
                view.getWindowVisibleDisplayFrame(rect)
                val screenHeight = view.rootView.height
                val keypadHeight = screenHeight - rect.bottom
                isImeVisible.value = keypadHeight > screenHeight * 0.15
            }
            view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
            onDispose {
                view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
            }
        }
    }
    return isImeVisible
}

/**
 * 重置当前界面的输入框的状态
 * 主要是为了收起软键盘
 */
@Deprecated("暂时没什么用")
@Composable
fun resetInput () {

    val manager = LocalFocusManager.current;

    manager.clearFocus()

}