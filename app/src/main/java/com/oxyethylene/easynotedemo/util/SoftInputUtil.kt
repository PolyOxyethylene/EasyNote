package com.oxyethylene.easynotedemo.util

import android.graphics.Rect
import android.os.Build
import android.view.ViewTreeObserver
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.util
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
 * 使某个 composition 内点击空白处时可以自动收回软键盘
 *
 * 必须在 @Composable 函数中使用
 */
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autoCloseKeyboard(): Modifier = composed {
    //LocalSoftwareKeyboardController 这个是compose 组件，必须在compose 函数内才能使用
//    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    pointerInput(this) {
        detectTapGestures(
            onPress = {
//                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    }
}