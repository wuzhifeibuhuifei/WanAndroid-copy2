package com.example.wanandroid_copy2.ui.system.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.BaseViewModel
import com.example.wanandroid_copy2.ui.system.dao.SystemRepository
import com.kkaka.wanandroid.system.data.SystemAtricleRsp
import com.kkaka.wanandroid.system.data.TopMenuRsp

class SystemViewModel : BaseViewModel<SystemRepository>() {

    var topMenuLiveData = MutableLiveData<BaseResponse<List<TopMenuRsp>>>()
    var systemArticleLiveData = MutableLiveData<BaseResponse<SystemAtricleRsp>>()

    fun getTopMenu() {
        mRepository.getTopMenu(topMenuLiveData)
    }

    fun getSystemArticles(page: Int, id: Int) {
        mRepository.getSystemArticles(page, id, systemArticleLiveData)
    }
}