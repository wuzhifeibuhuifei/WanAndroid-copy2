package com.example.wanandroid_copy2.ui.article.data

data class Article(
        var id: Int,
        var author: String,
        var chapterName: String?,
        var desc: String,
        var link: String,
        var originId: Int,
        var title: String,
        var collect: Boolean,
        var superChapterName: String?,
        var niceDate: String,
        var niceShareDate: String,
        var shareUser: String,
        var fresh: Boolean

)