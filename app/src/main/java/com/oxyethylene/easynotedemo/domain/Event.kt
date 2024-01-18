package com.oxyethylene.easynotedemo.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.domain
 * @ClassName    : Event.java
 * @createTime   : 2024/1/15 2:01
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 *  代表一个事件，一个事件通常可以关联多篇文章
 *  @param eventId 事件的固有 id，唯一标识符
 *  @param eventName 事件的名字
 *  @param description 事件的描述
 *  @param noteCount 事件绑定的文章数量
 */
@Entity
data class Event(@PrimaryKey val eventId: Int, var eventName: String, var description: String, var noteCount: Int = 0)


/**
 *  只是为了让 State 正常观察一个列表但是又不想用 MutableStateList 而写的套娃类，实在是太若只了
 */
class EventList (private val eventList: List<Event> = ArrayList()) {

    fun getList () = eventList

}