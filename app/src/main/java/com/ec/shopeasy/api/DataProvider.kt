package com.ec.shopeasy.api

import com.ec.shopeasy.api.response.ProductsResponse
import com.ec.shopeasy.api.response.ShopResponse
import com.ec.shopeasy.api.response.ShopSectionsResponse
import com.ec.shopeasy.api.response.ShopsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataProvider {

    var baseUrl : String = "http://10.0.2.2/shopeasy-api/"
    private var retrofit : Retrofit
    private var service  : Service

    constructor() {
        this.retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Service::class.java)

    }

    fun getShops() : Call<ShopsResponse> {
        return service.getShops()
    }

    fun getShop(shopId: Int) : Call<ShopResponse> {
        return service.getShop(shopId)
    }

    suspend fun getShopSections(shopId: Int) : ShopSectionsResponse {
        return service.getShopSections(shopId)
    }

    fun getProductsCategoriesAndProducts() : Call<ProductsResponse> {
        return service.getProducts()
    }


}