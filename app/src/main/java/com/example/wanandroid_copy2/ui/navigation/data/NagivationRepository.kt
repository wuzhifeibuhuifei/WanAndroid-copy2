package com.example.wanandroid_copy2.ui.navigation.data

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseObserver
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.api.ApiRepository
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.utils.execute

/**
 * @author Laizexin on 2019/12/10
 * @description
 */
class NagivationRepository(val loadState : MutableLiveData<State>) : ApiRepository(){

    fun getCategory(livadata :  MutableLiveData<BaseResponse<List<NagivationCategoryRsp>>>) {
        apiService.getCategory().execute(BaseObserver(livadata,loadState,this))
    }
}