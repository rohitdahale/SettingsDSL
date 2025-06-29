package com.example.settingslib.ui.viewholders

import android.R
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.model.SettingList
import com.example.settingslib.storage.SettingsStorage

class ListViewHolder private constructor(val layout: LinearLayout) :
    RecyclerView.ViewHolder(layout) {

    private val title = TextView(layout.context)
    private val spinner = Spinner(layout.context)

    fun bind(item: SettingList) {
        title.text = item.title
        val adapter = ArrayAdapter(layout.context, R.layout.simple_spinner_item, item.options)
        spinner.adapter = adapter
        spinner.setSelection(item.options.indexOf(item.defaultValue))

        layout.removeAllViews()
        layout.addView(title)
        layout.addView(spinner)
    }

    companion object {
        fun from(parent: ViewGroup, storage: SettingsStorage): ListViewHolder {
            val layout = LinearLayout(parent.context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 32, 32, 32)
            }
            return ListViewHolder(layout)
        }
    }
}
