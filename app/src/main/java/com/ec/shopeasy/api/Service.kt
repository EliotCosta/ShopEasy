package com.ec.shopeasy.api

import com.ec.shopeasy.api.response.ProductsResponse
import com.ec.shopeasy.api.response.ShopResponse
import com.ec.shopeasy.api.response.ShopSectionsResponse
import com.ec.shopeasy.api.response.ShopsResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @GET("shops")
    suspend fun getShops() : ShopsResponse

    @GET("shops/{Id}")
    suspend fun getShop(@Path("Id") shopId : Int) : ShopResponse

    @GET("shops/{Id}/sections")
    suspend fun getShopSections(@Path("Id") shopId : Int) : ShopSectionsResponse

    @GET("products")
    suspend fun getProducts() : ProductsResponse

}