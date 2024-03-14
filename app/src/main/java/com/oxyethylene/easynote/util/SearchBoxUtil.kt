package com.oxyethylene.easynote.util

import android.os.Handler
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.drawerbox.DrawerBox
import com.kongzue.drawerbox.DrawerBoxDialog
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.components.SearchBox

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : SearchBoxUtil.java
 * @createTime   : 2024/3/3 14:37
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 底部搜索框的工具类，管理主页面的底部搜索框
 */
object SearchBoxUtil {

    // 底部搜索框
    private var drawerBox: DrawerBox? = null

    // 主页面的 handler
    var handler: Handler? = null

    /**
     * 初始化工具类
     */
    fun initSearchBoxUtil (handler: Handler) {
        this.handler = handler
    }

    /**
     * 初始化对话框
     */
    fun init() {
        drawerBox = DrawerBox.build()
            .show(object : OnBindView<DrawerBoxDialog>(R.layout.search_drawerbox_layout){

                // 设置搜索框的功能和界面
                override fun onBind(dialog: DrawerBoxDialog?, v: View?) {
                    val composeView = v?.findViewById<ComposeView>(R.id.search_box_compose_view)

                    composeView?.setContent { SearchBox() }
                }

            })
    }

    /**
     * 展示对话框
     */
    fun show() {

        drawerBox?.apply {
            if (isFold) {
                unfold()
            } else {
                fold()
            }
            return
        }

        init()

    }

}