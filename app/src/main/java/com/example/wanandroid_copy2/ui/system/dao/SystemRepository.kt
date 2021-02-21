package com.example.wanandroid_copy2.ui.system.dao

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseObserver
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.api.ApiRepository
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.utils.execute
import com.kkaka.wanandroid.system.data.SystemAtricleRsp
import com.kkaka.wanandroid.system.data.TopMenuRsp

/**
 * @author Laizexin on 2019/12/17
 * @description
 */
class SystemRepository(val loadState : MutableLiveData<State>) : ApiRepository() {

    fun getTopMenu(liveData: MutableLiveData<BaseResponse<List<TopMenuRsp>>>) {
        apiService.getTopMenu().execute(BaseObserver(liveData, loadState,this))
    }

    fun getSystemArticles(page: Int, id: Int, liveData: MutableLiveData<BaseResponse<SystemAtricleRsp>>) {
        apiService.getSystemArticles(page, id).execute(BaseObserver(liveData, loadState,this))
    }

}