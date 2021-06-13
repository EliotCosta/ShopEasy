package com.ec.shopeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.ec.shopeasy.R

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button -> {

                // vers LocalisationActivity

                // Intent explicite
                var versLoginActivity: Intent =
                    Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(versLoginActivity)
            }
        }
    }
}