package com.ec.shopeasy.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.data.*
import com.ec.shopeasy.shopping.ShoppingInstance
import com.ec.shopeasy.shopping.ShoppingStep
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.vikramezhil.droidspeech.DroidSpeech
import com.vikramezhil.droidspeech.OnDSListener
import com.vikramezhil.droidspeech.OnDSPermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception


class ShoppingGuidanceActivity : AppCompatActivity(), OnDSListener, OnDSPermissionsListener {
    private val activityScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main
    )
    private var gson = Gson()
    private lateinit var shop : Shop
    private lateinit var shopSections : List<ShopSection>
    private lateinit var productCard : List<Product>

    private lateinit var shoppingInstance: ShoppingInstance
    private lateinit var currentStep: ShoppingStep

    private lateinit var sp : SharedPreferences
    private lateinit var dataProvider : DataProvider

    private lateinit var shoppingCardImg: ImageView
    private lateinit var shoppingCardValue : TextView
    private lateinit var progress : ProgressBar
    private lateinit var nextStepButton : Button
    private lateinit var stepText : TextView
    private lateinit var mapImg : ImageView

    private lateinit var btnScan : Button
    private lateinit var txtProd : TextView
    private lateinit var imgProd : ImageView

    private lateinit var ds: DroidSpeech
    var lastTimeWorking: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_guidance)

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

        dataProvider = DataProvider()

        // Get data from shared preferences
        sp = PreferenceManager.getDefaultSharedPreferences(this)
        shop = gson.fromJson(sp.getString("shop", "{}"), Shop::class.java)
        val name = sp.getString("name", "name").toString()
        productCard = gson.fromJson(sp.getString(name, "{\"name\": \"\", \"list\": []}"), ListeUser::class.java).list

        // Exemple de pannier
        shop = Shop(1, "V2", "maps/1.jpg", 1f)
        //shopSections = listOf(ShopSection(1,1,"Fruits",0f,2f, listOf(1,2)), ShopSection(2,1,"toto",1f,1f,listOf(3)))
        //productCard = listOf(Product(1, "Bananes"), Product(2, "Pommes"), Product(3, "Pull to toto"))


        // Get xml objects
        shoppingCardImg = findViewById(R.id.shoppingCardImg)
        shoppingCardValue = findViewById(R.id.shoppingCardValue)
        progress = findViewById(R.id.progressBar)
        nextStepButton = findViewById(R.id.nextStepBtn)
        stepText = findViewById(R.id.stepText)
        mapImg = findViewById(R.id.mapImg)
        btnScan = findViewById(R.id.tempBtnScan)
        txtProd = findViewById(R.id.textInfoProduit)
        imgProd = findViewById(R.id.imgInfos)

        loading(true)

        // Setup map
        Picasso.get().load(dataProvider.baseUrl + shop.urlMap).into(mapImg)
        Picasso.get().load("https://media.istockphoto.com/vectors/shopping-cart-icon-isolated-on-white-background-vector-id1206806317?k=6&m=1206806317&s=612x612&w=0&h=Fo7D7nh_QPu758KRdbNTp7m4xSVOxBvJ2cfUvA1_k_U=").into(shoppingCardImg)


        activityScope.launch {

            try {
                // Get shop sections
                shopSections = dataProvider.getShopSections(shop.id)
                Log.i("PMR", "ShopSections : ${shopSections.toString()}")
                // Initiate navigation object
                shoppingInstance = ShoppingInstance(shop, shopSections.toMutableList(), productCard.toMutableList())
                shoppingInstance.init()
                startNavigation()
            } catch (e: Exception) {
                Log.i("PMR", "Error : ${e.message}")
            }
        }

        // Droidspeech Section (commandes vocales)
        // Initializing the droid speech and setting the listener
        ds = DroidSpeech(applicationContext, null)
        ds.setOnDroidSpeechListener(this)
        ds.setShowRecognitionProgressView(false)
        ds.setOneStepResultVerify(false)


        // Starting droid speech
        //displayDroidSpeech.setContinuousSpeechRecognition(true);
        ds.startDroidSpeechRecognition()

        // Set button listener
        nextStepButton.setOnClickListener {
            nextStep()
        }

        btnScan.setOnClickListener {
            scan()
        }
    }


    var scanLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result?.data
                val productInfos = gson.fromJson(data?.extras?.getString("productInfos"), ProductInfos::class.java)
                handleCodeBarre(productInfos)
            } else {
                Toast.makeText(this, R.string.scanFail, Toast.LENGTH_LONG).show()
            }
    }

    fun scan() {
        val toScan: Intent = Intent(this@ShoppingGuidanceActivity, ScanActivity::class.java)
        try {
            scanLauncher.launch(toScan)
        } catch (e: Exception) {
            Log.e("PMRSCAN", e.message.toString())
        }
    }

    fun handleCodeBarre(infos: ProductInfos) {
        txtProd.text = "${infos.productNameFr} :\nNutriscore : ${infos.nutriScoreData.grade.toUpperCase()}\n" +
                "Calories : ${infos.nutriments.energy} kcal pour 100g\n"

        Picasso.get().load(infos.imageUrl).into(imgProd)
        showInfos(true)

    }


    fun loading(value : Boolean) {
        progress.isVisible = value
    }

    fun showMap(value: Boolean) {
        mapImg.isVisible = value
    }

    fun showInfos(value: Boolean) {
        if (value) {
            txtProd.visibility= View.VISIBLE
            imgProd.visibility= View.VISIBLE
        } else {
            txtProd.visibility= View.GONE
            imgProd.visibility= View.GONE
            // gone (contrairement à INVISIBLE) permet de disparaitre aux yeux du layout
        }
    }


    fun startNavigation() {
        // Starting navigation
        currentStep = shoppingInstance.nextStep()
        showStepInfo()
        loading(false)
    }

    fun showStepInfo() {
        // Show currentStep on screen
        showInfos(false)
        when (currentStep.type) {
            ShoppingStep.GO_TO -> {
                // Go to section step
                showMap(true)
                stepText.text = "Allez au rayon ${currentStep.sectionName}\n\nIl y a ${currentStep.nbProducts} produits à récupérer"
            }
            ShoppingStep.PRODUCTS -> {
                // Products step
                showMap(false)
                var text = "Prendre les produits :"
                currentStep.productList.forEach { product ->
                    text = text + "\n- ${product.name}"
                }
                stepText.text = text
            }
            ShoppingStep.CHECKOUT -> {
                // Checkout step
                showMap(false)
                stepText.text = "Passez à la caisse"
            }
        }
    }

    fun nextStep() {
        // Go to next step
        currentStep = shoppingInstance.nextStep()
        if (currentStep.type != ShoppingStep.END) {
            showStepInfo()
        } else {
            // End of shopping

            // Empty product list
            val name = sp.getString("name", "name").toString()
            val editor = sp.edit()
            editor.putString(name, "{\"name\": \"${name}\", \"list\": []}")
            editor.commit()

            // Go to home activity
            val nextAct: Intent = Intent(this@ShoppingGuidanceActivity, HomeActivity::class.java)
            startActivity(nextAct)
        }

    }

    //Interface qui gère la commande vocale

    override fun onDroidSpeechRmsChanged(rmsChangedValue: Float) {
        // Permet de visualiser des valeurs en nombre à chaque tonalité/ fréquence de la voix détécté
        Log.i(ContentValues.TAG, "Rms change value = $rmsChangedValue")
        lastTimeWorking = System.currentTimeMillis()
    }

    override fun onDroidSpeechSupportedLanguages(currentSpeechLanguage: String?, supportedSpeechLanguages: MutableList<String>?) {
        Log.i(
                ContentValues.TAG,
                "Supported speech languages = " + supportedSpeechLanguages.toString()
        )
        if (supportedSpeechLanguages!!.contains("fr-FR")) {
            // Setting the droid speech preferred language as french
            // Définir la langue préférée du discours de droid speech en français
            ds.setPreferredLanguage("fr-FR")
        }
        Log.i(ContentValues.TAG, "Current speech language = $currentSpeechLanguage")
    }

    override fun onDroidSpeechError(errorMsg: String?) {
        // Speech error
        // Permet d'afficher s'il y a une erreur
        //Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        Log.i(ContentValues.TAG, "Error $errorMsg")
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
        if (finalSpeechResult.equals("Suivant", ignoreCase = true)
                || finalSpeechResult!!.toLowerCase().contains("suivant")
        ) {
            nextStep()
        } else if (finalSpeechResult.equals("Scan", ignoreCase = true)
                || finalSpeechResult!!.toLowerCase().contains("scan")
        ) {
            scan()
        }
    }

    override fun onDroidSpeechAudioPermissionStatus(audioPermissionGiven: Boolean, errorMsgIfAny: String?) {
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