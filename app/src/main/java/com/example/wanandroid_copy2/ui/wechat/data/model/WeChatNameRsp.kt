package com.example.wanandroid_copy2.ui.wechat.data.model

/**
 * @author Laizexin on 2019/12/9
 * @description 微信公众号列表
 */
data class WeChatNameRsp(
        var courseId: Int,
        var name: String,
        var id: Int,
        var order: Int,
        var parentChapterId: Int,
        var userControlSetTop: Boolean
)