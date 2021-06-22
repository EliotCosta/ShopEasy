package com.ec.shopeasy.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.api.response.ShopsResponse
import com.ec.shopeasy.data.Shop
import com.ec.shopeasy.productInfoApi.ProductInfoDataProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dataProvider: DataProvider
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var imageMain: ImageView
    private lateinit var picto: ImageView

    private val activityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener(this)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        btnSignup.setOnClickListener(this)
        val imageMain = findViewById<ImageView>(R.id.imageMain)
        val picto = findViewById<ImageView>(R.id.pictogrammeMain)

        // setup des images

        Picasso.get().load("https://www.wedemain.fr/wp-content/uploads/2020/04/17599089-22052598.jpg").into(imageMain)
        Picasso.get().load("https://media.istockphoto.com/vectors/shopping-cart-icon-isolated-on-white-background-vector-id1206806317?k=6&m=1206806317&s=612x612&w=0&h=Fo7D7nh_QPu758KRdbNTp7m4xSVOxBvJ2cfUvA1_k_U=").into(picto)



        // Exemple d'appel API
        dataProvider = DataProvider()

        activityScope.launch {
            try {
                var shops : List<Shop> = dataProvider.getShops()
                Log.i("PMR","ok")
                Log.i("PMR", shops.toString())
            } catch (e: Exception) {
                Log.i("PMR","pas ok")
                error(e.message)
            }
        }

        // Exemple d'info de produit
        val productsInfosDataProvider = ProductInfoDataProvider()
        activityScope.launch {
            try {
                var productInfo = productsInfosDataProvider.getProductInfo("3038350345004")
                // Test image
                //Picasso.get().load(productInfo.imageUrl).into(imageMain)
                Log.i("PMR", productInfo.toString())
            } catch (e: Exception) {
                error(e.message)
            }
        }

    }

    fun error(message : String?) {
        Log.i("PMR", message.orEmpty())
        Toast.makeText(this@MainActivity, "${message}", Toast.LENGTH_SHORT).show()
    }

    fun verifReseau(): Boolean {
        // On vérifie si le réseau est disponible,
        // si oui on change le statut du bouton de connexion
        val cnMngr: ConnectivityManager? = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cnMngr?.activeNetworkInfo
        var sType = "Aucun réseau détecté"
        var bStatut = false
        if (netInfo != null) {
            val netState = netInfo.state
            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
                bStatut = true
                val netType = netInfo.type
                when (netType) {
                    ConnectivityManager.TYPE_MOBILE -> sType = "Réseau mobile détecté"
                    ConnectivityManager.TYPE_WIFI -> sType = "Réseau wifi détecté"
                }
            }
        }
        Log.i("PMR", sType)
        return bStatut
        return true


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {

                // vers LoginActivity

                // Intent explicite
                var versLoginActivity: Intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(versLoginActivity)
            }

            R.id.btnSignup -> {

                // vers SignupActivity

                // Intent explicite
                var versSignupActivity: Intent = Intent(this@MainActivity, SignupActivity::class.java)
                startActivity(versSignupActivity)
            }
        }
    }
}