package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.ec.shopeasy.R
import com.squareup.picasso.Picasso

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin2 = findViewById<Button>(R.id.btnLogin2)
        btnLogin2.setOnClickListener(this)
        val btnShow = findViewById<Button>(R.id.btnShow)
        btnShow.setOnClickListener(this)
        val image = findViewById<ImageView>(R.id.item_img)
        val pseudo = findViewById<EditText>(R.id.pseudo)
        val mdp = findViewById<EditText>(R.id.mdp)

        Picasso.get()
            .load("https://www.wedemain.fr/wp-content/uploads/2020/04/17599089-22052598.jpg")
            .into(image)



        btnShow.setOnClickListener {
            if (btnShow.text.toString().equals("Show")) {
                mdp.transformationMethod = HideReturnsTransformationMethod.getInstance()
                btnShow.text = "Hide"
            } else {
                mdp.transformationMethod = PasswordTransformationMethod.getInstance()
                btnShow.text = "Show"
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin2 -> {

                // à faire : vérification des identifiants de l'utilisateur

                // vers LocalisationActivity

                // Intent explicite
                //Normalement pas categories mais location dans ligne suivante
                var versLocalisationActivity: Intent = Intent(this@LoginActivity, ChoixShopActivity::class.java)
                startActivity(versLocalisationActivity)
            }
        }
    }
}