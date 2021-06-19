package com.ec.shopeasy.productInfoApi.response

import com.ec.shopeasy.data.ProductInfos

data class ProductInfosResponse (
        var code : String,
        var product : ProductInfos
)