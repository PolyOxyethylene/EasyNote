package com.oxyethylene.easynote.domain

import java.util.TreeSet

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : NLPResult.java
 * @createTime   : 2024/3/31 19:21
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * HANLP 客户端请求返回的结果封装
 * @param summarization 文章的摘要
 * @param keywords 文章的关键词
 */
class NLPResult (val summarization: String = "", val keywords: Set<String> = TreeSet())