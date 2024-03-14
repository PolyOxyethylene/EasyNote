package com.oxyethylene.easynote.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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
@Entity
class FileEntity(
    @PrimaryKey
    var fileId : Int,

    var eventId : Int,

    var fileName : String,

    var date : String,

    var parentId : Int,

    var fileType : Int
)