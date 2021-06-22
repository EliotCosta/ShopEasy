package com.ec.shopeasy.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.data.User
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class PreferencesActivity : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var user : User

    private lateinit var sp : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private val dataProvider : DataProvider = DataProvider()
    private val gson = Gson()

    private lateinit var radioNutriScoreA : RadioButton
    private lateinit var radioNutriScoreB : RadioButton
    private lateinit var radioNutriScoreC : RadioButton
    private lateinit var radioNutriScoreD : RadioButton
    private lateinit var radioNutriScoreE : RadioButton
    private lateinit var allergensText : EditText

    private lateinit var validateBtn : Button

    private lateinit var loader : ProgressBar

    private val activityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        var nutriScoreImg = findViewById<ImageView>(R.id.nutriScoreImg)
        Picasso.get()
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Nutri-score-A.svg/1920px-Nutri-score-A.svg.png")
            .into(nutriScoreImg)

        radioNutriScoreA = findViewById(R.id.radioNutriScoreA)
        radioNutriScoreB = findViewById(R.id.radioNutriScoreB)
        radioNutriScoreC = findViewById(R.id.radioNutriScoreC)
        radioNutriScoreD = findViewById(R.id.radioNutriScoreD)
        radioNutriScoreE = findViewById(R.id.radioNutriScoreE)

        allergensText = findViewById(R.id.allergensTextView)

        validateBtn = findViewById(R.id.prefValidationBtn)

        loader = findViewById(R.id.progressBar2)
        loader.isVisible = false

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sp.edit()

        token = sp.getString("token", "").toString()
        user = gson.fromJson(sp.getString("user", ""), User::class.java)
        Log.i("PMR", "toto ${user.toString()}")
        displayUserInfo(user)

        validateBtn.setOnClickListener {
            validateModif()
        }
    }

    fun displayUserInfo(user : User) {
        Log.i("PMR", "${user.toString()}")
        when(user.minNutriScorePreference) {
            "a" -> radioNutriScoreA.setChecked(true)
            "b" -> radioNutriScoreB.setChecked(true)
            "c" -> radioNutriScoreC.setChecked(true)
            "d" -> radioNutriScoreD.setChecked(true)
            else -> radioNutriScoreE.setChecked(true)
        }

        allergensText.setText(user.allergens)
    }

    fun validateModif() {
        loader.isVisible = true

        if (radioNutriScoreA.isChecked) {
            user.minNutriScorePreference = "a"
        } else if (radioNutriScoreB.isChecked) {
            user.minNutriScorePreference = "b"
        } else if (radioNutriScoreC.isChecked) {
            user.minNutriScorePreference = "c"
        } else if (radioNutriScoreD.isChecked) {
            user.minNutriScorePreference = "d"
        } else if (radioNutriScoreE.isChecked) {
            user.minNutriScorePreference = "e"
        }

        user.allergens = allergensText.getText().toString()

        activityScope.launch {
            try {
                dataProvider.updateProfile(token, user)
                editor.putString("user", gson.toJson(user))
                editor.commit()
                loader.isVisible = false
                Toast.makeText(this@PreferencesActivity, "Vos préférences ont été mises à jour", Toast.LENGTH_SHORT).show()
            } catch (e : Exception) {
                Log.i("PMR", "${e.message}")
                loader.isVisible = false
            }
        }
    }
}