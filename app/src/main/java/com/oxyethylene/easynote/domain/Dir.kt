package com.oxyethylene.easynote.domain

import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.util.DateUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : Dir.java
 * @createTime   : 2023/12/3 22:07
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class Dir(_id: Int, _fileName: String, _parent: Dentry?, date: String) :
    Dentry(_id, _fileName, _parent, FileType.DIRECTORY, date) {

    private var files = ArrayList<Dentry> ()   // 该目录下的文件集合

    companion object {

        // 创建目录文件的工厂方法
        fun createDirectory (fileId : Int, dirName : String, parent : Dentry, date: String = "创建于 ${DateUtil.getCurrentDateTime()}") : Dir {
            val newDir = Dir(fileId, dirName, parent, date)
            (newDir.parent as Dir).addFile(newDir)
            return newDir
        }

    }

    // 往文件集合中添加新文件，可以是目录也可以是笔记文件
    fun addFile (file : Dentry) {
        files.add(file)
    }

    // 获取文件列表
    fun getFileList () = files

    // 目录是否为空
    fun isEmpty() = files.isEmpty()

    // 软拷贝
    override fun clone() : Dir{
        val newDir = Dir(this.fileId, this.fileName, this.parent, this.lastModifiedTime)
        newDir.files = this.files
        return newDir
    }

}
