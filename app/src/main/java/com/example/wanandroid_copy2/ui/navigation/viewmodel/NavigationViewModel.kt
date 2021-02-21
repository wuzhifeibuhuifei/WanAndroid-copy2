package com.example.wanandroid_copy2.ui.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.common.BaseResponse
import com.example.wanandroid_copy2.common.BaseViewModel
import com.example.wanandroid_copy2.ui.navigation.data.NagivationCategoryRsp
import com.example.wanandroid_copy2.ui.navigation.data.NagivationRepository

class NavigationViewModel : BaseViewModel<NagivationRepository>() {

    val categoryLiveData = MutableLiveData<BaseResponse<List<NagivationCategoryRsp>>>()

    fun getCategory() {
        mRepository.getCategory(categoryLiveData)
    }
}