package com.example.settingslib.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.settingslib.model.SettingItem

class SettingsDiffCallback(
    private val oldList: List<SettingItem>,
    private val newList: List<SettingItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.key == newItem.key && oldItem::class == newItem::class
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
