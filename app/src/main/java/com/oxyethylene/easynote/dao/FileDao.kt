package com.oxyethylene.easynote.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.oxyethylene.easynote.domain.entity.FileEntity

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.dao
 * @ClassName    : FileDao.java
 * @createTime   : 2023/12/30 1:43
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Dao
interface FileDao {

    @Insert
    fun insertFile (file : FileEntity)

    @Query("select * from FileEntity where parentId = :id")
    fun getChildFilesById (id : Int) : List<FileEntity>

    @Query("select * from FileEntity where eventId = :id")
    fun getChildOfEventById (id: Int) : List<FileEntity>

    @Delete
    fun deleteFile (file: FileEntity)

    @Query("update FileEntity set fileName = :newFileName where fileId = :id")
    fun renameFile (id: Int, newFileName : String)

    @Update
    fun updateFile (file: FileEntity)


}