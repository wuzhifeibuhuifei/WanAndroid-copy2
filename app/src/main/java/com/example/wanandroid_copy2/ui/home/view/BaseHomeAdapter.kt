package com.example.wanandroid_copy2.ui.home.view

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.utils.toHtml
import com.example.wanandroid_copy2.ui.article.data.Article

class BaseHomeAdapter(layoutId: Int, list: List<Article>?)
    : BaseQuickAdapter<Article, BaseViewHolder>(layoutId, list) {


    override fun convert(helper: BaseViewHolder, item: Article) {
        item.let {
            helper.setText(R.id.mTvAuthor, if(it.author.isEmpty()) it.shareUser else it.author)
                .setText(R.id.mTvTitle, it.title.toHtml())
                .setText(R.id.mTvTime, if(it.niceDate.isEmpty()) it.niceDate else it.niceShareDate)
                .setText(R.id.mTvCategory, buildCategory(it))
                .setImageResource(R.id.mIvCollect, isCollect(it.collect))
                .setVisible(R.id.mIvNews, it.fresh)
                .addOnClickListener(R.id.mIvCollect)
        }
    }

    private fun isCollect(collect: Boolean): Int = if(collect) R.drawable.ic_collection else R.drawable.ic_uncollection

    private fun buildCategory(it: Article): String {
        return when{
            it.superChapterName.isNullOrEmpty() && it.chapterName.isNullOrEmpty() ->  ""
            it.superChapterName.isNullOrEmpty() ->  it.chapterName ?: ""
            it.chapterName.isNullOrEmpty() -> it.superChapterName ?: ""
            else -> "${it.superChapterName} / ${it.chapterName}"
        }
    }
}