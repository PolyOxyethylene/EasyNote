package com.oxyethylene.easynote.domain

import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.domain.entity.FileEntity

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : FileEntry.java
 * @createTime   : 2023/12/3 21:46
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 表示一个目录项，可能是一个目录或者文件对象
 */
interface Dentry {

    val fileId: Int         // 目录项对应的唯一 id

    var fileName: String    // 文件/目录名

    val parent: Dentry?      // 父目录

    val type: FileType      // 目录项类型

    var createTime: String    // 创建时间

    /**
     * 将该类对象转为可持久化的实体类 FileEntity
     */
    fun toFileEntity () : FileEntity

    /**
     * 将该类对象进行 浅 拷贝，子类中实现
     */
    fun clone() : Dentry

}

