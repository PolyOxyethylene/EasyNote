package com.oxyethylene.easynote.util

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.drake.net.Post
import com.drake.net.utils.scopeNet
import com.drake.net.utils.withMain
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.GPTResult
import com.oxyethylene.easynote.ui.components.ShowExtractionDialog
import kotlinx.coroutines.Dispatchers
import okhttp3.Response
import java.util.Properties

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : GPTUtil.java
 * @createTime   : 2024/4/14 20:08
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object GPTUtil {

    /**
     * 用户密钥
     */
    private var gptAuthKey: String? = null

    /**
     * 请求 url
     */
    private var gptUrl: String? = null

    /**
     * gpt 请求参数
     */
    private var gptTemperature: String? = null

    /**
     * 每次请求的固定前缀
     */
    private var contentPrefix: String? = null

    /**
     * 初始化，从配置文件中读取密钥信息
     * @param 应用上下文
     */
    fun init (context: Context) {

        val prop = Properties();

        prop.load(context.assets.open("auth.properties"))

        gptAuthKey = prop.getProperty("GPT_AUTH")

        gptUrl = prop.getProperty("GPT_URL")

        gptTemperature = prop.getProperty("GPT_TEMPERATURE")

        contentPrefix = context.resources.getString(R.string.gpt_request_content_prefix)

    }

    /**
     * 获取文本的摘要和关键词，比 Hanlp 更实用
     * @param content 要解析的文本
     * @param onKeywordUpdate 当执行关键词相关的更新操作时，执行的额外操作
     */
    fun getExtractions (content: String, onKeywordUpdate: () -> Unit = {}) {

        if (content.isBlank() || content.isEmpty()) {
            MessageDialog.build(MIUIStyle())
                .setTitle("请求限制")
                .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                .setMessage("无法解析空文章")
                .setOkButton("确认")
                .show()
            return
        }

        if (content.length > 4000 - (contentPrefix?.length ?: 0)) {
            MessageDialog.build(MIUIStyle())
                .setTitle("请求限制")
                .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
                .setMessage("当前文章内容超过最大字数\n请删减后再请求分析")
                .setOkButton("确认")
                .show()
            return
        }

        WaitDialog.show("上传服务器分析中，请稍等")

        val jsonString = StringBuffer()

        // 构建请求的 json 体
        jsonString.append("{\n  \"model\": \"gpt-3.5-turbo\",\n  \"messages\": [{\"role\": \"user\", \"content\": \"")
            .append(contentPrefix)
            .append(content)
            .append("\"}],\n\"temperature\": $gptTemperature}")

        gptUrl?.let {

            // 上传服务端获取分析结果
            scopeNet(Dispatchers.IO) {
                // 构建请求并发送
                val resp = Post<Response>(it) {
                    addHeader("Authorization", "Bearer $gptAuthKey")
                    addHeader("Content-Type", "application/json")
                    json(jsonString.toString())
                }.await()

                withMain {
                    val result = parseResult(resp.body?.string())
                    WaitDialog.dismiss()

                    result?.let {

                        FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.text_extraction_dialog_layout){
                            override fun onBind(dialog: FullScreenDialog?, v: View?) {
                                val composeView = v?.findViewById<ComposeView>(R.id.text_extraction_compose_view)

                                composeView?.setContent {
                                    ShowExtractionDialog(
                                        extraction = result,
                                        onKeywordUpdate = onKeywordUpdate
                                    ) {
                                        dialog?.dismiss()
                                    }
                                }
                            }
                        })

                    }
                }

            }

        }

    }

    fun parseResult (content: String?): GPTResult? {
        content?.let {

            println(it)

            val iTitle = it.indexOf("^标题^")

            val iTitleEnd = it.indexOf("^标题结束^")

            val iExtraction = it.indexOf("^摘要^")

            val iExtractionEnd = it.indexOf("^摘要结束^")

            val iKeyword = it.indexOf("^关键词^")

            val iKeywordEnd = it.indexOf("^关键词结束^")

            val title = content.substring(iTitle + 4, iTitleEnd)

            val extraction = content.substring(iExtraction + 4, iExtractionEnd)

            val keywords = content.substring(iKeyword + 5, iKeywordEnd).split('|')

            return GPTResult(title, extraction, keywords)

        }
        return null
    }

}