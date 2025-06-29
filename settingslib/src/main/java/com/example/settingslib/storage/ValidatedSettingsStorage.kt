package com.example.settingslib.storage

class ValidatedSettingsStorage(
    private val delegate: SettingsStorage
) : SettingsStorage by delegate {

    private val validationRules = mutableMapOf<String, ValidationRule>()

    fun addFloatValidation(key: String, minValue: Float, maxValue: Float, defaultValue: Float) {
        validationRules[key] = FloatValidationRule(minValue, maxValue, defaultValue)
    }

    override fun putFloat(key: String, value: Float) {
        val rule = validationRules[key] as? FloatValidationRule
        val validatedValue = rule?.validate(value) ?: value
        delegate.putFloat(key, validatedValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        val storedValue = delegate.getFloat(key, defaultValue)
        val rule = validationRules[key] as? FloatValidationRule
        return rule?.validate(storedValue) ?: storedValue
    }

    private sealed class ValidationRule

    private data class FloatValidationRule(
        val minValue: Float,
        val maxValue: Float,
        val defaultValue: Float
    ) : ValidationRule() {
        fun validate(value: Float): Float = when {
            value < minValue -> minValue
            value > maxValue -> maxValue
            else -> value
        }
    }
}
