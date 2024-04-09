package com.oxyethylene.easynote.util

import android.view.Gravity
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : DebugInfoDial.java
 * @createTime   : 2024/3/25 22:43
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 项目开发中用到的对话框
 */
object DebugInfoDial {

    /**
     * 显示要检查的信息的 log 对话框
     * @param info 要显示的信息
     */
    fun logInfo (info: CharSequence) {
        MessageDialog.build()
            .setTitle("调试")
            .setMessage(info)
            .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
            .setOkButton("确认")
            .show()
    }

    /**
     * 显示未开发的内容提示信息的对话框
     * @param title 未实现的功能的名字
     */
    fun todoDialog (title: CharSequence) {
        MessageDialog.build(MIUIStyle())
            .setTitle(title)
            .setMessage("功能开发中，敬请期待")
            .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
            .setOkButton("确认")
            .show()
    }

}