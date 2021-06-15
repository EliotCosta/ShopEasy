package com.ec.shopeasy.shopping

import com.ec.shopeasy.data.Product

data class ShoppingStep (
        var type: Int,
        var sectionName : String = "",
        val x: Float = 0f,
        val y: Float = 0f,
        val nbProducts: Int = 0,
        val productList: MutableList<Product> = mutableListOf()
) {
    companion object {
        val GO_TO = 0
        val PRODUCTS = 1
        val CHECKOUT = 2
        val END = 3
    }
}