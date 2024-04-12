package com.oxyethylene.easynote.domain

import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.domain.entity.FileEntity
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
class Dir(
    override val fileId: Int,
    override var fileName: String,
    override val parent: Dentry?,
    override var createTime: String
) : Dentry {

    /**
     * 文件类型固定为 目录
     */
    override val type: FileType = FileType.DIRECTORY

    /**
     * 该目录下的文件集合
     */
    private var files = ArrayList<Dentry> ()

    /**
     * 该目录下已回收的文件数
     */
    var recycleCount = 0

    companion object {

        /**
         * 创建目录文件的工厂方法
         * @param fileId 文件 id
         * @param dirName 目录的名称
         * @param parent 父目录
         * @param createTime 创建时间，如果是第一次创建无需传参，如果是数据库恢复数据则传入
         */
        fun createDirectory (fileId : Int, dirName : String, parent : Dentry, createTime: String = DateUtil.getCurrentDateTime()) : Dir {
            val newDir = Dir(fileId, dirName, parent, createTime)
            (newDir.parent as Dir).addFile(newDir)
            return newDir
        }

    }

    /**
     * 往文件集合中添加新文件，可以是目录也可以是笔记文件
     * @param file 要添加的文件
    */
    fun addFile (file : Dentry): Boolean {
        return files.add(file)
    }

    /**
     * 从文件集合中移除文件，可以是目录也可以是笔记文件
     * @param file 要移除的文件
     */
    fun removeFile (file : Dentry): Boolean {
        return files.remove(file)
    }

    /**
     * 获取文件列表
     */
    fun getFileList () = files

    /**
     * 目录是否为空
     * @return 当且仅当文件列表为空且没有回收状态文件时返回 true
     */
    fun isEmpty() = files.isEmpty() && recycleCount == 0

    override fun toFileEntity(): FileEntity {
        return FileEntity(
            fileId,
            0,
            fileName,
            createTime,
            createTime,
            parent?.fileId?:0,
            type.ordinal,
            null
        )
    }

    // 软拷贝
    override fun clone() : Dir{
        val newDir = Dir(this.fileId, this.fileName, this.parent, this.createTime)
        newDir.files = this.files
        return newDir
    }

}
