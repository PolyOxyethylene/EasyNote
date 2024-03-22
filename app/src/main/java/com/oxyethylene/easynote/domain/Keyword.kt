package com.oxyethylene.easynote.domain

import java.io.Serializable

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : Keyword.java
 * @createTime   : 2024/3/15 23:59
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

/**
 * 关键词，负责标识文章并用于事件的关联
 * @param keywordId 关键词的 id
 * @param keyword 关键词的内容
 */
class Keyword (val keywordId: Int, var keyword: String) : Serializable