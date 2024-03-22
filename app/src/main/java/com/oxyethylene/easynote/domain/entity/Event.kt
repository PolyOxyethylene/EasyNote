package com.oxyethylene.easynote.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.domain
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
class Event(
    @PrimaryKey val eventId: Int,
    var eventName: String,
    var description: String,
    var noteCount: Int = 0
) {

    /**
     * 浅拷贝事件对象
     */
    fun clone(): Event {
        return Event(eventId, eventName, description, noteCount)
    }

}


/**
 *  只是为了让 State 正常观察一个列表但是又不想用 MutableStateList 而写的套娃类，实在是太若只了
 */
class EventList (val eventList: List<Event> = ArrayList())