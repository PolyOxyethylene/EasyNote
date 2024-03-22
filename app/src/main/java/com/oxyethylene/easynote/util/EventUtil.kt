package com.oxyethylene.easynote.util

import android.os.Handler
import android.os.Message
import com.oxyethylene.easynote.common.constant.EVENTLIST_INIT_SUCCESS
import com.oxyethylene.easynote.common.constant.EVENT_DELETE_SUCCESS
import com.oxyethylene.easynote.common.constant.EVENT_UPDATE_SUCCESS
import com.oxyethylene.easynote.dao.EventDao
import com.oxyethylene.easynote.database.AppDatabase
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.domain.entity.Event
import com.oxyethylene.easynote.domain.entity.EventList
import com.oxyethylene.easynote.viewmodel.MainViewModel
import java.util.TreeMap
import kotlin.concurrent.thread

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : EventUtil.java
 * @createTime   : 2024/1/16 15:00
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object EventUtil {

    // 统一管理事件的 id
    private var eventId = 0

    // 统一管理应用内的事件，键为事件的 id，值为事件对象本身
    private val eventMap = HashMap<Int, Event>()

    // 键为事件名，值为事件id的映射
    private val eventName2IdMap = TreeMap<String, Int>()

    // 绑定主页面的 viewModel
    private var mainViewModel: MainViewModel? = null

    // 数据库
    private var database: AppDatabase? = null

    // 主页面的 handler
    private var handler: Handler? = null

    // 数据库 事件类的 dao
    private var eventDao: EventDao? = null

    // 是否已经初始化，用于保证初始化事件列表操作只发生一次
    private var inited = false

    /**
     *  初始化 viewModel 和 context
     */
    fun initEventUtil(viewModel: MainViewModel, database: AppDatabase, handler: Handler) {
        if (this.mainViewModel == null || this.mainViewModel != viewModel) {
            this.mainViewModel = viewModel
        }
        if (this.database == null) {
            this.database = database
        }
        if (this.handler == null) {
            this.handler = handler
        }
        // 初始化 fileDao
        this.database?.let {
            eventDao =  it.EventDao()
        }
    }

    /**
     *  创建新的事件
     *  @param eventName 事件名称
     *  @param eventDescription 事件的描述，默认为空字符串，后续可以修改
     */
    fun createEvent (eventName: String, eventDescription: String = "") {
        eventId++

        // 创建新的事件
        val newEvent = Event(eventId, eventName, eventDescription)
        // 将新增事件添加到映射中
        eventMap.put(eventId, newEvent)
        eventName2IdMap.put(eventName, eventId)

        // 保存该事件到数据库
        saveEvent(newEvent)

        mainViewModel?.updateEventEntryList(EventList(eventMap.values.toList()))
    }

    /**
     *  刷新主界面事件列表
     */
    fun updateEventList() {
        val list = EventList(eventMap.values.toList())
        println(list)
        mainViewModel?.updateEventEntryList(list)
    }

    /**
     *  根据 id 获取对应的 event
     *  @param eventId 事件的 id
     */
    fun getEvent (eventId: Int) = eventMap.get(eventId)

    /**
     *  初始化事件列表
     */
    fun initEventList () {

        if (!inited) {

            thread {
                eventDao?.let {
                    val eventList = it.getAllEvents()
                    eventList.forEach {
                        // 初始化 eventId
                        if (it.eventId > eventId) eventId = it.eventId
                        // 初始化映射
                        eventMap.put(it.eventId, it)
                        eventName2IdMap.put(it.eventName, it.eventId)
                    }
                }

                // 通知主线程更新 UI
                val msg = Message()
                msg.what = EVENTLIST_INIT_SUCCESS
                handler?.sendMessage(msg)
            }

        }

    }

    /**
     *  将事件保存到数据库中
     *  @param event 要保存的事件
     */
    fun saveEvent (event: Event) {
        thread {
            eventDao?.insertEvent(event)
        }
    }

    /**
     *  修改事件的名字
     *  @param id 事件的 id
     *  @param newName 新名字
     */
    fun renameEvent (id: Int, newName: String) {

        thread {

            val renameItem = eventMap.get(id)

            renameItem?.let {
                // 重命名事件
                eventDao?.renameEvent(id, newName)

                // 更新名字到id的映射
                eventName2IdMap.remove(it.eventName)
                eventName2IdMap.put(newName, it.eventId)

                it.eventName = newName

                // 通知主线程更新 UI
                val msg = Message()
                msg.what = EVENT_UPDATE_SUCCESS
                handler?.sendMessage(msg)
            }

        }

    }

    /**
     *  修改事件的描述
     *  @param id 事件的 id
     *  @param description 事件的描述
     */
    fun updateEventDesc (id: Int, description: String) {

        val renameItem = eventMap.get(id)

        renameItem?.let {
            // 更新事件描述
            it.description = description
            thread {
                eventDao?.updateEventDescription(id, description)
                // 通知主线程更新 UI
                val msg = Message()
                msg.what = EVENT_UPDATE_SUCCESS
                handler?.sendMessage(msg)
            }
        }
    }

    fun deleteEvent (id: Int) {

        eventMap.get(id)?.let {
            thread {
                eventDao?.deleteEvent(it)

                eventMap.remove(it.eventId)
                eventName2IdMap.remove(it.eventName)

                // 通知主线程更新 UI
                val msg = Message()
                msg.what = EVENT_DELETE_SUCCESS
                handler?.sendMessage(msg)
            }
        }

    }

    /**
     *  更新事件
     *  @param file 要更新的事件
     */
    fun updateEvent (event: Event) {
        thread {
            eventDao?.updateEvent(event)
            // 通知主线程更新 UI
            val msg = Message()
            msg.what = EVENT_UPDATE_SUCCESS
            handler?.sendMessage(msg)
        }
    }

    /**
     *  获取所有事件的名称，返回包含所有名称的一个集合
     */
    fun getEventNames () = eventName2IdMap.keys

    /**
     *  通过名字查询对应事件并将其与指定的文章绑定
     *  @param eventName 事件名
     *  @param note 要绑定的文章
     */
    fun bindNote2Event (eventName: String, note: NoteFile): Boolean {

        if (note.eventId != 0) return false

        eventName2IdMap.get(eventName)?.let {
            note.eventId = it
            // 更新事件的计数器
            val event = eventMap[it]
            event?.also {
                it.noteCount++
                // 更新数据库中的记录
                updateEvent(it)
                FileUtil.updateFile(note)
            }
            updateEventList()
            return true
        }

        return false
    }

    /**
     *  解除文章和事件的绑定关系
     *  @param note 需要解绑的文章
     */
    fun unbindNote (note: NoteFile): Boolean {

        if (note.eventId == 0) return false

        eventMap.get(note.eventId)?.let {

            it.noteCount--

            note.eventId = 0

            // 更新数据库
            FileUtil.updateFile(note)
            updateEvent(it)

            return true
        }

        return false
    }

}