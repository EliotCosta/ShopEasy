package com.ec.shopeasy.api

import com.ec.shopeasy.api.response.ProductsResponse
import com.ec.shopeasy.api.response.ShopResponse
import com.ec.shopeasy.api.response.ShopSectionsResponse
import com.ec.shopeasy.api.response.ShopsResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @GET("shops")
    fun getShops() : Call<ShopsResponse>

    @GET("shops/{Id}")
    fun getShop(@Path("Id") shopId : Int) : Call<ShopResponse>

    @GET("shops/{Id}/sections")
    fun getShopSections(@Path("Id") shopId : Int) : Call<ShopSectionsResponse>

    @GET("products")
    fun getProducts() : Call<ProductsResponse>

}