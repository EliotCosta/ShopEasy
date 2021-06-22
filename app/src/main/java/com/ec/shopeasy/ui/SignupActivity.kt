package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private val activityScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main
    )

    private lateinit var pseudo : EditText
    private lateinit var mdp : EditText
    private lateinit var mdpConfirmation : EditText
    private lateinit var dataProvider: DataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        pseudo = findViewById<EditText>(R.id.pseudo2)
        mdp = findViewById<EditText>(R.id.mdp2)
        mdpConfirmation = findViewById<EditText>(R.id.mdpConfirm)

        val image = findViewById<ImageView>(R.id.item_img2)

        Picasso.get()
                .load("https://www.wedemain.fr/wp-content/uploads/2020/04/17599089-22052598.jpg")
                .into(image)

        dataProvider = DataProvider()

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button -> {

                if (mdp.getText().toString() == mdpConfirmation.getText().toString()) {
                    activityScope.launch {
                        try {
                            dataProvider.createUser(pseudo.getText().toString(), mdp.getText().toString())
                            Toast.makeText(this@SignupActivity, "Le compte a bien été crée", Toast.LENGTH_SHORT).show()
                            // Intent explicite
                            var versLoginActivity: Intent =
                                    Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(versLoginActivity)
                        } catch(e : Exception) {
                            Log.i("PMR", "${e.message}")
                            Toast.makeText(this@SignupActivity, "Pseudo déjà pris", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@SignupActivity, "Les deux champs mot de passe n'ont pas la même valeur", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}