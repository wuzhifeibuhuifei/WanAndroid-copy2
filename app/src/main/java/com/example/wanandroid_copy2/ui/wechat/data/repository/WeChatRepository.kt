package com.example.wanandroid_copy2.ui.wechat.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseObserver
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.api.ApiRepository
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.utils.execute
import com.example.wanandroid_copy2.ui.wechat.data.model.WeChatListRsp
import com.example.wanandroid_copy2.ui.wechat.data.model.WeChatNameRsp

class WeChatRepository(private val loadState: MutableLiveData<State>) : ApiRepository() {

    fun getWeChat(liveData: MutableLiveData<BaseResponse<List<WeChatNameRsp>>>) {
        apiService.getWeChat().execute(BaseObserver(liveData, loadState, this))
    }

    fun getWeChatList(id: Int, page: Int, liveData: MutableLiveData<BaseResponse<WeChatListRsp>>) {
        apiService.getWeChatList(id, page).execute(BaseObserver(liveData, loadState, this))
    }
}