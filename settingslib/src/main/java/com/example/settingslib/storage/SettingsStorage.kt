package com.example.settingslib.storage

interface SettingsStorage {
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean

    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): String

    fun putFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float = 0f): Float

    fun putInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int = 0): Int

    fun remove(key: String)
    fun clear()

    fun contains(key: String): Boolean
    fun getAll(): Map<String, *>
}


