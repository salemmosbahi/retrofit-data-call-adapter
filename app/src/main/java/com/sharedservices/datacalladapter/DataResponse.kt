package com.sharedservices.datacalladapter

sealed class DataResponse<out T> {
    object NetworkError: DataResponse<Nothing>()
    data class Failure(val statusCode: Int?): DataResponse<Nothing>()
    data class Success<T>(val data: T?): DataResponse<T>()
}