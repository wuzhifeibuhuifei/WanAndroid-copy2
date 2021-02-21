package com.example.wanandroid_copy2.ui.article.view

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid_copy2.MainActivity
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.BaseFragment
import com.example.wanandroid_copy2.common.BaseViewModel
import com.example.wanandroid_copy2.common.behavior.HideScrollListener
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.network.StateType
import com.example.wanandroid_copy2.common.utils.Util
import com.example.wanandroid_copy2.ui.article.data.Article
import com.example.wanandroid_copy2.ui.home.view.BaseHomeAdapter
import com.example.wanandroid_copy2.ui.web.view.WebActivity
import kotlinx.android.synthetic.main.fragment_article.*

abstract class ArticleFragment<T : BaseViewModel<*>> : BaseFragment() {

    lateinit var baseAdapter: BaseHomeAdapter
    lateinit var mViewModel: T

    val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
//                    StateType.EMPTY -> showEmpty()
//                    StateType.SUCCESS -> showSuccess()
//                    StateType.LOADING -> showLoading()
//                    StateType.ERROR -> showTip(it.msg)
//                    StateType.NETWORK_ERROR -> showError("网络异常")
//                    StateType.TIP -> showTip(it.msg)
                    StateType.EMPTY -> Log.i("result", "显示空白页面")
                    StateType.SUCCESS -> Log.i("result", "加载成功")
                    StateType.LOADING -> Log.i("result", "正在加载中")
                    StateType.ERROR -> Log.i("result", "显示错误信息")
                    StateType.NETWORK_ERROR -> Log.i("result", "显示网络错误信息")
                    StateType.TIP -> Log.i("result", "显示提示信息")
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_article
    }

    override fun initView() {
        super.initView()
        initLifeCycle()
        initRefresh()
        initRecycleView()
    }

    private fun initLifeCycle() {
        mViewModel = ViewModelProviders.of(this).get(Util.getClass(this))
        mViewModel.loadState.observe(this, Observer {
            observer
        })
        dataObserver()
    }

    private fun initRecycleView() {
        mRvArticle.layoutManager = LinearLayoutManager(activity)
        baseAdapter = BaseHomeAdapter(R.layout.item_article, null)
        mRvArticle.adapter = baseAdapter
        baseAdapter.setEnableLoadMore(true)
        baseAdapter.setOnLoadMoreListener({ onLoadMore() }, mRvArticle)
        baseAdapter.setOnItemClickListener { adapter, view, position ->
            val item = baseAdapter.getItem(position) as Article
            item?.let {
                // 跳转到指定页面
                toPage(it)
            }
        }

        // 滚动过程
        mRvArticle.addOnScrollListener(object : HideScrollListener() {
            override fun onShow() {
                if (activity is MainActivity) {
                    (activity as MainActivity).onHide()
                }
            }

            override fun onHide() {
                if (activity is MainActivity) {
                    (activity as MainActivity).onShow()
                }
            }
        })
    }

    // 跳转到web页面
    private fun toPage(t: Article) {
        var intent = Intent(context, WebActivity::class.java).apply {
            putExtra("url", t.link)
            putExtra("title", t.title)
        }
        startActivity(intent)
    }

    private fun initRefresh() {
        msrlRefresh.setColorSchemeResources(R.color.colorPrimaryDark)
        msrlRefresh.setOnRefreshListener {
            onRefreshData()
        }
    }

    open fun addData(datas: List<Article>) {
        if (datas.isEmpty()) {
            baseAdapter.loadMoreEnd()
            return
        }

        //下拉刷新 注意完成加载更多(存在加载更多时刷新的情况)
        if (msrlRefresh.isRefreshing) {
            msrlRefresh.isRefreshing = false
            baseAdapter.setNewData(datas)
            baseAdapter.loadMoreComplete()
            return
        }

        //加载更多
        baseAdapter.addData(datas)
        baseAdapter.loadMoreComplete()
    }

    // 刷新
    abstract fun onRefreshData()

    // 加载更多的方法
    abstract fun onLoadMore()

    // 数据监测
    abstract fun dataObserver()
}