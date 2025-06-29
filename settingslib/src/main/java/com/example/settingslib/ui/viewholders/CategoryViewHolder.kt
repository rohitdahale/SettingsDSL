package com.example.settingslib.ui.viewholders

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.model.CategoryHeader

class CategoryViewHolder private constructor(private val textView: TextView) :
    RecyclerView.ViewHolder(textView) {

    fun bind(item: CategoryHeader) {
        textView.text = item.title
    }

    companion object {
        fun from(parent: ViewGroup): CategoryViewHolder {
            val textView = TextView(parent.context).apply {
                setPadding(32, 32, 32, 16)
                textSize = 18f
                setTypeface(null, Typeface.BOLD)
            }
            return CategoryViewHolder(textView)
        }
    }
}
