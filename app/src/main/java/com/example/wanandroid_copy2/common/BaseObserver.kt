package com.example.wanandroid_copy2.common

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_copy2.BuildConfig
import com.example.wanandroid_copy2.common.constant.Constant
import com.example.wanandroid_copy2.common.network.State
import com.example.wanandroid_copy2.common.network.StateType
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Laizexin on 2019/12/2
 * @description
 */
class BaseObserver<T : BaseResponse<*>>(
    private val liveData: MutableLiveData<T>,
    private val loadState: MutableLiveData<State>,
    private val repository: BaseRepository
) : Observer<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        repository.subscribe(d)
    }

    override fun onNext(response: T) {
        when(response.errorCode){
            Constant.RESPONSE_SUCCESS -> {
                if(response.data is List<*>){
                    if((response.data as List<*>).isEmpty()){
                        loadState.postValue(State(StateType.EMPTY))
                        return
                    }
                }
                loadState.postValue(State(StateType.SUCCESS))
                liveData.postValue(response)
            }
            Constant.RESPONSE_NOT_LOGIN ->{
//                UserContext.instance.logoutSuccess()
                loadState.postValue(State(StateType.ERROR,msg = "登录过期"))
            }
            else -> {
                loadState.postValue(State(StateType.ERROR,msg = response.errorMsg))
            }
        }
    }

    override fun onError(e: Throwable) {
        if(BuildConfig.DEBUG){
            e.message?.let { Logger.e(it) }
        }
        loadState.postValue(State(StateType.NETWORK_ERROR))
    }


}