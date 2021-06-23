package com.ec.shopeasy.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.data.Shop
import com.ec.shopeasy.ui.adapters.OnShopClickListener
import com.ec.shopeasy.ui.adapters.ShopAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class ChoixShopActivity : AppCompatActivity(), View.OnClickListener, OnShopClickListener {

    private lateinit var dataProvider: DataProvider
    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private lateinit var image: ImageView
    private lateinit var btn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var shops: List<Shop>

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var getJson: String
    private lateinit var gson: Gson



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choixshop)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        gson = Gson()

        // Btn pour passer la choix du magasin, désactivé par défaut
        btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener(this)
        btn.isVisible = false

        image = findViewById<ImageView>(R.id.imageView)
        Picasso.get().load("https://i.ytimg.com/vi/A1Uv7eok7FU/maxresdefault.jpg").into(image)


        dataProvider = DataProvider()
        activityScope.launch {
            try {
                shops = dataProvider.getShops()
                Log.i("PMR", shops.toString())
                listMaker(shops)
            } catch (e: Exception) {
                error(e.message)
            }
        }
    }

    fun listMaker(dataset: List<Shop>) {
        recyclerView = findViewById(R.id.recyclerViewShops)
        recyclerView.adapter = ShopAdapter(dataset, this@ChoixShopActivity)
        recyclerView.layoutManager = LinearLayoutManager(this@ChoixShopActivity)
    }


    fun error(message : String?) {
        Log.i("PMR", message.orEmpty())
        Toast.makeText(this@ChoixShopActivity, "${message}", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btn -> {
                // Intent explicite
                var nextAct: Intent = Intent(this@ChoixShopActivity, ShoppingStartActivity::class.java)
                startActivity(nextAct)
            }
        }
    }

    override fun onShopClicked(v: View, pos: Int) {
        val shop = shops[pos]

        //Modification des shared preferences

        editor.putString("shop", gson.toJson(shop))
        editor.commit()

        var nextAct: Intent = Intent(this@ChoixShopActivity, ShoppingStartActivity::class.java)
        startActivity(nextAct)

    }
}