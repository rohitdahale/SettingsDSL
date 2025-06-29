package com.example.settingslib.dsl

import com.example.settingslib.model.*

@DslMarker
annotation class SettingsDsl

@SettingsDsl
class SettingsBuilder {
    private val categories = mutableListOf<SettingCategory>()

    fun category(name: String, block: CategoryBuilder.() -> Unit) {
        val builder = CategoryBuilder(name)
        builder.block()
        categories.add(builder.build())
    }

    fun build(): List<SettingCategory> = categories.toList()
}

@SettingsDsl
class CategoryBuilder(private val name: String) {
    private val items = mutableListOf<SettingItem>()

    fun switch(key: String, title: String, block: (SwitchBuilder.() -> Unit)? = null) {
        val builder = SwitchBuilder(key, title)
        block?.invoke(builder)
        items.add(builder.build())
    }

    fun list(key: String, title: String, options: List<String>, defaultValue: String,
             block: (ListBuilder.() -> Unit)? = null) {
        val builder = ListBuilder(key, title, options, defaultValue)
        block?.invoke(builder)
        items.add(builder.build())
    }

    fun textInput(key: String, title: String, block: (TextInputBuilder.() -> Unit)? = null) {
        val builder = TextInputBuilder(key, title)
        block?.invoke(builder)
        items.add(builder.build())
    }

    fun slider(key: String, title: String, minValue: Float, maxValue: Float, defaultValue: Float,
               block: (SliderBuilder.() -> Unit)? = null) {
        val builder = SliderBuilder(key, title, minValue, maxValue, defaultValue)
        block?.invoke(builder)
        items.add(builder.build())
    }

    fun action(key: String, title: String, block: ActionBuilder.() -> Unit) {
        val builder = ActionBuilder(key, title)
        builder.block()
        items.add(builder.build())
    }

    fun build(): SettingCategory = SettingCategory(name, items.toList())
}

class SwitchBuilder(private val key: String, private val title: String) {
    var defaultValue: Boolean = false
    var description: String? = null
    var icon: Int? = null
    var onChanged: ((Boolean) -> Unit)? = null

    fun build() = SettingSwitch(key, title, defaultValue, description, icon, onChanged)
}

class ListBuilder(
    private val key: String,
    private val title: String,
    private val options: List<String>,
    private val defaultValue: String
) {
    var description: String? = null
    var icon: Int? = null
    var onChanged: ((String) -> Unit)? = null

    fun build() = SettingList(key, title, options, defaultValue, description, icon, onChanged)
}

class TextInputBuilder(private val key: String, private val title: String) {
    var hint: String = ""
    var description: String? = null
    var icon: Int? = null
    var inputType: Int = android.text.InputType.TYPE_CLASS_TEXT
    var validation: ((String) -> String?)? = null
    var onChanged: ((String) -> Unit)? = null

    fun build() = SettingTextInput(key, title, hint, description, icon, inputType, validation, onChanged)
}

class SliderBuilder(
    private val key: String,
    private val title: String,
    private val minValue: Float,
    private val maxValue: Float,
    private val defaultValue: Float
) {
    var description: String? = null
    var icon: Int? = null
    var unit: String? = null
    var stepSize: Float = 1f
    var onChanged: ((Float) -> Unit)? = null

    fun build() = SettingSlider(key, title, minValue, maxValue, defaultValue, description, icon, unit, stepSize, onChanged)
}

class ActionBuilder(private val key: String, private val title: String) {
    var description: String? = null
    var icon: Int? = null
    var destructive: Boolean = false
    lateinit var onClick: () -> Unit

    fun build() = SettingAction(key, title, description, icon, destructive, onClick)
}

fun settings(block: SettingsBuilder.() -> Unit): List<SettingCategory> {
    val builder = SettingsBuilder()
    builder.block()
    return builder.build()
}
