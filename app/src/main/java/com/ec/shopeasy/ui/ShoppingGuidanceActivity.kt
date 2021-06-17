package com.ec.shopeasy.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.api.response.ShopSectionsResponse
import com.ec.shopeasy.api.response.ShopsResponse
import com.ec.shopeasy.data.ListeUser
import com.ec.shopeasy.data.Product
import com.ec.shopeasy.data.Shop
import com.ec.shopeasy.data.ShopSection
import com.ec.shopeasy.shopping.ShoppingInstance
import com.ec.shopeasy.shopping.ShoppingStep
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.reflect.Type


class ShoppingGuidanceActivity : AppCompatActivity() {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_guidance)

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

        loading(true)

        // Setup map
        Picasso.get().load(dataProvider.baseUrl + shop.urlMap).into(mapImg)
        Picasso.get().load("https://media.istockphoto.com/vectors/shopping-cart-icon-isolated-on-white-background-vector-id1206806317?k=6&m=1206806317&s=612x612&w=0&h=Fo7D7nh_QPu758KRdbNTp7m4xSVOxBvJ2cfUvA1_k_U=").into(shoppingCardImg)


        activityScope.launch {

            try {
                // Get shop sections
                shopSections = dataProvider.getShopSections(shop.id).sections
                Log.i("PMR", "ShopSections : ${shopSections.toString()}")
                // Initiate navigation object
                shoppingInstance = ShoppingInstance(shop, shopSections.toMutableList(), productCard.toMutableList())
                shoppingInstance.init()
                startNavigation()
            } catch (e: Exception) {
                Log.i("PMR", "Error : ${e.message}")
            }
        }

        // Set button listener
        nextStepButton.setOnClickListener {
            nextStep()
        }
    }

    fun loading(value : Boolean) {
        progress.isVisible = value
    }

    fun showMap(value: Boolean) {
        mapImg.isVisible = value
    }

    fun startNavigation() {
        // Starting navigation
        currentStep = shoppingInstance.nextStep()
        showStepInfo()
        loading(false)
    }

    fun showStepInfo() {
        // Show currentStep on screen
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
            var editor = sp.edit()
            editor.putString(name, "{\"name\": \"${name}\", \"list\": []}")
            editor.commit()

            // Go to main activity
            var nextAct: Intent = Intent(this@ShoppingGuidanceActivity, MainActivity::class.java)
            startActivity(nextAct)
        }

    }
}