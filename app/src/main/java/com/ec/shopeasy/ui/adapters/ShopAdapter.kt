package com.ec.shopeasy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ec.shopeasy.R
import com.ec.shopeasy.data.Shop

class ShopAdapter(
        private val dataset: List<Shop>,
        private val shopClickListener: OnShopClickListener):
        RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_shop)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.shop, parent, false)

        return ShopViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
        //holder.imgView.setImageResource(item.image)
        holder.textView.setOnClickListener {
            shopClickListener.onShopClicked(holder.textView, position)
        }

    }

    override fun getItemCount() = dataset.size
}

interface OnShopClickListener {
    fun onShopClicked(v: View, pos: Int)
}