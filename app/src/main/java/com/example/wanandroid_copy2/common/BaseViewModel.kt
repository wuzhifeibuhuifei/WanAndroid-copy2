package com.example.wanandroid_copy2.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.utils.Util

open class BaseViewModel<T : BaseRepository> : ViewModel() {

    val loadState by lazy {
        MutableLiveData<State>()
    }

    // 初始化repository方法
    val mRepository: T by lazy {
        Util.getClass<T>(this)
            .getDeclaredConstructor(MutableLiveData::class.java)
            .newInstance(loadState)
    }
}