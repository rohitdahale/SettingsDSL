package com.example.settingslib.model

import androidx.annotation.DrawableRes

sealed class SettingItem(
    open val key: String,
    open val title: String
) {
    abstract fun matchesSearch(query: String): Boolean
}

data class SettingSwitch(
    override val key: String,
    override val title: String,
    val defaultValue: Boolean = false,
    val description: String? = null,
    @DrawableRes val icon: Int? = null,
    val onChanged: ((Boolean) -> Unit)? = null
) : SettingItem(key, title) {
    override fun matchesSearch(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                description?.contains(query, ignoreCase = true) == true
}

data class SettingList(
    override val key: String,
    override val title: String,
    val options: List<String>,
    val defaultValue: String,
    val description: String? = null,
    @DrawableRes val icon: Int? = null,
    val onChanged: ((String) -> Unit)? = null
) : SettingItem(key, title) {
    override fun matchesSearch(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                description?.contains(query, ignoreCase = true) == true ||
                options.any { it.contains(query, ignoreCase = true) }
}

data class SettingTextInput(
    override val key: String,
    override val title: String,
    val hint: String = "",
    val description: String? = null,
    @DrawableRes val icon: Int? = null,
    val inputType: Int = android.text.InputType.TYPE_CLASS_TEXT,
    val validation: ((String) -> String?)? = null,
    val onChanged: ((String) -> Unit)? = null
) : SettingItem(key, title) {
    override fun matchesSearch(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                description?.contains(query, ignoreCase = true) == true
}

data class SettingSlider(
    override val key: String,
    override val title: String,
    val minValue: Float,
    val maxValue: Float,
    val defaultValue: Float,
    val description: String? = null,
    @DrawableRes val icon: Int? = null,
    val unit: String? = null,
    val stepSize: Float = 1f,
    val onChanged: ((Float) -> Unit)? = null
) : SettingItem(key, title) {
    override fun matchesSearch(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                description?.contains(query, ignoreCase = true) == true
}

data class SettingAction(
    override val key: String,
    override val title: String,
    val description: String? = null,
    @DrawableRes val icon: Int? = null,
    val destructive: Boolean = false,
    val onClick: () -> Unit
) : SettingItem(key, title) {
    override fun matchesSearch(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                description?.contains(query, ignoreCase = true) == true
}

data class SettingCategory(
    val name: String,
    val items: List<SettingItem>
)
