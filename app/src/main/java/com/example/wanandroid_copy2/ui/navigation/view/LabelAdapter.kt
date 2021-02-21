package com.example.wanandroid_copy2.ui.navigation.view

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.ui.navigation.data.LableRsp

class LabelAdapter(layoutId : Int, lists : List<LableRsp>?) : BaseQuickAdapter<LableRsp, BaseViewHolder>(layoutId, lists) {


    override fun convert(helper: BaseViewHolder, item: LableRsp?) {
        with(helper){
            setText(R.id.mLable,item?.title)
            addOnClickListener(R.id.mLable)
        }
    }
}