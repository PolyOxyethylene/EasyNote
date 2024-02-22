package com.oxyethylene.easynote.domain

import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.util.DateUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : NoteFile.java
 * @createTime   : 2023/12/3 22:38
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 *  文章类型文件
 *  @param _id 文章的文件 id
 *  @param _fileName 文章的文件名
 *  @param _parent 文章的父目录
 *  @param date 文章的创建日期
 *  @param eventId 文章所属的事件的 id，默认为 0（无绑定事件）
 */
class NoteFile(
    _id: Int,
    _fileName: String,
    _parent: Dentry?,
    date: String,
    var eventId: Int = 0
) : Dentry(_id, _fileName, _parent, FileType.FILE, date) {

    companion object {

        // 创建笔记文件的工厂方法
        fun createFile (fileId : Int, fileName : String, parent : Dentry, date: String = "创建于 ${DateUtil.getCurrentDateTime()}") : NoteFile {
            val newFile = NoteFile(fileId, fileName, parent, date)
            (newFile.parent as Dir).addFile(newFile)
            return newFile
        }

    }

    // 软拷贝
    override fun clone(): NoteFile {
        val newNote = NoteFile(this.fileId, this.fileName, this.parent, this.lastModifiedTime, this.eventId)
        return newNote
    }

}

data class NoteList (val noteList: MutableList<NoteFile> = ArrayList())