package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.ec.shopeasy.R

class PanierActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panier)

        val btn_panier = findViewById<Button>(R.id.btn_panier)
        btn_panier.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_panier -> {

                //val bdl = Bundle()
                //bdl.putString("json", )

                // vers LoginActivity

                // Intent explicite
                //var toShow: Intent = Intent(this@PanierActivity, ::class.java)
                //versTransitionActivity.putExtras(bdl)
                //startActivity(toShow)
            }
        }
    }
}