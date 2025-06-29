package com.example.modularsettings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.settingslib.dsl.settings
import com.example.settingslib.storage.SettingsStorage
import com.example.settingslib.storage.SharedPrefStorage
import com.example.settingslib.storage.ValidatedSettingsStorage
import com.example.settingslib.ui.SettingsAdapter
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SettingsAdapter
    private lateinit var searchEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSettings()
        setupSearch()
    }

    private fun initializeViews() {
        recyclerView = findViewById(R.id.settingsRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    private fun setupSettings() {
        val storage = SharedPrefStorage(this)

        val settings = settings {
            category("Appearance") {
                switch("dark_mode", "Dark Mode") {
                    description = "Enable dark theme"
                    defaultValue = false
                    onChanged = { enabled ->
                        // Apply theme change
                        recreateForThemeChange()
                    }
                }

                list("language", "Language", listOf("English", "Hindi", "Spanish"), "English") {
                    description = "Choose your preferred language"
                }

                slider("font_size", "Font Size", 12f, 24f, 16f) {
                    description = "Adjust text size"
                    unit = "sp"
                }
            }

            category("Account") {
                textInput("username", "Username") {
                    hint = "Enter your name"
                    validation = { text ->
                        when {
                            text.isBlank() -> "Username cannot be empty"
                            text.length < 3 -> "Username must be at least 3 characters"
                            else -> null
                        }
                    }
                }

                textInput("email", "Email") {
                    hint = "Enter your email"
                    inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    validation = { text ->
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) null
                        else "Please enter a valid email"
                    }
                }

                action("change_password", "Change Password") {
                    description = "Update your account password"
                    onClick = {
                        // Navigate to change password screen
                        Toast.makeText(this@MainActivity, "Opening password change", Toast.LENGTH_SHORT).show()
                    }
                }

                action("logout", "Log Out") {
                    description = "Sign out of your account"
                    destructive = true
                    onClick = {
                        showLogoutConfirmation()
                    }
                }
            }

            category("Privacy & Security") {
                switch("biometric_auth", "Biometric Authentication") {
                    description = "Use fingerprint or face unlock"
                    defaultValue = true
                }

                switch("analytics", "Usage Analytics") {
                    description = "Help improve the app by sharing usage data"
                    defaultValue = false
                }

                action("clear_data", "Clear App Data") {
                    description = "Remove all stored data"
                    destructive = true
                    onClick = {
                        showClearDataConfirmation()
                    }
                }
            }

            category("Notifications") {
                switch("push_notifications", "Push Notifications") {
                    description = "Receive push notifications"
                    defaultValue = true
                }

                list("notification_sound", "Notification Sound",
                    listOf("Default", "Bell", "Chime", "None"), "Default") {
                    description = "Choose notification sound"
                }

                slider("notification_volume", "Notification Volume", 0f, 100f, 50f) {
                    description = "Adjust notification volume"
                    unit = "%"
                }
            }

            category("Advanced") {
                switch("developer_mode", "Developer Mode") {
                    description = "Enable advanced debugging features"
                    defaultValue = false
                }

                action("export_settings", "Export Settings") {
                    description = "Save settings to file"
                    onClick = {
                        exportSettings()
                    }
                }

                action("import_settings", "Import Settings") {
                    description = "Load settings from file"
                    onClick = {
                        importSettings()
                    }
                }
            }
        }

        adapter = SettingsAdapter(this, settings, storage)
        recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener { text ->
            adapter.filter(text.toString())
        }
    }

    fun setupValidatedStorage(): SettingsStorage {
        val baseStorage = SharedPrefStorage(this)
        val validatedStorage = ValidatedSettingsStorage(baseStorage)

        // Add validation rules for sliders
        validatedStorage.addFloatValidation("font_size", 12f, 24f, 16f)
        validatedStorage.addFloatValidation("notification_volume", 0f, 100f, 50f)

        return validatedStorage
    }


    private fun recreateForThemeChange() {
        // Implement theme change logic
        recreate()
    }

    private fun showLogoutConfirmation() {
        // Show confirmation dialog
        Toast.makeText(this, "Logout confirmation", Toast.LENGTH_SHORT).show()
    }

    private fun showClearDataConfirmation() {
        // Show confirmation dialog
        Toast.makeText(this, "Clear data confirmation", Toast.LENGTH_SHORT).show()
    }

    private fun exportSettings() {
        // Implement settings export
        Toast.makeText(this, "Settings exported", Toast.LENGTH_SHORT).show()
    }

    private fun importSettings() {
        // Implement settings import
        Toast.makeText(this, "Settings imported", Toast.LENGTH_SHORT).show()
    }
}
