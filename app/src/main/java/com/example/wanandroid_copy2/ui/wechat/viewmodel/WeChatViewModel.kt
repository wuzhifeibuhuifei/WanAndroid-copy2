package com.example.wanandroid_copy2.ui.wechat.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.BaseViewModel
import com.example.wanandroid_copy2.ui.article.data.Article
import com.example.wanandroid_copy2.ui.wechat.data.model.WeChatListRsp
import com.example.wanandroid_copy2.ui.wechat.data.model.WeChatNameRsp
import com.example.wanandroid_copy2.ui.wechat.data.repository.WeChatRepository

class WeChatViewModel : BaseViewModel<WeChatRepository>() {

    var page = 0

    var articleList = ArrayList<Article>()

    var weChatLiveData = MutableLiveData<BaseResponse<List<WeChatNameRsp>>>()
    var weChatListLiveData = MutableLiveData<BaseResponse<WeChatListRsp>>()

    fun getWeChat() {
        mRepository.getWeChat(weChatLiveData)
    }

    fun getWeChatList(id: Int, page: Int) {
        mRepository.getWeChatList(id, page, weChatListLiveData)
    }
}