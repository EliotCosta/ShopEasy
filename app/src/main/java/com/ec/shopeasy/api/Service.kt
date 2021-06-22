package com.ec.shopeasy.api

import com.ec.shopeasy.api.response.*
import com.ec.shopeasy.data.NutriScoreData
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @Headers("Content-Type: application/json")
    @GET("shops")
    suspend fun getShops() : ShopsResponse

    @GET("shops/{Id}")
    suspend fun getShop(@Path("Id") shopId : Int) : ShopResponse

    @GET("shops/{Id}/sections")
    suspend fun getShopSections(@Path("Id") shopId : Int) : ShopSectionsResponse

    @GET("products")
    suspend fun getProducts() : ProductsResponse

    @POST("users")
    suspend fun createUser(@Query("user") pseudo : String,@Query("password") pass : String) : UserResponse

    @POST("authenticate")
    suspend fun authenticate(@Query("user") pseudo : String,@Query("password") pass : String) : AuthenticateResponse

    @GET("profile")
    suspend fun getProfile(@Header("hash") hash : String) : UserResponse

    @PUT("users")
    suspend fun updateProfile(@Header("hash") hash : String, @Query("minNutriScorePreference") minNutriScorePreference : String, @Query("allergens") allergens : String) : UserResponse

}