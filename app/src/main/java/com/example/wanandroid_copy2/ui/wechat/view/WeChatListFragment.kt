package com.example.wanandroid_copy2.ui.wechat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.wanandroid_copy2.ui.article.data.Article
import com.example.wanandroid_copy2.ui.article.view.ArticleFragment
import com.example.wanandroid_copy2.ui.home.view.BaseHomeAdapter
import com.example.wanandroid_copy2.ui.wechat.viewmodel.WeChatViewModel

class WeChatListFragment : ArticleFragment<WeChatViewModel>() {

    private val uid by lazy {
        arguments?.getInt("uid") ?: -1
    }

    companion object {
        fun instance(uid: Int): Fragment {
            var fragment = WeChatListFragment()
            fragment.arguments = Bundle().apply {
                putInt("uid", uid)
            }
            return fragment
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getWeChatList(uid, mViewModel.page)
    }

    override fun onRefreshData() {
        mViewModel.page = 0
        mViewModel.getWeChatList(uid, mViewModel.page)
    }

    override fun onLoadMore() {
        mViewModel.getWeChatList(uid, ++mViewModel.page)
    }

    override fun dataObserver() {
        mViewModel.weChatListLiveData.observe(viewLifecycleOwner, Observer {
            addData(it.data.datas)
        })
    }
}