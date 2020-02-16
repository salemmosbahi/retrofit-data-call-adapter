package com.sharedservices.datacalladapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class DataCallAdapterFactory: CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                DataResponse::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    DataCallAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}