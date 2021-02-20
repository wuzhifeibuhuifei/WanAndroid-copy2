package com.example.wanandroid_copy2.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Laizexin on 2019/11/28
 * @description 负责对数据请求做批量管理
 */
open class BaseRepository {

    private val mCompositeSubscription by lazy { CompositeDisposable() }

    fun subscribe(disposable: Disposable) = mCompositeSubscription.add(disposable)

    fun unSubscribe() = mCompositeSubscription.dispose()

}