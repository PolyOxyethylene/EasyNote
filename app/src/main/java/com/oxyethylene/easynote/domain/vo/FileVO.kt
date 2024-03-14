package com.oxyethylene.easynote.domain.vo

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.domain.vo
 * @ClassName    : FileVO.java
 * @createTime   : 2024/3/3 16:15
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 用于在主页搜索栏搜索结果中展示文件类型
 * @param fileId 文件 id
 * @param fileName 文件名
 * @param fileType 文件类型，0为目录，1为文章
 */
class FileVO (val fileId: Int, val fileName: String, val fileType: Int)