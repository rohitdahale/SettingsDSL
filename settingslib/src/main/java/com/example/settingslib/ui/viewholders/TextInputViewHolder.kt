package com.example.settingslib.ui.viewholders

import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.model.SettingTextInput
import com.example.settingslib.storage.SettingsStorage

class TextInputViewHolder private constructor(val layout: LinearLayout) :
    RecyclerView.ViewHolder(layout) {

    private val title = TextView(layout.context)
    private val input = EditText(layout.context)

    fun bind(item: SettingTextInput) {
        title.text = item.title
        input.hint = item.hint

        layout.removeAllViews()
        layout.addView(title)
        layout.addView(input)
    }

    companion object {
        fun from(parent: ViewGroup, storage: SettingsStorage): TextInputViewHolder {
            val layout = LinearLayout(parent.context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 32, 32, 32)
            }
            return TextInputViewHolder(layout)
        }
    }
}
