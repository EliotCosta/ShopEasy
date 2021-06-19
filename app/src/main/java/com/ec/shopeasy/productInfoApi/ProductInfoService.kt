package com.ec.shopeasy.productInfoApi

import com.ec.shopeasy.productInfoApi.response.ProductInfosResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductInfoService {

    @GET("product/{barCode}")
    suspend fun getProductInfo(@Path("barCode") barCode : String) : ProductInfosResponse
}