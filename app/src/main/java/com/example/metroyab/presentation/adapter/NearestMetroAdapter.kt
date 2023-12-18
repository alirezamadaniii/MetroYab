package com.example.metroyab.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.metroyab.data.model.Item
import com.example.metroyab.data.model.Search
import com.example.metroyab.databinding.ItemNearestMetroBinding
import java.lang.NullPointerException
import javax.inject.Inject

class NearestMetroAdapter @Inject constructor() : RecyclerView.Adapter<NearestMetroAdapter.MyViewHolder>() {



    private val callback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.title == newItem.title
        }


        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNearestMetroBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }



    inner class MyViewHolder(val binding: ItemNearestMetroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            if (item.type == "subway_station"){
                binding.txtStationName.text = item.title
                binding.txtRouting.text = "1.2"
            }


            binding.btnRoutingMap.setOnClickListener {
                onItemClick?.let {
                    it(item)
                }
            }

        }





    }
    private var onItemClick: ((Item) -> Unit)? = null
    fun setOnItemClick(listener: (Item) -> Unit) {
        onItemClick = listener
    }

}