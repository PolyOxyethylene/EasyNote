package com.oxyethylene.easynotedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxyethylene.easynotedemo.domain.NoteList

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.viewmodel
 * @ClassName    : EventViewModel.java
 * @createTime   : 2024/1/19 18:57
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class EventViewModel (): ViewModel() {

    val noteList: LiveData<NoteList>
        get() = _noteList

    private val _noteList: MutableLiveData<NoteList>

    init {
        _noteList = MutableLiveData(NoteList())
    }

    fun updateNoteList (current: NoteList) {
        _noteList.value = current
    }

}