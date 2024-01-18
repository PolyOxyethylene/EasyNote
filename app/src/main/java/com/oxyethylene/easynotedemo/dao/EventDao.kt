package com.oxyethylene.easynotedemo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.oxyethylene.easynotedemo.domain.Event

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.dao
 * @ClassName    : EventDao.java
 * @createTime   : 2024/1/16 14:48
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

@Dao
interface EventDao {

    @Query("select * from Event")
    fun getAllEvents () : List<Event>

    @Insert
    fun insertEvent (event: Event)

    @Delete
    fun deleteEvent (event: Event)

    @Query("update Event set eventName = :newEventName where eventId = :id")
    fun renameEvent (id: Int, newEventName: String)

    @Query("update Event set description = :description where eventId = :id")
    fun updateEventDescription (id: Int, description: String)

    @Update
    fun updateEvent (event: Event)

}