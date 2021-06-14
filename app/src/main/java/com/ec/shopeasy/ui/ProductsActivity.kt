package com.ec.shopeasy.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.Product
import com.ec.shopeasy.data.ProductCategories

class ProductsActivity: AppCompatActivity(), OnItemClickListener {

    private lateinit var liste: ProductCategories
    private lateinit var products: List<Product>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val bdl = this.intent.extras

        //products = ?

        //TODO Récup liste des produits, comment ?

        recyclerView = findViewById(R.id.recycler_item)
        recyclerView.adapter = ProductAdapter(products, this)
    }

    override fun onItemClicked(v: View, pos: Int) {
        TODO("Not yet implemented")
    }

}