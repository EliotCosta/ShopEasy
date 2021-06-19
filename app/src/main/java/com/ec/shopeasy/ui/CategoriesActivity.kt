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
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.data.ProductCategories
import com.ec.shopeasy.ui.adapters.CategoriesAdapter
import com.ec.shopeasy.ui.adapters.OnListClickListener
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CategoriesActivity: AppCompatActivity(), View.OnClickListener, OnListClickListener {

    private lateinit var name : String
    private lateinit var dataProvider: DataProvider
    private lateinit var categories : List<ProductCategories>

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var products: String
    private lateinit var gson: Gson

    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        gson=Gson()

        val bdl = this.intent.extras
        //TODO passer le nom de l'utilisateur courant depuis l'activité prec
        //name= bdl?.get("name") as String
        name="Mathis"
        editor.putString("name",name)
        editor.commit()


        val btn_panier = findViewById<Button>(R.id.btn_vers_liste)
        btn_panier.setOnClickListener(this)
        val vider_panier = findViewById<Button>(R.id.btn_empty)
        vider_panier.setOnClickListener(this)

        dataProvider = DataProvider()
        activityScope.launch {
            try {
                categories = dataProvider.getProductsCategoriesAndProducts()
                Log.i("PMR","catégories importées")
                listMaker(categories)
            } catch (e: Exception) {
                error(e.message)
            }
            /**val response = dataProvider.getProductsCategoriesAndProducts()
            response.enqueue(object : Callback<ProductsResponse> {
                override fun onResponse(call: Call<ProductsResponse>, response: Response<ProductsResponse>) {
                    // Fonction appelée en cas de succes
                    val data : ProductsResponse? = response.body()
                    Log.i("PMR", "API Response : " + response.raw().toString())

                    if (data?.success == true) {
                        // test si l'api renvoie bien success = true, si ce n'est pas le cas, il y a un problème de requete
                        categories = data.productCategories
                        Log.i("PMR","catégories importées")
                        listMaker(categories)

                    } else {
                        error("Network error")
                    }

                }

                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    // Fonction appelée en cas d'échec
                    error(t.message)
                }
            })**/


        }



    }

    fun listMaker(dataset: List<ProductCategories>) {
        recyclerView = findViewById(R.id.recycler_cat)
        recyclerView.adapter = CategoriesAdapter(dataset, this@CategoriesActivity)
        recyclerView.layoutManager = LinearLayoutManager(this@CategoriesActivity)
    }


    fun error(message : String?) {
        Log.i("PMR", message.orEmpty())
        Toast.makeText(this@CategoriesActivity, "${message}", Toast.LENGTH_SHORT).show()
    }


    override fun onListClicked(list: ProductCategories) {
        val bdl = Bundle()
        products = gson.toJson(list)
        bdl.putString("products",products)
        bdl.putString("name",name)

        //val nameOfProducts = list.products.map {prod ->  prod.name}
        //val productIds = list.products.map {prod ->  prod.id}
        //bdl.putString("name",list.name)
        //bdl.putStringArrayList("productNames",nameOfProducts as ArrayList)
        //bdl.putIntegerArrayList("productIds",productIds as ArrayList)

        val toShow = Intent(this@CategoriesActivity, ProductsActivity::class.java)
        toShow.putExtras(bdl)
        startActivity(toShow)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_vers_liste -> {
                val bdl = Bundle()
                bdl.putString("name",name)


                Log.i("PMR","activité suivante")
                Log.i("PMR",name+" : "+sp.getString(name,"{\"name\": $name, \"list\": []}").toString())

                val toShow = Intent(this@CategoriesActivity, PanierActivity::class.java)
                toShow.putExtras(bdl)
                startActivity(toShow)
            }
            R.id.btn_empty -> {
                editor.putString(name,"{\"name\": $name, \"list\": []}")
                editor.commit()
                Log.i("PMR","Panier vidé")
                Toast.makeText(this@CategoriesActivity, "Panier vidé", Toast.LENGTH_SHORT).show()
            }
        }
    }
}