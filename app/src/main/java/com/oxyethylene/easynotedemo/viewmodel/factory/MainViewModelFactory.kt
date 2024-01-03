package com.oxyethylene.easynotedemo.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxyethylene.easynotedemo.domain.Dir
import com.oxyethylene.easynotedemo.viewmodel.MainViewModel

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
class MainViewModelFactory (private val currentDir: Dir) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(currentDir) as T
    }
}