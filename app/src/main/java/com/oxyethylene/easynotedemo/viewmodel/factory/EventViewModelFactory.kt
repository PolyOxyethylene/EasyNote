package com.oxyethylene.easynotedemo.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxyethylene.easynotedemo.viewmodel.EventViewModel

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.viewmodel.factory
 * @ClassName    : MainViewModelFactory.java
 * @createTime   : 2023/12/14 21:26
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
class EventViewModelFactory () : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel() as T
    }
}