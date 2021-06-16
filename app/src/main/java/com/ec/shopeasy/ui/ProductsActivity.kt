package com.ec.shopeasy.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.ListeUser
import com.ec.shopeasy.data.Product
import com.ec.shopeasy.data.ProductCategories
import com.google.gson.Gson
import java.util.ArrayList

class ProductsActivity: AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private lateinit var name: String
    private lateinit var liste: ListeUser

    private lateinit var recyclerView: RecyclerView
    private lateinit var products: List<Product>

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var getJson: String
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        gson = Gson()

        val bdl = this.intent.extras
        name = bdl?.get("name") as String
        val from:String = bdl?.get("products") as String
        val categories:ProductCategories = gson.fromJson(from, ProductCategories::class.java)
        products=categories.products

        recyclerView = findViewById(R.id.recycler_item)
        recyclerView.adapter = ProductAdapter(products, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btn_panier = findViewById<Button>(R.id.btn_vers_liste)
        btn_panier.setOnClickListener(this)
        val vider_panier = findViewById<Button>(R.id.btn_empty)
        vider_panier.setOnClickListener(this)
    }

    override fun onItemClicked(v: View, pos: Int) {
        val btn = v as Button
        val product = products[pos]
        getJson = sp.getString(name,"{\"name\": $name, \"list\": []}").toString()
        liste=gson.fromJson(getJson, ListeUser::class.java)
        liste.list.add(product)
        getJson=gson.toJson(liste)
        Log.i("PMR",product.name+" ajouté au panier de $name !")
        editor.putString(name,getJson)
        editor.commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_vers_liste -> {
                val bdl = Bundle()
                bdl.putString("name",name)

                //TODO passage à activité Panier

                Log.i("PMR","activité suivante")
                Log.i("PMR",name+" : "+sp.getString(name,"{\"name\": $name, \"list\": []}").toString())

                val toShow = Intent(this@ProductsActivity, PanierActivity::class.java)
                toShow.putExtras(bdl)
                startActivity(toShow)
            }
            R.id.btn_empty -> {
                editor.putString(name,"{\"name\": $name, \"list\": []}")
                editor.commit()
                Log.i("PMR","Panier vidé")
                Toast.makeText(this@ProductsActivity, "Panier vidé", Toast.LENGTH_SHORT).show()
            }
        }
    }

}