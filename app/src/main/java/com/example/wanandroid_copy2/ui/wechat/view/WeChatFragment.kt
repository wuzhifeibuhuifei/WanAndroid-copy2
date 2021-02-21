package com.example.wanandroid_copy2.ui.wechat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.ui.home.viewmodel.HomeViewModel
import com.example.wanandroid_copy2.ui.wechat.data.model.WeChatNameRsp
import com.example.wanandroid_copy2.ui.wechat.viewmodel.WeChatViewModel
import kotlinx.android.synthetic.main.fragment_wechat.*

class WeChatFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WeChatViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wechat, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mTabLayout.setupWithViewPager(mContent)
        dataObserver()
    }

    private fun dataObserver() {
        // 进行数据的观察
        viewModel.weChatLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                builderData(it.data)
            }
        })
    }

    private fun builderData(lists: List<WeChatNameRsp>) {
        var titleList = mutableListOf<String>()
        var fragments = mutableListOf<Fragment>()
        for (item in lists) {
            titleList.add(item.name)
            fragments.add(WeChatListFragment.instance(item.id))
        }
        mContent.adapter = WeChatAdapter(childFragmentManager, titleList, fragments)
    }

    private fun initData() {
        viewModel.getWeChat()
    }

}