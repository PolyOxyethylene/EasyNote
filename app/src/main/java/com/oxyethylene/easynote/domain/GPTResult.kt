package com.oxyethylene.easynote.domain

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : GPTResult.java
 * @createTime   : 2024/4/14 19:51
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
data class GPTResult (
    val title: String,
    val summarization: String,
    val keywords: List<String>
)
