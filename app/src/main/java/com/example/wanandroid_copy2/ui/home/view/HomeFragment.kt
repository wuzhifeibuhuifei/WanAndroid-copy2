package com.example.wanandroid_copy2.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.network.StateType
import com.example.wanandroid_copy2.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class HomeFragment : Fragment() {

    lateinit var adapter: HomeAdapter

    val homeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 创建下拉数据
        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        mRvArticle.layoutManager = LinearLayoutManager(activity)
        // 初始化适配器
        adapter = HomeAdapter(homeViewModel.articleList)
        mRvArticle.adapter = adapter
    }

    private fun initData() {
        val page = 0
        homeViewModel.getArticle(page)
        loadStateObserve()
        articleListObserve()
    }

    private fun articleListObserve() {
        homeViewModel.articleLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                homeViewModel.articleList.addAll(it.data.datas)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun loadStateObserve() {
        homeViewModel.loadState.observe(viewLifecycleOwner, {
            Observer<State> {
                it?.let {
                    when (it.code) {
                        //                        StateType.EMPTY -> showEmpty()
                        //                        StateType.SUCCESS -> showSuccess()
                        //                        StateType.LOADING -> showLoading()
                        //                        StateType.ERROR -> showTip(it.msg)
                        //                        StateType.NETWORK_ERROR -> showError("网络异常")
                        //                        StateType.TIP -> showTip(it.msg)
                        StateType.EMPTY -> Log.i("result", "显示空白页面")
                        StateType.SUCCESS -> Log.i("result", "加载成功")
                        StateType.LOADING -> Log.i("result", "正在加载中")
                        StateType.ERROR -> Log.i("result", "显示错误信息")
                        StateType.NETWORK_ERROR -> Log.i("result", "显示网络错误信息")
                        StateType.TIP -> Log.i("result", "显示提示信息")
                    }
                }
            }
        })
    }
}