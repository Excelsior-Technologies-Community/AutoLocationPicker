package com.ext.locationpicker.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ext.locationpicker.R
import java.util.Locale

class SimplePickerAdapter(
    private val originalList: List<String>,
    private val onClick: (String, Int) -> Unit
) : RecyclerView.Adapter<SimplePickerAdapter.VH>() {

    private var filteredList = originalList.toMutableList()

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_picker, parent, false)
        return VH(view)
    }

    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.title.text = filteredList[position]
        holder.itemView.setOnClickListener {
            val originalIndex = originalList.indexOf(filteredList[position])
            onClick(filteredList[position], originalIndex)
        }
    }

    // ✅ REAL-TIME SEARCH LOGIC
    fun filter(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(originalList)
        } else {
            val search = query.lowercase(Locale.getDefault())
            originalList.forEach {
                if (it.lowercase(Locale.getDefault()).contains(search)) {
                    filteredList.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }
}
