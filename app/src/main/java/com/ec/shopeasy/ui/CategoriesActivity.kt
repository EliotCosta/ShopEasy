package com.ec.shopeasy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.api.response.ProductsResponse
import com.ec.shopeasy.data.Product
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

    private val activityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        dataProvider = DataProvider()

        activityScope.launch {
            val response = dataProvider.getProductsCategoriesAndProducts()
            response.enqueue(object : Callback<ProductsResponse> {
                override fun onResponse(call: Call<ProductsResponse>, response: Response<ProductsResponse>) {
                    // Fonction appellée en cas de succes
                    val data : ProductsResponse? = response.body()
                    Log.i("PMR", "API Response : " + response.raw().toString())

                    if (data?.success == true) {
                        // test si l'api renvoie bien success = true, si ce n'est pas le cas, il y a un problème de requete
                        categories = (response.body() as ProductsResponse).productCategories
                        Log.i("PMR", categories.toString())

                    } else {
                        error("Network error")
                    }

                }

                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    // Fonction appellée en cas d'échec
                    error(t.message)
                }
            })
        }



        recyclerView = findViewById(R.id.recycler_cat)
        recyclerView.adapter = CategoriesAdapter(categories, this)
    }

    fun error(message : String?) {
        Log.i("PMR", message.orEmpty())
        Toast.makeText(this@CategoriesActivity, "${message}", Toast.LENGTH_SHORT).show()
    }


    override fun onListClicked(list: ProductCategories) {

        val bdl = Bundle()
        //TODO passer l'objet ProductCategory choisi dans un bundle (serialization ?)
        //bdl.putParcelable("category",list)

        // vers la ShowListActivity correspondante
        val toShow = Intent(this@CategoriesActivity, ProductsActivity::class.java)
        toShow.putExtras(bdl)
        startActivity(toShow)
    }
}