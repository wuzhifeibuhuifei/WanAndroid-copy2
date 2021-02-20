package com.example.wanandroid_copy2.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.network.StateType
import com.example.wanandroid_copy2.common.utils.GlideImageLoader
import com.example.wanandroid_copy2.ui.article.data.Article
import com.example.wanandroid_copy2.ui.home.viewmodel.HomeViewModel
import com.example.wanandroid_copy2.ui.web.view.WebActivity
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.layout_home_headview.*
import kotlinx.android.synthetic.main.layout_home_headview.view.*

class HomeFragmentV2 : Fragment() {

    private lateinit var adapter: BaseHomeAdapter
    private lateinit var mBanner: Banner

    private val homeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRefresh()
        initRecyclerView()
        initBannerView()
        initData()
    }

    private fun initRefresh() {
        msrlRefresh.setColorSchemeResources(R.color.colorPrimaryDark)
        msrlRefresh.setOnRefreshListener {
            onRefreshData()
        }
    }

    private fun initBannerView() {
        var bannerView = View.inflate(activity, R.layout.layout_home_headview, null)
        mBanner = bannerView.mBanner
        mBanner
            .setImageLoader(GlideImageLoader())
            .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            .setDelayTime(3000)
            .setBannerAnimation(Transformer.FlipHorizontal)
            .setOnBannerListener {
                // startActivity<WebActivity>("url" to bannerUrls[it],"title" to bannerTitles[it])
            }
        // 在adapter中添加头部布局
        adapter.addHeaderView(bannerView)
    }

    private fun initRecyclerView() {
        mRvArticle.layoutManager = LinearLayoutManager(activity)
        adapter = BaseHomeAdapter(R.layout.item_article, homeViewModel.articleList)
        mRvArticle.adapter = adapter
        // 设置加载更多
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({
            onLoadMore()
        }, mRvArticle)

        // 点击跳转到web界面
        adapter.setOnItemClickListener { adapter, view, position ->
            var item = adapter.getItem(position) as Article
            item?.let { t ->
                // 根据article跳转到指定页面
                toPage(t)
            }
        }

        // 点击收藏

        //滑动隐藏菜单特效
//        mRvArticle.addOnScrollListener(object : Hide)
    }

    // 跳转到web页面
    private fun toPage(t: Article) {
        var intent = Intent(context, WebActivity::class.java).apply {
            putExtra("url", t.link)
            putExtra("title", t.title)
        }
        startActivity(intent)
    }

    private fun initData() {
        homeViewModel.page = 0
        homeViewModel.getArticle(homeViewModel.page)
        homeViewModel.getBanner()
        loadStateObserve()
        articleListObserve()
        bannerObserve()
    }

    private fun bannerObserve() {
        homeViewModel.bannerLiveData.observe(viewLifecycleOwner, { response ->
            response?.let {
                homeViewModel.bannerImgs.clear()
                homeViewModel.bannerTitles.clear()
                homeViewModel.bannerUrls.clear()
                response.data.forEach {
                    homeViewModel.bannerImgs.add(it.imagePath)
                    homeViewModel.bannerTitles.add(it.title)
                    homeViewModel.bannerUrls.add(it.url)
                }
                // 设置banner图片列表和标题，并开始轮播图
                mBanner.setImages(homeViewModel.bannerImgs)
                    .setBannerTitles(homeViewModel.bannerTitles).start()
            }
        })
    }

    // 观察页面的文章列表
    private fun articleListObserve() {
        homeViewModel.articleLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                addData(it.data.datas)
            }
        })
    }

    // 观察页面不同的加载状态显示不同的内容
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

    private fun addData(datas: List<Article>) {
        // 如果为空，则需要加载更多
        if (datas.isEmpty()) {
            adapter.loadMoreEnd()
            return
        }

        //下拉刷新
        if (msrlRefresh.isRefreshing) {
            msrlRefresh.isRefreshing = false
            adapter.setNewData(datas)
            adapter.loadMoreComplete()
            return
        }

        // 新增数据
        adapter.addData(datas)
        adapter.loadMoreComplete()
    }

    private fun onRefreshData() {
        // 重新加载数据
        homeViewModel.page = 0
        homeViewModel.getArticle(homeViewModel.page)
    }

    private fun onLoadMore() {
        homeViewModel.page++
        homeViewModel.getArticle(homeViewModel.page)
    }
}