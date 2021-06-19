package com.ec.shopeasy.productInfoApi

import com.ec.shopeasy.data.ProductInfos
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductInfoDataProvider {

    var baseUrl : String = "https://world.openfoodfacts.org/api/v0/"
    private var retrofit : Retrofit
    private var service  : ProductInfoService

    constructor() {
        this.retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(ProductInfoService::class.java)

    }

    suspend fun getProductInfo(barCode : String) : ProductInfos {
        return service.getProductInfo(barCode).product
    }
}