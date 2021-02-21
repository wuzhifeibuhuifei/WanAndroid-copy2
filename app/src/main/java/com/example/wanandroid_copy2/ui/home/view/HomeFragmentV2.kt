package com.example.wanandroid_copy2.ui.home.view

import android.view.View
import androidx.lifecycle.Observer
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.utils.GlideImageLoader
import com.example.wanandroid_copy2.ui.article.view.ArticleFragment
import com.example.wanandroid_copy2.ui.home.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_home_headview.view.*

class HomeFragmentV2 : ArticleFragment<HomeViewModel>() {

    private lateinit var mBanner: Banner

    override fun initView() {
        super.initView()
        initBannerView()
        bannerObserver()
        dataObserver()
    }

    override fun initData() {
        super.initData()
        mViewModel.page = 0
        mViewModel.getArticle(mViewModel.page)
        mViewModel.getBanner()
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
        baseAdapter.addHeaderView(bannerView)
    }

    private fun bannerObserver() {
        mViewModel.bannerLiveData.observe(viewLifecycleOwner, { response ->
            response?.let {
                mViewModel.bannerImgs.clear()
                mViewModel.bannerTitles.clear()
                mViewModel.bannerUrls.clear()
                response.data.forEach {
                    mViewModel.bannerImgs.add(it.imagePath)
                    mViewModel.bannerTitles.add(it.title)
                    mViewModel.bannerUrls.add(it.url)
                }
                // 设置banner图片列表和标题，并开始轮播图
                mBanner.setImages(mViewModel.bannerImgs)
                    .setBannerTitles(mViewModel.bannerTitles).start()
            }
        })
    }

    override fun onRefreshData() {
        // 重新加载数据
        mViewModel.page = 0
        mViewModel.getArticle(mViewModel.page)
    }

    override fun onLoadMore() {
        mViewModel.page++
        mViewModel.getArticle(mViewModel.page)
    }

    override fun dataObserver() {
        mViewModel.articleLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                addData(it.data.datas)
            }
        })
    }
}