package com.example.wanandroid_copy2.ui.home.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseObserver
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.api.ApiRepository
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.utils.execute
import com.example.wanandroid_copy2.ui.home.data.model.BannerRsp
import com.example.wanandroid_copy2.ui.home.data.model.HomeArticleRsp

class HomeRepository(private val loadState: MutableLiveData<State>) : ApiRepository() {

    fun getArticle(page: Int, liveData: MutableLiveData<BaseResponse<HomeArticleRsp>>) {
        apiService.getHomeArticle(page).execute(BaseObserver(liveData, loadState, this))
    }

    fun getBanner(liveData: MutableLiveData<BaseResponse<List<BannerRsp>>>) {
        apiService.getBanner().execute(BaseObserver(liveData, loadState, this))
    }
}