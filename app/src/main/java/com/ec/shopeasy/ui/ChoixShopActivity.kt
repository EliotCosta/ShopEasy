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

        btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener(this)
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
            /**val response = dataProvider.getShops()
            response.enqueue(object : Callback<ShopsResponse> {
                override fun onResponse(call: Call<ShopsResponse>, response: Response<ShopsResponse>) {
                    // Fonction appelée en cas de succes
                    val data : ShopsResponse? = response.body()
                    Log.i("PMR", "API Response : " + response.raw().toString())

                    if (data?.success == true) {
                        // test si l'api renvoie bien success = true, si ce n'est pas le cas, il y a un problème de requete
                        shops = data.shops
                        Log.i("PMR", shops.toString())
                        listMaker(shops)

                    } else {
                        error("Network error")
                    }

                }

                override fun onFailure(call: Call<ShopsResponse>, t: Throwable) {
                    // Fonction appelée en cas d'échec
                    error(t.message)
                }
            })**/


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

                val bdl = Bundle()
                //bdl.putString("json", )

                // vers LoginActivity

                // Intent explicite
                var nextAct: Intent = Intent(this@ChoixShopActivity, ShoppingStartActivity::class.java)
                nextAct.putExtras(bdl)
                startActivity(nextAct)
            }
        }
    }

    override fun onShopClicked(v: View, pos: Int) {

        val bdl = Bundle()
        val shop = shops[pos]

        //Modification des shared preferences

        editor.putString("shop", shop.name)
        editor.commit()

        bdl.putString("shop",shop.name)

        var nextAct: Intent = Intent(this@ChoixShopActivity, ShoppingStartActivity::class.java)
        nextAct.putExtras(bdl)
        startActivity(nextAct)

    }
}