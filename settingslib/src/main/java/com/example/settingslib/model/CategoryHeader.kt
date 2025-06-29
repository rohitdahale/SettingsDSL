package com.example.settingslib.model

data class CategoryHeader(
    override val title: String
) : SettingItem("", title) {
    override fun matchesSearch(query: String): Boolean =
        title.contains(query, ignoreCase = true)
}