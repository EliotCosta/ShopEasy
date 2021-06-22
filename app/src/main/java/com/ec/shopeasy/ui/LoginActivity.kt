package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val activityScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main
    )

    private lateinit var pseudo : EditText
    private lateinit var mdp : EditText
    private lateinit var dataProvider: DataProvider
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin2 = findViewById<Button>(R.id.btnLogin2)
        btnLogin2.setOnClickListener(this)
        val btnShow = findViewById<Button>(R.id.btnShow)
        btnShow.setOnClickListener(this)
        val image = findViewById<ImageView>(R.id.item_img)
        pseudo = findViewById<EditText>(R.id.pseudo)
        mdp = findViewById<EditText>(R.id.mdp)

        dataProvider = DataProvider()

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
                activityScope.launch {
                    try {
                        // Authentification
                        var response = dataProvider.authenticate(pseudo.getText().toString(), mdp.getText().toString())
                        var token = response.hash
                        var user = response.user
                        Log.i("PMR", "Token : ${token}")
                        Log.i("PMR", "User : ${user.toString()}")

                        // Saving Info
                        val sp = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                        val editor = sp.edit()
                        editor.putString("name",user.pseudo)
                        editor.putString("user",gson.toJson(user))
                        editor.putString("token",token)
                        editor.commit()

                        // Changement d'activité
                        var nextAct: Intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(nextAct)
                    } catch (e: Exception) {
                        Log.i("PMR", "${e.message}")
                        Toast.makeText(this@LoginActivity, "Identifiants invalides", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}