package com.ec.shopeasy.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.Product

class ProductAdapter(
    private val dataset: List<Product>,
    private val itemClickListener: OnItemClickListener):
        RecyclerView.Adapter<ProductAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //val imgView: ImageView = view.findViewById(R.id.item_img)
        val textView: TextView = view.findViewById(R.id.item_title)
        val btn: Button = view.findViewById(R.id.add_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
        //holder.imgView.setImageResource(item.image)
        holder.btn.setOnClickListener{
            itemClickListener.onItemClicked(holder.btn, position)
            Log.i("PMR","hello")
        }
    }

    override fun getItemCount() = dataset.size
}

interface OnItemClickListener {
    fun onItemClicked(v: View, pos: Int)
}