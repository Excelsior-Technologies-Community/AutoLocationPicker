package com.ext.locationpicker.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ext.locationpicker.R

class SimplePickerAdapter(
    private var items: List<String>,
    private val onClick: (String, Int) -> Unit
) : RecyclerView.Adapter<SimplePickerAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_picker, parent, false)
        return VH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.title.text = items[position]
        holder.itemView.setOnClickListener {
            onClick(items[position], position)
        }
    }

    fun updateData(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}
