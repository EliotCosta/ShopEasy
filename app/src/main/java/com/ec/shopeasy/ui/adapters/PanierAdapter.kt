package com.ec.shopeasy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.Product

class PanierAdapter(
        private val dataset: List<Product>):
        RecyclerView.Adapter<PanierAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //val imgView: ImageView = view.findViewById(R.id.item_img)
        val textView: TextView = view.findViewById(R.id.item_panier)
        //val btn: Button = view.findViewById(R.id.add_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.panier, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
        //holder.imgView.setImageResource(item.image)

    }

    override fun getItemCount() = dataset.size
}

