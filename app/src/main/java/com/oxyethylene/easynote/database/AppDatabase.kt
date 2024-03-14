package com.oxyethylene.easynote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oxyethylene.easynote.dao.EventDao
import com.oxyethylene.easynote.dao.FileDao
import com.oxyethylene.easynote.domain.Event
import com.oxyethylene.easynote.domain.entity.FileEntity

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.database
 * @ClassName    : AppDatabase.java
 * @createTime   : 2023/12/30 1:50
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Database(version = 1, entities = [FileEntity::class, Event::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun FileDao() : FileDao

    abstract fun EventDao() : EventDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "easynote_database")
                .build().apply {
                    instance = this
                }
        }

    }

}