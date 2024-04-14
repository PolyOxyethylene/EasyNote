package com.oxyethylene.easynote.errorhandler

import com.drake.net.interfaces.NetErrorHandler
import com.kongzue.dialogx.dialogs.WaitDialog

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.errorhandler
 * @ClassName    : GPTErrorHandler.java
 * @createTime   : 2024/4/14 22:50
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class GPTErrorHandler : NetErrorHandler {

    override fun onError(e: Throwable) {
        super.onError(e)
        WaitDialog.dismiss()
    }

}