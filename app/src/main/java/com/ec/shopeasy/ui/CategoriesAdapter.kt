package com.ec.shopeasy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.ProductCategories

class CategoriesAdapter (
    private val dataset: List<ProductCategories>,
    private val listClickListener: OnListClickListener): RecyclerView.Adapter<CategoriesAdapter.ListViewHolder>() {

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.cat_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.category, parent, false)

        return ListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list = dataset[position]

        holder.textView.text = list.name
        holder.view.setOnClickListener {
            listClickListener.onListClicked(list)
        }
    }

    override fun getItemCount() = dataset.size
}

interface OnListClickListener {
    fun onListClicked(list: ProductCategories)
}