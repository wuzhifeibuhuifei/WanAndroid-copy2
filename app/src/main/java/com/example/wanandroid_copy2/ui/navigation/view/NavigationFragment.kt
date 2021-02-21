package com.example.wanandroid_copy2.ui.navigation.view

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid_copy2.MainActivity
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.BaseFragment
import com.example.wanandroid_copy2.common.behavior.HideScrollListener
import com.example.wanandroid_copy2.ui.navigation.data.LableRsp
import com.example.wanandroid_copy2.ui.navigation.data.NagivationCategoryRsp
import com.example.wanandroid_copy2.ui.navigation.viewmodel.NavigationViewModel
import com.example.wanandroid_copy2.ui.web.view.WebActivity
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_nagivation.*

class NavigationFragment : BaseFragment() {

    private lateinit var mAdapter: NavigationAdapter
    private lateinit var lAdapter: LabelAdapter
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(NavigationViewModel::class.java)
    }
    private lateinit var mNagivationCategoryRspList: List<NagivationCategoryRsp>

    override fun getLayoutId(): Int {
        return R.layout.fragment_nagivation
    }

    override fun initView() {
        super.initView()
        mRvCategory.layoutManager = LinearLayoutManager(activity)
        mAdapter = NavigationAdapter(R.layout.item_category, null)
        mRvCategory.adapter = mAdapter

        mRvCategory.addOnScrollListener(object : HideScrollListener() {
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

        // 左边部分
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            setSelectCategory(position)
            setSelectLables(position)
        }

        // 右边部分
        mRvLabel.layoutManager = FlexboxLayoutManager(activity)
        lAdapter = LabelAdapter(R.layout.item_lable, null)
        mRvLabel.adapter = lAdapter
        lAdapter.setOnItemChildClickListener { adapter, view, position ->
            // 跳转到web页面
            var item = adapter.getItem(position) as LableRsp
            toPage(item)
        }
        dataObserver()
    }

    private fun dataObserver() {
        mViewModel.categoryLiveData.observe(this, Observer {response ->
            response?.let {
                mNagivationCategoryRspList = it.data
                mAdapter.addData(it.data)
                setSelectCategory(0)
                setSelectLables(0)
            }
        })
    }

    override fun initData() {
        super.initData()
        mViewModel.getCategory()
    }

    private fun setSelectLables(position: Int) {
        lAdapter.replaceData(mNagivationCategoryRspList[position].articles)
    }

    private fun setSelectCategory(position: Int) {
        mAdapter.selectPosition = position
        mAdapter.notifyDataSetChanged()
    }

    private fun toPage(item: LableRsp) {
        startActivity(Intent(activity, WebActivity::class.java).apply {
            putExtra("url", item.link)
            putExtra("title", item.title)
        })
    }
}