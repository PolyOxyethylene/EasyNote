package com.oxyethylene.easynotedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxyethylene.easynotedemo.domain.Dir
import com.oxyethylene.easynotedemo.domain.EventList
import com.oxyethylene.easynotedemo.util.FileUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.viewmodel
 * @ClassName    : MainViewModel.java
 * @createTime   : 2023/12/14 20:38
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class MainViewModel (current : Dir = FileUtil.root) : ViewModel() {

    // 获取当前的目录
    val currentFolder : LiveData<Dir>
        get() = _currentFolder

    // 实际更新的 currentFolder
    private val _currentFolder : MutableLiveData<Dir>

    // 事件项的列表
    val eventEntryList : LiveData<EventList>
        get() = _eventEntryList

    private var _eventEntryList : MutableLiveData<EventList>

    // 初始化
    init {
        _currentFolder = MutableLiveData(current)
        _eventEntryList = MutableLiveData(EventList())
    }

    fun updateCurrentFolder (current: Dir) {
        _currentFolder.value = current
    }

    fun updateEventEntryList (current: EventList) {
        _eventEntryList.value = current
    }

}



//    // 获取当前编辑的文章标题
//    val currentNoteTitle : LiveData<String>
//        get() = _currentNoteTitle
//    // 实际更新的 currentNotTitle
//    private val _currentNoteTitle : MutableLiveData<String>
//
//    // 获取当前编辑的内容
//    val currentNoteContent : LiveData<String>
//        get() = _currentNoteContent
//    // 实际更新的 currentNoteContent
//    private val _currentNoteContent : MutableLiveData<String>
//
//    val writable : LiveData<Boolean>
//        get() = _writable
//
//    private val _writable : MutableLiveData<Boolean>

//    fun updateCurrentTitle (current: String) {
//        _currentNoteTitle.value = current
//    }
//
//    fun updateCurrentContent (current: String) {
//        _currentNoteContent.value = current
//    }
//
//    // 无参方法，清空内容用
//    fun updateCurrentContent () {
//        _currentNoteContent.value = ""
//    }
//
//    // 检查是否修改
//    fun isContentChanged () = NoteDefaults.defaultContent.equals(currentNoteContent)
//
//    fun enableWrite () {
//        _writable.value = true
//    }