package com.ec.shopeasy.ui

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ec.shopeasy.R
import com.vikramezhil.droidspeech.DroidSpeech
import com.vikramezhil.droidspeech.OnDSListener
import com.vikramezhil.droidspeech.OnDSPermissionsListener

class ShoppingStartActivity : AppCompatActivity(), View.OnClickListener, OnDSListener, OnDSPermissionsListener {

    private lateinit var startShoppingBtn: Button
    private lateinit var ds: DroidSpeech

    var lastTimeWorking: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_start)

        //*** Forcing de la permission qui permet d'activer la commande vocale
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            1
        )


        // Droidspeech Section (commandes vocales)
        // Initializing the droid speech and setting the listener
        ds = DroidSpeech(applicationContext, null)
        ds.setOnDroidSpeechListener(this)
        ds.setShowRecognitionProgressView(false)
        ds.setOneStepResultVerify(false)


        // Starting droid speech
        //displayDroidSpeech.setContinuousSpeechRecognition(true);
        ds.startDroidSpeechRecognition()

        startShoppingBtn = findViewById<Button>(R.id.startBtn)
        startShoppingBtn.setOnClickListener {
            Log.i("PMR", "Démarrage des courses")

            var newAct: Intent = Intent(this@ShoppingStartActivity, ShoppingGuidanceActivity::class.java)
            startActivity(newAct)
        }
    }

    override fun onClick(v: View?) {

    }

    override fun onDroidSpeechRmsChanged(rmsChangedValue: Float) {
        // Permet de visualiser des valeurs en nombre à chaque tonalité/ fréquence de la voix détécté
        Log.i(TAG, "Rms change value = $rmsChangedValue")
        lastTimeWorking = System.currentTimeMillis()
    }

    override fun onDroidSpeechSupportedLanguages(
        currentSpeechLanguage: String?,
        supportedSpeechLanguages: MutableList<String>?
    ) {
        Log.i(
            TAG,
            "Supported speech languages = " + supportedSpeechLanguages.toString()
        )
        if (supportedSpeechLanguages!!.contains("fr-FR")) {
            // Setting the droid speech preferred language as french
            // Définir la langue préférée du discours de droid speech en français
            ds.setPreferredLanguage("fr-FR")
        }
        Log.i(TAG, "Current speech language = $currentSpeechLanguage")
    }

    override fun onDroidSpeechError(errorMsg: String?) {

        // Speech error
        // Permet d'afficher s'il y a une erreur
        //Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error $errorMsg")
        ds.closeDroidSpeechOperations()
    }

    override fun onDroidSpeechClosedByUser() {

    }

    override fun onDroidSpeechLiveResult(liveSpeechResult: String?) {

    }

    override fun onDroidSpeechFinalResult(finalSpeechResult: String?) {

        // Setting the final speech result
        //Possibilité de modifier les mots-clés
        //Définir un comportement pour chaque mot-clé
        if (finalSpeechResult.equals("Démarrer", ignoreCase = true)
            || finalSpeechResult!!.toLowerCase().contains("démarrer")
        ) {
            ds.closeDroidSpeechOperations()
            var newAct: Intent = Intent(this@ShoppingStartActivity, ShoppingGuidanceActivity::class.java)
            startActivity(newAct)
        }
    }

    override fun onDroidSpeechAudioPermissionStatus(
        audioPermissionGiven: Boolean,
        errorMsgIfAny: String?
    ) {
        if (audioPermissionGiven) {
            ds.startDroidSpeechRecognition()
        } else {
            if (errorMsgIfAny != null) {
                // Permissions error
                Toast.makeText(this, errorMsgIfAny, Toast.LENGTH_LONG).show()
            }
            ds.closeDroidSpeechOperations()
        }
    }
}