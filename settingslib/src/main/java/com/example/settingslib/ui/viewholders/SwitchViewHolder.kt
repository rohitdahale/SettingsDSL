package com.example.settingslib.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.R
import com.example.settingslib.model.SettingSwitch
import com.example.settingslib.storage.SettingsStorage
import com.google.android.material.materialswitch.MaterialSwitch

class SwitchViewHolder private constructor(
    private val itemView: android.view.View,
    private val storage: SettingsStorage
) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.settingTitle)
    private val description: TextView = itemView.findViewById(R.id.settingDescription)
    private val icon: ImageView = itemView.findViewById(R.id.settingIcon)
    private val switch: MaterialSwitch = itemView.findViewById(R.id.settingSwitch)

    fun bind(item: SettingSwitch) {
        title.text = item.title

        description.apply {
            text = item.description
            isVisible = !item.description.isNullOrBlank()
        }

        icon.apply {
            isVisible = item.icon != null
            item.icon?.let { setImageResource(it) }
        }

        // Load saved value or use default
        val currentValue = storage.getBoolean(item.key, item.defaultValue)
        switch.isChecked = currentValue

        switch.setOnCheckedChangeListener { _, isChecked ->
            storage.putBoolean(item.key, isChecked)
            item.onChanged?.invoke(isChecked)
        }

        itemView.setOnClickListener {
            switch.toggle()
        }
    }

    companion object {
        fun from(parent: ViewGroup, storage: SettingsStorage): SwitchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_setting_switch, parent, false)
            return SwitchViewHolder(view, storage)
        }
    }
}

