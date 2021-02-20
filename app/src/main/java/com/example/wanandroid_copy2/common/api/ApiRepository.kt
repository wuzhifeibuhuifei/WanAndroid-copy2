package com.example.wanandroid_copy2.common.api

import com.example.wanandroid_copy2.common.BaseRepository
import com.example.wanandroid_copy2.common.network.RetrofitFactory

/**
 * @author Laizexin on 2019/12/2
 * @description
 */
open class ApiRepository : BaseRepository() {

    public val apiService : ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }

}