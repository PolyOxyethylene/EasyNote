package com.oxyethylene.easynote.domain

import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.domain.entity.FileEntity
import com.oxyethylene.easynote.util.DateUtil
import java.util.TreeSet

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
 *  @param fileId 文章的文件 id，文章的 id 可修改
 *  @param fileName 文章的文件名
 *  @param parent 文章的父目录
 *  @param createTime 文章的创建日期
 *  @param updateTime 文章的更新日期，文章首次创建时为 null
 *  @param eventId 文章所属的事件的 id，默认为 0（无绑定事件）
 *  @param keywordList 文章关联的关键词集合
 */
class NoteFile(
    override var fileId: Int,
    override var fileName: String,
    override val parent: Dentry?,
    override var createTime: String,
    var updateTime: String? = null,
    var eventId: Int = 0,
    val keywordList: TreeSet<Int>
) : Dentry {

    override val type: FileType = FileType.FILE    // 文件类型固定为 文章

    companion object {

        /**
         * 创建文章文件的工厂方法
         * @param fileId 文件 id
         * @param fileName 目录的名称
         * @param parent 父目录
         * @param createTime 创建时间，如果是第一次创建无需传参
         * @param keywordList 文章关联的关键词集合，如果是第一次创建无需传参
         */
        fun createFile (fileId : Int, fileName : String, parent : Dentry, createTime: String = DateUtil.getCurrentDateTime(), keywordList: TreeSet<Int> = TreeSet()) : NoteFile {
            val newFile = NoteFile(fileId, fileName, parent, createTime, keywordList = keywordList)
            (newFile.parent as Dir).addFile(newFile)
            return newFile
        }

        /**
         * 这是从数据库恢复文章数据时的版本
         * @param fileId 文件 id
         * @param fileName 目录的名称
         * @param parent 父目录
         * @param createTime 创建时间，必须传参
         * @param updateTime 修改时间，必须传参
         * @param eventId 绑定的事件 id
         * @param keywordList 文章关联的关键词集合
         * */
        fun createFileFromDB (fileId : Int, fileName : String, parent : Dentry, createTime: String, updateTime: String, eventId: Int, keywordList: TreeSet<Int>) : NoteFile {
            val newFile = NoteFile(fileId, fileName, parent, createTime, updateTime, eventId, keywordList)
            // 如果文件 id 为正数，加入父目录文件列表
            if (newFile.fileId > 0) {
                (newFile.parent as Dir).addFile(newFile)
            } else {
                // 否则文件为回收状态，使父目录回收计数器 +1
                (newFile.parent as Dir).recycleCount++
            }
            return newFile
        }

    }

    /**
     * 判断文章有无关联任何关键词
     */
    fun hasBindedKeywords () = !keywordList.isEmpty()

    /**
     * 判断文件是否处于回收状态
     * @return 处于则返回 true
     */
    fun isRecycled () = fileId < 0

    override fun toFileEntity(): FileEntity {
        return FileEntity(
            fileId,
            eventId,
            fileName,
            createTime,
            updateTime?: createTime,
            parent?.fileId?:0,
            type.ordinal,
            keywordList
        )
    }

    // 软拷贝
    override fun clone(): NoteFile {
        val newNote = NoteFile(this.fileId, this.fileName, this.parent, this.createTime, this.updateTime, this.eventId, this.keywordList)
        return newNote
    }

}

data class NoteList (val noteList: MutableList<NoteFile> = ArrayList())