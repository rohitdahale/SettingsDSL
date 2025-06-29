package com.example.settingslib.ui.viewholders

import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.model.SettingAction

class ActionViewHolder private constructor(val button: Button) :
    RecyclerView.ViewHolder(button) {

    fun bind(item: SettingAction) {
        button.text = item.title
        button.setOnClickListener { item.onClick() } // ← Fixed: onClick instead of action
    }

    companion object {
        fun from(parent: ViewGroup): ActionViewHolder {
            val button = Button(parent.context).apply {
                setPadding(32, 32, 32, 32)
            }
            return ActionViewHolder(button)
        }
    }
}
