package com.ec.shopeasy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.api.DataProvider
import com.ec.shopeasy.data.ProductCategories
import com.squareup.picasso.Picasso

class CategoriesAdapter(
        private val dataset: List<ProductCategories>,
        private val listClickListener: OnListClickListener): RecyclerView.Adapter<CategoriesAdapter.ListViewHolder>() {

    private var dataProvider : DataProvider = DataProvider()

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView = view.findViewById(R.id.img_cat)
        val textView: TextView = view.findViewById(R.id.cat_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.category, parent, false)

        return ListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cat = dataset[position]

        Picasso.get().load(dataProvider.baseUrl + cat.urlImg).into(holder.imgView)

        holder.textView.text = cat.name
        holder.view.setOnClickListener {
            listClickListener.onListClicked(cat)
        }
    }

    override fun getItemCount() = dataset.size
}

interface OnListClickListener {
    fun onListClicked(list: ProductCategories)
}