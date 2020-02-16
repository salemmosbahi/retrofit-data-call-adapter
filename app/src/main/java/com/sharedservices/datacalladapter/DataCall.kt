package com.sharedservices.datacalladapter

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DataCall<T>(private val call: Call<T>): Call<DataResponse<T>> {

    override fun enqueue(callback: Callback<DataResponse<T>>) =
        call.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                val result = if (code in 200 until 300) {
                    val body = response.body()
                    DataResponse.Success(body)
                } else {
                    DataResponse.Failure(code)
                }
                callback.onResponse(this@DataCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = if (t is IOException) {
                    DataResponse.NetworkError
                } else {
                    DataResponse.Failure(null)
                }
                callback.onResponse(this@DataCall, Response.success(result))
            }
        })


    override fun isExecuted(): Boolean = call.isExecuted

    override fun clone(): Call<DataResponse<T>> = DataCall(call.clone())

    override fun isCanceled(): Boolean = call.isCanceled

    override fun cancel() = call.cancel()

    override fun execute(): Response<DataResponse<T>> = throw NotImplementedError()

    override fun request(): Request = call.request()
}