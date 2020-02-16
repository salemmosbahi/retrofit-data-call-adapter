package com.sharedservices.datacalladapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class DataCallAdapter(private val type: Type): CallAdapter<Type, Call<DataResponse<Type>>> {

    override fun adapt(call: Call<Type>): Call<DataResponse<Type>> = DataCall(call)

    override fun responseType(): Type = type
}