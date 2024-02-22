package com.oxyethylene.easynote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxyethylene.easynote.domain.Dir
import com.oxyethylene.easynote.domain.EventList
import com.oxyethylene.easynote.util.FileUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.viewmodel
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
    private var _currentFolder : MutableLiveData<Dir>

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
