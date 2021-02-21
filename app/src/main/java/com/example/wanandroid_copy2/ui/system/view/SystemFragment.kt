package com.example.wanandroid_copy2.ui.system.view

import androidx.recyclerview.widget.GridLayoutManager
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.ui.article.view.ArticleFragment
import com.example.wanandroid_copy2.ui.system.viewmodel.SystemViewModel
import com.kkaka.wanandroid.system.data.TopMenu
import kotlinx.android.synthetic.main.fragment_article.*

class SystemFragment : ArticleFragment<SystemViewModel>() {

    lateinit var mAdapter : SystemMenuAdapter

    override fun initView() {
        super.initView()


        mRvArticle.layoutManager = GridLayoutManager(activity, 4)
        mAdapter = SystemMenuAdapter(R.layout.item_system_top_menu_content,R.layout.item_system_top_menu_head,null)
        mRvArticle.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            var item = mAdapter.getItem(position) as TopMenu
        }
    }

    override fun onRefreshData() {
        TODO("Not yet implemented")
    }

    override fun onLoadMore() {
        TODO("Not yet implemented")
    }

    override fun dataObserver() {
        TODO("Not yet implemented")
    }

}