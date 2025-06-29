# 📱 ModularSettingsDSL

A modular, theme-aware, and highly customizable Android settings screen builder powered by **Kotlin DSL**.

> ✅ Designed for modern apps that need flexible, beautiful, and structured settings UI — with built-in support for search, theming, validation, and state persistence.

---

## 🚀 Features

- ✅ Kotlin DSL to define settings quickly
- 🎨 Theme-aware (Material3 support)
- 🔍 Built-in search bar to filter settings
- 🧠 Validation rules for input fields
- 🛠 Persistent storage via SharedPreferences
- 🔗 Supports dependency logic (planned)
- 🎛️ Switch, List, Slider, TextInput, Action items
- 📁 Category headers and grouping

---

## 🧰 Installation

### Step 1 – Add JitPack to your root `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

Step 2 – Add the dependency to your app's build.gradle.kts

```kotlin
dependencies {
    implementation("com.github.rohitdahale:SettingsDSL:v1.0.0")
}

📦 Published via JitPack


✨ Usage Example
val settings = settings {
    category("Appearance") {
        switch("dark_mode", "Dark Mode") {
            description = "Enable dark theme"
            icon = R.drawable.ic_dark_mode
            defaultValue = false
            onChanged = { isDarkMode ->
                // Handle theme toggle
            }
        }

        list("language", "Language", listOf("English", "Hindi", "Spanish"), "English") {
            description = "Choose your preferred language"
            icon = R.drawable.ic_language
        }

        slider("font_size", "Font Size", 12f, 24f, 16f) {
            description = "Adjust text size"
            unit = "sp"
        }
    }

    category("Account") {
        textInput("username", "Username") {
            hint = "Enter your name"
            validation = {
                when {
                    it.isBlank() -> "Cannot be empty"
                    it.length < 3 -> "At least 3 characters"
                    else -> null
                }
            }
        }

        action("logout", "Log Out") {
            description = "Sign out of your account"
            icon = R.drawable.ic_logout
            destructive = true
            onClick = {
                // Handle logout
            }
        }
    }
}

🔍 Search Support
Just add a TextInputEditText and wire it to the adapter:
searchEditText.addTextChangedListener {
    adapter.filter(it.toString())
}

🎨 Theme Support
Theme-aware components automatically adjust based on your current Material Theme. To apply changes:

switch("dark_mode", "Dark Mode") {
    onChanged = {
        recreate() // Apply theme
    }
}

🧪 Test App Demo
Use the :app module in the GitHub repo as a live testbed. It uses:

Material3

Dynamic categories

SharedPreferences storage

Full UI adapter
