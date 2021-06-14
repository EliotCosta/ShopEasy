package com.ec.shopeasy.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.Product
import com.ec.shopeasy.data.ProductCategories

class ProductsActivity: AppCompatActivity() {

    private lateinit var liste: ProductCategories
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val bdl = this.intent.extras
        val id_cat = bdl!!.getString("id")!!


        //TODO Récup liste des produits, où ça?

        recyclerView = findViewById(R.id.recycler_item)
        recyclerView.adapter = ProductAdapter(liste.products as MutableList<Product>, this)
    }

}