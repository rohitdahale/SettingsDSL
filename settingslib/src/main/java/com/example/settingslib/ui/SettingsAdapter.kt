package com.example.settingslib.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.model.*
import com.example.settingslib.storage.SettingsStorage
import com.example.settingslib.ui.viewholders.*

class SettingsAdapter(
    private val context: Context,
    private val categories: List<SettingCategory>,
    private val storage: SettingsStorage
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var allItems: List<SettingItem> = flattenCategories(categories)
    private var filteredItems: List<SettingItem> = allItems
    private var lastPosition = -1

    private fun flattenCategories(categories: List<SettingCategory>): List<SettingItem> {
        return categories.flatMap { category ->
            listOf(CategoryHeader(category.name)) + category.items
        }
    }

    fun filter(query: String) {
        val filtered = if (query.isBlank()) {
            allItems
        } else {
            val matchingItems = mutableListOf<SettingItem>()

            categories.forEach { category ->
                val matchingCategoryItems = category.items.filter { it.matchesSearch(query) }
                if (matchingCategoryItems.isNotEmpty()) {
                    matchingItems.add(CategoryHeader(category.name))
                    matchingItems.addAll(matchingCategoryItems)
                }
            }

            matchingItems
        }

        val diffCallback = SettingsDiffCallback(filteredItems, filtered)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        filteredItems = filtered
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (filteredItems[position]) {
            is CategoryHeader -> VIEW_TYPE_CATEGORY
            is SettingSwitch -> VIEW_TYPE_SWITCH
            is SettingList -> VIEW_TYPE_LIST
            is SettingTextInput -> VIEW_TYPE_TEXT_INPUT
            is SettingSlider -> VIEW_TYPE_SLIDER
            is SettingAction -> VIEW_TYPE_ACTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> CategoryViewHolder.from(parent)
            VIEW_TYPE_SWITCH -> SwitchViewHolder.from(parent, storage)
            VIEW_TYPE_LIST -> ListViewHolder.from(parent, storage)
            VIEW_TYPE_TEXT_INPUT -> TextInputViewHolder.from(parent, storage)
            VIEW_TYPE_SLIDER -> SliderViewHolder.from(parent, storage)
            VIEW_TYPE_ACTION -> ActionViewHolder.from(parent)
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = filteredItems[position]

        when (holder) {
            is CategoryViewHolder -> holder.bind(item as CategoryHeader)
            is SwitchViewHolder -> holder.bind(item as SettingSwitch)
            is ListViewHolder -> holder.bind(item as SettingList)
            is TextInputViewHolder -> holder.bind(item as SettingTextInput)
            is SliderViewHolder -> holder.bind(item as SettingSlider)
            is ActionViewHolder -> holder.bind(item as SettingAction)
        }

        // Add animation
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            holder.itemView.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount() = filteredItems.size

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    companion object {
        const val VIEW_TYPE_CATEGORY = 0
        const val VIEW_TYPE_SWITCH = 1
        const val VIEW_TYPE_LIST = 2
        const val VIEW_TYPE_TEXT_INPUT = 3
        const val VIEW_TYPE_SLIDER = 4
        const val VIEW_TYPE_ACTION = 5
    }
}
