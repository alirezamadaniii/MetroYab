package com.example.metroyab.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.metroyab.R
import com.example.metroyab.data.model.Item
import com.google.android.material.textview.MaterialTextView

class SearchBottomSheetAdapter(
    private var mList: List<Item>?,
    private val name: (Item) -> Unit
) :
    RecyclerView.Adapter<SearchBottomSheetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_sheet, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mList?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: MaterialTextView = itemView.findViewById(R.id.txt)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mList?.get(position)
        holder.item.text = item?.title
        holder.item.setOnClickListener {
            item?.let { it1 -> name(it1) }
        }
    }
}