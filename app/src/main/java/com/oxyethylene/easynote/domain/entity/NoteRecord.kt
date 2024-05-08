package com.oxyethylene.easynote.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.oxyethylene.easynote.database.convertor.StringListConvertor

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.domain.entity
 * @ClassName    : NoteRecord.java
 * @createTime   : 2024/4/28 13:23
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 文章的记录
 * @param noteId 文章 id
 * @param modifiedTimes 文件的修改时间，与地点一一对应
 * @param locations 文件的位置记录
 */
@TypeConverters(StringListConvertor::class)
@Entity
class NoteRecord (
    @PrimaryKey
    val noteId: Int,
    val modifiedTimes: ArrayList<String>,
    val locations: ArrayList<String>
)