package com.oxyethylene.easynote.util

import com.hankcs.hanlp.restful.HanLPClient
import com.oxyethylene.easynote.domain.NLPResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    // 客户端对象
    private var nlpClient: HanLPClient? = null

    /**
     * 获取文本的摘要和关键词
     * @param content 要解析的文本
     */
    fun getExtractionAndKeywords (content: String): NLPResult? {

        if (nlpClient == null) {
            nlpClient = HanLPClient("https://www.hanlp.com/api", null, "zh", 10)
        }

        var result: NLPResult? = null

        // 上传服务端获取分析结果
        CoroutineScope(Dispatchers.IO).launch {
            val keywords = nlpClient?.keyphraseExtraction(content)
            val summarization = nlpClient?.abstractiveSummarization(content)
            // 封装分析结果
            CoroutineScope(Dispatchers.Main).launch {
                result = NLPResult(summarization?:"", keywords?.keys?:TreeSet<String>())
            }
        }

        return result
    }

}