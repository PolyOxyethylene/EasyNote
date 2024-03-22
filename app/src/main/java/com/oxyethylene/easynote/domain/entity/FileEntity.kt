package com.oxyethylene.easynote.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.oxyethylene.easynote.database.convertor.KeywordSetConvertor
import java.util.TreeSet

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : FileEntity.java
 * @createTime   : 2023/12/30 1:23
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 用于保存文件目录结构的实体类
 */
/**
 * 负责持久化的实体类
 * @param fileId 文件 id
 * @param eventId 绑定的事件 id
 * @param fileName 文件名
 * @param createTime 文件的创建时间
 * @param updateTime 文件的更新时间，只对文章类型有效，目录类型该字段值与 createTime 保持一致
 * @param parentId 父结点 id
 * @param fileType 文件的类型，0为目录，1为文章
 * @param keywordList 文件的关联关键词列表
 */
@TypeConverters(KeywordSetConvertor::class)
@Entity
class FileEntity(
    @PrimaryKey
    var fileId : Int,

    var eventId : Int,

    var fileName : String,

    var createTime : String,

    var updateTime : String,

    var parentId : Int,

    var fileType : Int,

    val keywordList: TreeSet<Int>?
)