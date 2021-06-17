package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ec.shopeasy.R

class ShoppingStartActivity : AppCompatActivity() {

    private lateinit var startShoppingBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_start)

        startShoppingBtn = findViewById<Button>(R.id.startBtn)
        startShoppingBtn.setOnClickListener {
            Log.i("PMR", "Démarrage des courses")

            var newAct: Intent = Intent(this@ShoppingStartActivity, ShoppingGuidanceActivity::class.java)
            startActivity(newAct)
        }
    }
}