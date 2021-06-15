package com.ec.shopeasy.shopping

import com.ec.shopeasy.data.Product
import com.ec.shopeasy.data.Shop
import com.ec.shopeasy.data.ShopSection
import kotlin.math.pow

class ShoppingInstance (
        private var shop : Shop,
        private var shopSections: MutableList<ShopSection>,
        private var shopingList: MutableList<Product>
) {

    private var steps : MutableList<ShoppingStep> = mutableListOf()
    private var currentStep : Int = -1

    suspend fun init() {
        // Sort sections according to their location
        shopSections.sortWith(SectionComparator)

        // Create a list of sections saving the products to get there
        var sectionsWithProducts: MutableList<SectionWithProducts> = mutableListOf()
        shopSections.forEach { section ->
            sectionsWithProducts.add(SectionWithProducts(
                    name = section.name,
                    x = section.x,
                    y = section.y,
                    productList = section.productList,
                    shoppingProductList = mutableListOf(),
                    nbProducts = 0
            ))
        }

        // Match each product in shopping list with shop sections
        shopingList.forEach {product ->
            var section = sectionsWithProducts.find{
                it.productList.contains(product.id)
            }
            section?.shoppingProductList?.add(product)
            section?.nbProducts?.inc()
        }

        // Keep only sections where there are products to purchase
        sectionsWithProducts.filter { section ->
            section.nbProducts > 0
        }


        sectionsWithProducts.forEach { section ->
            steps.add(ShoppingStep(
                    ShoppingStep.GO_TO,
                    section.name,
                    section.x,
                    section.y,
                    section.nbProducts
            ))
            steps.add(ShoppingStep(
                    ShoppingStep.PRODUCTS,
                    section.name,
                    section.x,
                    section.y,
                    section.nbProducts,
                    section.shoppingProductList
            ))
        }
        steps.add(
                ShoppingStep(ShoppingStep.CHECKOUT)
        )
        steps.add(
                ShoppingStep(ShoppingStep.END)
        )
    }

    // Return next shopping step
    fun nextStep() : ShoppingStep {
        return steps.get(currentStep.inc())
    }

    // Comparator used to order shop sections left to right, top to bottom
    class SectionComparator {
        companion object : Comparator<ShopSection> {
            override fun compare(a: ShopSection, b: ShopSection): Int {
                if (a.x.pow(2) + a.y.pow(2) < b.x.pow(2) + b.y.pow(2)) {
                    return 1
                } else {
                    return -1
                }
            }
        }
    }

    data class SectionWithProducts(
            var name: String,
            var x: Float,
            var y: Float,
            var productList: List<Int>,
            var shoppingProductList: MutableList<Product>,
            var nbProducts : Int
    )

}