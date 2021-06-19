package com.ec.shopeasy.api

import com.ec.shopeasy.api.response.ProductsResponse
import com.ec.shopeasy.api.response.ShopResponse
import com.ec.shopeasy.api.response.ShopSectionsResponse
import com.ec.shopeasy.api.response.ShopsResponse
import com.ec.shopeasy.data.ProductCategories
import com.ec.shopeasy.data.Shop
import com.ec.shopeasy.data.ShopSection
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

    suspend fun getShops() : List<Shop> {
        return service.getShops().shops
    }

    suspend fun getShop(shopId: Int) : Shop {
        return service.getShop(shopId).shop
    }

    suspend fun getShopSections(shopId: Int) : List<ShopSection> {
        return service.getShopSections(shopId).sections
    }

    suspend fun getProductsCategoriesAndProducts() : List<ProductCategories> {
        return service.getProducts().productCategories
    }


}