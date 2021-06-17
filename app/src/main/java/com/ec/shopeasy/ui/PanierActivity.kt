package com.ec.shopeasy.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.ListeUser
import com.ec.shopeasy.data.Product
import com.google.gson.Gson

class PanierActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var name: String
    private lateinit var liste: ListeUser

    private lateinit var recyclerView: RecyclerView
    private lateinit var panier: List<Product>

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var getJson: String
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panier)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()
        gson = Gson()

        //on recupère les produits selectionnés dans SharedPreferences



        recyclerView = findViewById(R.id.recycler_panier)
        recyclerView.adapter = PanierAdapter(panier)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btn_panier = findViewById<Button>(R.id.btn_panier)
        btn_panier.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_panier -> {

                //val bdl = Bundle()

                // Intent explicite
                //var toShow: Intent = Intent(this@PanierActivity, ::class.java)
                //versTransitionActivity.putExtras(bdl)
                //startActivity(toShow)
            }
        }
    }
}