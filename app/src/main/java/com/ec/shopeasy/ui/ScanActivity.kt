package com.ec.shopeasy.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.data.Shop
import com.ec.shopeasy.productInfoApi.ProductInfoDataProvider
import com.google.gson.Gson
import com.google.zxing.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.lang.Exception

class ScanActivity: AppCompatActivity(), ZXingScannerView.ResultHandler {

    lateinit var myScannerView: ZXingScannerView
    lateinit var myCameraView: FrameLayout
    lateinit var message: TextView

    lateinit var gson: Gson
    lateinit var productsInfosDataProvider: ProductInfoDataProvider
    val activityScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        message = findViewById(R.id.text1)
        myCameraView = findViewById(R.id.camera_preview)
        productsInfosDataProvider = ProductInfoDataProvider()
        gson = Gson()

        //initialisation
        myScannerView= ZXingScannerView(applicationContext)
        myCameraView.addView(myScannerView)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),1)

        //Start scan
        myScannerView.setResultHandler(this)
        myScannerView.startCamera()


    }

    @Override
    override fun handleResult(result: Result) {
        val code=result.text
        Log.v("PMRSCAN","Scan : "+code)
        Log.v("PMRSCAN","Format du scan : "+result.barcodeFormat.toString())

        if (result.barcodeFormat.toString()=="EAN_13") {
            // On conserve que ce format pour éviter les faux codes.
            // Peut-être qu'il y a plusieurs types acceptés, à màj en fonction

            activityScope.launch {
                var infos: String? = null
                try {
                    val productInfo = productsInfosDataProvider.getProductInfo(code)
                    if (productInfo!=null) {
                        infos = gson.toJson(productInfo)
                        Log.i("PMRSCAN", "Infos récupérées : " + infos)
                        val returnIntent = Intent()
                        returnIntent.putExtra("productInfos", infos)
                        setResult(Activity.RESULT_OK, returnIntent)
                        finish()
                    } else {
                        // Il faudrait afficher un toast différent dans ce cas là,
                        // plus sympa pour l'utilisateur
                        Log.i("PMRSCAN", "Produit inconnu")
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                } catch (e: Exception) {
                    Log.i("PMRSCAN", e.message.orEmpty())
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }

            }
        } else {
            Log.i("PMRSCAN", "Mauvais format de scan")
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

    @Override
    override fun onPause() {
        //Called when activity is paused
        super.onPause()
        myScannerView.stopCamera() // Stop camera on pause
    }

    override fun onResume() {
        super.onResume()
        myScannerView.startCamera()
    }
}