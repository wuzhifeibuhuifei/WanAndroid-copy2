package com.example.wanandroid_copy2.ui.system.view

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.wanandroid_copy2.R
import com.kkaka.wanandroid.system.data.TopMenu

/**
 * @author Laizexin on 2019/12/17
 * @description
 */
class SystemMenuAdapter(layoutId : Int,headLayoutId :Int,topMenu : List<TopMenu>?) : BaseSectionQuickAdapter<TopMenu, BaseViewHolder>(layoutId,headLayoutId,topMenu) {

    override fun convertHead(helper: BaseViewHolder, item: TopMenu?) {
        with(helper){
            setText(R.id.mTvHead,item?.header)
            addOnClickListener(R.id.mTvMore)
        }
    }

    override fun convert(helper: BaseViewHolder, item: TopMenu?) {
        val topMenuRsp = item?.t
        helper.setText(R.id.mTvSecond,topMenuRsp?.name)
    }

}