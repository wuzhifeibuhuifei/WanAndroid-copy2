package com.example.wanandroid_copy2.ui.wechat.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class WeChatAdapter(private val fragmentManager: FragmentManager,
                    private val titles: List<String>,
                    private val fragments: List<Fragment>)
    : FragmentStatePagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}