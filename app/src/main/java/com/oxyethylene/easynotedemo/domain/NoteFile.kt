package com.oxyethylene.easynotedemo.domain

import com.oxyethylene.easynotedemo.util.DateUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.domain
 * @ClassName    : NoteFile.java
 * @createTime   : 2023/12/3 22:38
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class NoteFile(
    _id: Int,
    _fileName: String,
    _parent: Dentry?,
    date: String
) : Dentry(_id, _fileName, _parent, FileType.FILE, date) {

//    var text : String = ""

    companion object {

        // 创建笔记文件的工厂方法
        fun createFile (fileId : Int, fileName : String, parent : Dentry, date: String = DateUtil.getCurrentDateTime()) : NoteFile {
            val newFile = NoteFile(fileId, fileName, parent, date)
            (newFile.parent as Dir).addFile(newFile)
            return newFile
        }

    }

    // 软拷贝
    override fun clone(): NoteFile {
        val newNote = NoteFile(this.fileId, this.fileName, this.parent, this.lastModifiedTime)
        return newNote
    }

}

object NoteDefaults {

    const val defaultTitle = "新文章?"

    const val defaultContent = "请在目录中新建一篇文章再进行编辑"

}