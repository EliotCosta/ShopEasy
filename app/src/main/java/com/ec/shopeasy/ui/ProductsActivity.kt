package com.ec.shopeasy.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.Product
import com.ec.shopeasy.data.ProductCategories
import java.util.ArrayList

class ProductsActivity: AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val bdl = this.intent.extras
        val productNames= bdl?.get("productNames") as ArrayList<String>
        val productIds = bdl.get("productIds") as ArrayList<Int>

        var products= mutableListOf<Product>()
        for (i in 0..productIds.size-1) {
            products.add(Product(productIds[i], productNames[i]))
        }

        recyclerView = findViewById(R.id.recycler_item)
        recyclerView.adapter = ProductAdapter(products as List<Product>, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClicked(v: View, pos: Int) {
        TODO("Not yet implemented")
    }

}