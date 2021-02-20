package com.example.wanandroid_copy2.common.utils

import java.lang.reflect.ParameterizedType

/**
 * 利用反射获取类的class
 *
 */
object Util {


    fun <T> getClass(clz: Any): Class<T> {
        return (clz.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<T>
    }

}