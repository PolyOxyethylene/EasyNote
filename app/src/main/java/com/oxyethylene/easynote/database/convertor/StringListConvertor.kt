package com.oxyethylene.easynote.database.convertor

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.hjq.gson.factory.GsonFactory

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
class StringListConvertor {

    @TypeConverter
    fun stringToObject(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return GsonFactory.getSingletonGson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: ArrayList<String>?): String = GsonFactory.getSingletonGson().toJson(list)

}