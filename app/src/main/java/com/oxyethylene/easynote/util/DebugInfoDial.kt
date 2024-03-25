package com.oxyethylene.easynote.util

import com.kongzue.dialogx.dialogs.MessageDialog

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : DebugInfoDial.java
 * @createTime   : 2024/3/25 22:43
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object DebugInfoDial {

    fun logInfo (info: CharSequence) {
        MessageDialog.build()
            .setTitle("调试")
            .setMessage(info)
            .setOkButton("确认")
            .show()
    }

}