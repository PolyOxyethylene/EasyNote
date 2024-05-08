package com.oxyethylene.easynote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.oxyethylene.easynote.domain.entity.NoteRecord

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.dao
 * @ClassName    : RecordDao.java
 * @createTime   : 2024/4/28 13:34
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Dao
interface RecordDao {

    @Insert
    fun insertRecord (record : NoteRecord)

    /**
     * 根据文章 id 删除对应记录
     */
    @Query("delete from NoteRecord where noteId = :noteId")
    fun deleteRecordById (noteId: Int)

    @Query("select * from NoteRecord where noteId = :noteId")
    fun getRecordById (noteId: Int) : NoteRecord

    @Update
    fun updateRecord (record: NoteRecord)

}