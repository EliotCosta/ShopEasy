package com.ec.shopeasy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.api.response.ProductsResponse
import com.ec.shopeasy.data.ProductCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity: AppCompatActivity(), OnListClickListener {

    private lateinit var dataProvider: DataProvider
    private lateinit var categories : List<ProductCategories>

    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        // val bdl = this.intent.extras

        dataProvider = DataProvider()
        activityScope.launch {
            val response = dataProvider.getProductsCategoriesAndProducts()
            response.enqueue(object : Callback<ProductsResponse> {
                override fun onResponse(call: Call<ProductsResponse>, response: Response<ProductsResponse>) {
                    // Fonction appelée en cas de succes
                    val data : ProductsResponse? = response.body()
                    Log.i("PMR", "API Response : " + response.raw().toString())

                    if (data?.success == true) {
                        // test si l'api renvoie bien success = true, si ce n'est pas le cas, il y a un problème de requete
                        var categories = data.productCategories
                        Log.i("PMR", categories.toString())
                        listMaker(categories)

                    } else {
                        error("Network error")
                    }

                }

                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    // Fonction appelée en cas d'échec
                    error(t.message)
                }
            })


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
        val nameOfProducts = list.products.map {prod ->  prod.name}
        val productIds = list.products.map {prod ->  prod.id}
        bdl.putString("name",list.name)
        bdl.putStringArrayList("productNames",nameOfProducts as ArrayList)
        bdl.putIntegerArrayList("productIds",productIds as ArrayList)

        val toShow = Intent(this@CategoriesActivity, ProductsActivity::class.java)
        toShow.putExtras(bdl)
        startActivity(toShow)
    }
}