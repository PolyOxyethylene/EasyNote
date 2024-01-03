package com.oxyethylene.easynotedemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oxyethylene.easynotedemo.dao.FileDao
import com.oxyethylene.easynotedemo.domain.FileEntity

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.database
 * @ClassName    : AppDatabase.java
 * @createTime   : 2023/12/30 1:50
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Database(version = 1, entities = [FileEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun FileDao() : FileDao

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