package com.example.wanandroid_copy2.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.ui.article.data.Article
import org.jetbrains.anko.find

class HomeAdapter(private var lists: List<Article>) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = lists.get(position)
        holder.author.text = article.author
        holder.title.text = article.title
        holder.time.text = if(article.niceDate.isEmpty()) article.niceDate else article.niceShareDate
        holder.category.text = buildCategory(article)
        holder.collect.setImageResource(isCollect(article.collect))
        holder.news.visibility = if(article.fresh) View.VISIBLE else View.INVISIBLE
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

    override fun getItemCount(): Int {
        return lists.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var author: TextView = itemView.find(R.id.mTvAuthor)
        var title: TextView = itemView.find(R.id.mTvTitle)
        var time: TextView = itemView.find(R.id.mTvTime)
        var category: TextView = itemView.find(R.id.mTvCategory)
        var collect: ImageView = itemView.find(R.id.mIvCollect)
        var news: ImageView = itemView.find(R.id.mIvNews)
    }
}