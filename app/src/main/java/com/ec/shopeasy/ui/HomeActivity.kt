package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.ProductCategories
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {
    private lateinit var btnList : Button
    private lateinit var btnPanier : Button
    private lateinit var btnPref : Button
    private lateinit var img : ImageView
    private lateinit var welcomeText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnList = findViewById(R.id.btnList)
        btnPanier = findViewById(R.id.btnPanier)
        btnPref = findViewById(R.id.btnPref)
        img = findViewById(R.id.item_img3)
        welcomeText = findViewById(R.id.welcomeText)

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudo = sp.getString("name", "inconnu")
        welcomeText.setText("Bonjour ${pseudo}")

        Picasso.get()
            .load("https://www.wedemain.fr/wp-content/uploads/2020/04/17599089-22052598.jpg")
            .into(img)

        btnList.setOnClickListener {
            var nextAct: Intent = Intent(this@HomeActivity, CategoriesActivity::class.java)
            startActivity(nextAct)
        }

        btnPanier.setOnClickListener {
            var nextAct: Intent = Intent(this@HomeActivity, PanierActivity::class.java)
            startActivity(nextAct)
        }

        btnPref.setOnClickListener {
            var nextAct: Intent = Intent(this@HomeActivity, PreferencesActivity::class.java)
            startActivity(nextAct)
        }
    }
}