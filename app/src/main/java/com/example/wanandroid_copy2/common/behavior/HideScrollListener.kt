package com.example.wanandroid_copy2.common.behavior

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 添加滑动时的事件处理
abstract class HideScrollListener : RecyclerView.OnScrollListener() {

    // 设置移动的高度开始隐藏功能
    private val HIDE_THRESHOLD = 20

    // 默认滚动距离
    private var scrolledDistance = 0

    // 设置隐藏的条件
    private var controlsVisible = true


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // 如果顶到开头的话，一定是显示的
        var position =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (position == 0) {
            if (!controlsVisible) {
                controlsVisible = true
                onShow()
            }
        } else {
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                onHide()
                scrolledDistance = 0
                controlsVisible = false
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                onShow()
                scrolledDistance = 0
                controlsVisible = true
            }
            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy
            }
        }

    }

    abstract fun onShow()

    abstract fun onHide()
}