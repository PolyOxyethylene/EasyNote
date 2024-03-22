package com.oxyethylene.easynote.database.convertor

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.hjq.gson.factory.GsonFactory
import java.util.TreeSet

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.database.convertor
 * @ClassName    : KeywordSetConvertor.java
 * @createTime   : 2024/3/19 19:21
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class KeywordSetConvertor {

    @TypeConverter
    fun stringToObject(value: String): TreeSet<Int> {
        val listType = object : TypeToken<TreeSet<Int>>() {}.type
        return GsonFactory.getSingletonGson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(set: TreeSet<Int>?): String = GsonFactory.getSingletonGson().toJson(set)

}