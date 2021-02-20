package com.example.wanandroid_copy2.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.BaseViewModel
import com.example.wanandroid_copy2.ui.article.data.Article
import com.example.wanandroid_copy2.ui.home.data.model.BannerRsp
import com.example.wanandroid_copy2.ui.home.data.model.HomeArticleRsp
import com.example.wanandroid_copy2.ui.home.data.repository.HomeRepository

class HomeViewModel() : BaseViewModel<HomeRepository>() {

    // 当前加载的页数
    var page = 0
    val articleList = ArrayList<Article>()
    // 图片列表
    val bannerImgs = ArrayList<String>()
    // 标题列表
    val bannerTitles = ArrayList<String>()
    // 图片跳转的链接列表
    val bannerUrls = ArrayList<String>()

    val articleLiveData = MutableLiveData<BaseResponse<HomeArticleRsp>>()
    val bannerLiveData = MutableLiveData<BaseResponse<List<BannerRsp>>>()

    fun getArticle(page: Int) {
        mRepository.getArticle(page, articleLiveData)
    }

    fun getBanner() {
        mRepository.getBanner(bannerLiveData)
    }

}