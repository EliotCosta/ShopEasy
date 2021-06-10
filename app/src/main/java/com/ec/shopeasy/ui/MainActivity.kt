package com.ec.shopeasy.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.api.response.ShopsResponse
import com.ec.shopeasy.data.Shop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var dataProvider: DataProvider

    private val activityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Exemple d'appel API
        dataProvider = DataProvider()

        activityScope.launch {
            var response = dataProvider.getShops()
            response?.enqueue(object : Callback<ShopsResponse> {
                override fun onResponse(call: Call<ShopsResponse>, response: Response<ShopsResponse>) {
                    // Fonction appellée en cas de succes
                    var data : ShopsResponse? = response.body()
                    Log.i("PMR", "API Response : " + response.raw().toString())

                    if (data?.success == true) {
                        // test si l'api renvoie bien success = true, si ce n'est pas le cas, il y a un problème de requete
                        var shops : List<Shop> = (response.body() as ShopsResponse).shops
                        Log.i("PMR", shops.toString())

                    } else {
                        error("Network error")
                    }

                }

                override fun onFailure(call: Call<ShopsResponse>, t: Throwable) {
                    // Fonction appellée en cas d'échec
                    error(t.message)
                }
            })
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
        Log.i("EDPMR", sType)
        return bStatut
        return true
    }
}