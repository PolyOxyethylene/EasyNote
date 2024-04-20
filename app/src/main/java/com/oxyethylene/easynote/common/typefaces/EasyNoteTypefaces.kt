package com.oxyethylene.easynote.common.typefaces

import android.content.Context
import android.graphics.Typeface

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.common.typefaces
 * @ClassName    : EasyNoteTypefaces.java
 * @createTime   : 2024/4/18 22:50
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object EasyNoteTypefaces {

    private const val LXGW_NEO_XIHEI_PATH = "fonts/LXGWNeoXiHei.ttf"

    private const val LXGW_WEN_KAI_PATH = "fonts/LXGWWenKaiLite-Regular.ttf"

    private const val LXGW_YOZAI_PATH = "fonts/Yozai-Regular.ttf"

    /**
     * 霞鹜新晰黑字体
     * @param context 应用上下文
     * @param isBold 是否加粗
     */
    fun lxgwNeoXiHei (context: Context, isBold: Boolean = false): Typeface {
        val neoXiHei = Typeface.createFromAsset(context.assets, LXGW_NEO_XIHEI_PATH)
        if (isBold) {
            val neoXiHeiBold = Typeface.create(neoXiHei, Typeface.BOLD)
            return neoXiHeiBold
        }
        return neoXiHei
    }

    /**
     * 霞鹜文楷字体
     * @param context 应用上下文
     * @param isBold 是否加粗
     */
    fun lxgwWenKai (context: Context, isBold: Boolean = false): Typeface {
        val wenKai = Typeface.createFromAsset(context.assets, LXGW_WEN_KAI_PATH)
        if (isBold) {
            val wenKaiBold = Typeface.create(wenKai, Typeface.BOLD)
            return wenKaiBold
        }
        return wenKai
    }

    /**
     * 霞鹜悠哉字体
     * @param context 应用上下文
     * @param isBold 是否加粗
     */
    fun lxgwYoZai (context: Context, isBold: Boolean = false): Typeface {
        val yoZai = Typeface.createFromAsset(context.assets, LXGW_YOZAI_PATH)
        if (isBold) {
            val yoZaiBold = Typeface.create(yoZai, Typeface.BOLD)
            return yoZaiBold
        }
        return yoZai
    }
}