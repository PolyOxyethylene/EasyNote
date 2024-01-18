package com.oxyethylene.easynotedemo.domain

import com.oxyethylene.easynotedemo.util.FileType

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.domain
 * @ClassName    : FileEntry.java
 * @createTime   : 2023/12/3 21:46
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 表示一个目录项，可能是一个目录或者文件对象
 */
open class Dentry {

    val fileId: Int         // 目录项对应的唯一 id

    var fileName: String    // 文件/目录名

    val parent: Dentry?      // 父目录

    val type: FileType      // 目录项类型

    var lastModifiedTime: String    // 上次修改时间，初始时为创建时间

    constructor(_id : Int, _fileName : String, _parent : Dentry?, _type : FileType, date: String) {
        fileId = _id
        fileName = _fileName
        parent = _parent
        type = _type
        lastModifiedTime = date
    }

    // 将该类对象转为可持久化的实体类 FileEntity
    fun toFileEntity () : FileEntity {
        val eventId = if (this is NoteFile) this.eventId else 0
        val entity = FileEntity(fileId, eventId, fileName, lastModifiedTime, parent!!.fileId, type.ordinal)
        return entity
    }

    // 将该类对象进行 浅 拷贝，子类中实现
    open fun clone() : Dentry {
        return Dentry(-1, "not a file", null, FileType.DIRECTORY, "")
    }

}

