package com.oxyethylene.easynote.util

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.drake.net.utils.TipUtils
import com.hankcs.hanlp.restful.HanLPClient
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.NLPResult
import com.oxyethylene.easynote.ui.components.ShowExtractionDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Properties
import java.util.TreeSet

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : NlpUtil.java
 * @createTime   : 2024/3/31 19:11
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object NlpUtil {

    /**
     * 客户端对象
     */
    private var nlpClient: HanLPClient? = null

    /**
     * Hanlp 用户密钥
     */
    private var hanlpAuthKey = ""

    /**
     * 初始化，从配置文件中读取密钥信息
     * @param 应用上下文
     */
    fun init (context: Context) {

        val prop = Properties();

        prop.load(context.assets.open("auth.properties"))

        hanlpAuthKey = prop.getProperty("HANLP_AUTH")

    }



    /**
     * 获取文本的摘要和关键词
     * @param content 要解析的文本
     * @param onKeywordUpdate 当执行关键词相关的更新操作时，执行的额外操作
     */
    fun getExtractionAndKeywords (content: String, onKeywordUpdate: () -> Unit = {}) {

        if (nlpClient == null) {
            nlpClient = HanLPClient("https://www.hanlp.com/api", hanlpAuthKey, "zh", 1000)
        }

        var result: NLPResult?

        if (content.isBlank() || content.isEmpty()) {
            MessageDialog.build(MIUIStyle())
                .setTitle("请求限制")
                .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                .setMessage("无法解析空文章")
                .setOkButton("确认")
                .show()
            return
        }

        if (content.length > 1000) {
            MessageDialog.build(MIUIStyle())
                .setTitle("请求限制")
                .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                .setMessage("当前文章内容超过 1000 字\n请删减后再请求分析")
                .setOkButton("确认")
                .show()
            return
        }

        WaitDialog.show("上传服务器分析中，请稍等")

        // 上传服务端获取分析结果
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keywords = nlpClient!!.keyphraseExtraction(content)
                val summarization = nlpClient!!.abstractiveSummarization(content)
                // 封装分析结果
                if (keywords.isNotEmpty() && summarization.isNotEmpty() && summarization.isNotBlank()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        result = NLPResult(summarization?:"", keywords?.keys?: TreeSet<String>())
                        WaitDialog.dismiss()
                        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.compose_layout){
                            override fun onBind(dialog: FullScreenDialog?, v: View?) {
                                val composeView = v?.findViewById<ComposeView>(R.id.compose_view)

                                composeView?.setContent {
                                    ShowExtractionDialog(
                                        extraction = result?:NLPResult(),
                                        onKeywordUpdate = onKeywordUpdate
                                    ) {
                                        dialog?.dismiss()
                                    }
                                }
                            }
                        })
                    }
                }

            } catch (ex: IOException) {
                CoroutineScope(Dispatchers.Main).launch {

                    WaitDialog.dismiss()
                    TipUtils.toast("请求失败，请检查文章内容或者网络情况")

                }
            }
        }
    }

}