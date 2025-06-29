// SliderViewHolder.kt - Fixed version
package com.example.settingslib.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.R
import com.example.settingslib.model.SettingSlider
import com.example.settingslib.storage.SettingsStorage
import com.google.android.material.slider.Slider
import kotlin.math.max
import kotlin.math.min

class SliderViewHolder private constructor(
    private val itemView: android.view.View,
    private val storage: SettingsStorage
) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.settingTitle)
    private val description: TextView = itemView.findViewById(R.id.settingDescription)
    private val icon: ImageView = itemView.findViewById(R.id.settingIcon)
    private val slider: Slider = itemView.findViewById(R.id.settingSlider)
    private val valueText: TextView = itemView.findViewById(R.id.settingValue)

    fun bind(item: SettingSlider) {
        title.text = item.title

        description.apply {
            text = item.description
            isVisible = !item.description.isNullOrBlank()
        }

        icon.apply {
            isVisible = item.icon != null
            item.icon?.let { setImageResource(it) }
        }

        // Configure slider range FIRST
        slider.valueFrom = item.minValue
        slider.valueTo = item.maxValue
        slider.stepSize = item.stepSize

        // Load saved value and validate it's within range
        val savedValue = storage.getFloat(item.key, item.defaultValue)
        val validatedValue = when {
            savedValue < item.minValue -> {
                // Value is below minimum, use minimum and update storage
                storage.putFloat(item.key, item.minValue)
                item.minValue
            }
            savedValue > item.maxValue -> {
                // Value is above maximum, use maximum and update storage
                storage.putFloat(item.key, item.maxValue)
                item.maxValue
            }
            else -> savedValue
        }

        // Set the validated value
        slider.value = validatedValue
        updateValueText(validatedValue, item.unit)

        // Set up listener for changes
        slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                storage.putFloat(item.key, value)
                updateValueText(value, item.unit)
                item.onChanged?.invoke(value)
            }
        }
    }

    private fun updateValueText(value: Float, unit: String?) {
        val formattedValue = if (value == value.toInt().toFloat()) {
            value.toInt().toString()
        } else {
            String.format("%.1f", value)
        }

        valueText.text = if (unit != null) "$formattedValue $unit" else formattedValue
    }

    companion object {
        fun from(parent: ViewGroup, storage: SettingsStorage): SliderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_setting_slider, parent, false)
            return SliderViewHolder(view, storage)
        }
    }
}