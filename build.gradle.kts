
// buildscript ext with gradle kotlin dsl
buildscript {
    extra.apply {
        set("composeUIVersion", "1.3.3")
    }
}

plugins {
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    kotlin("plugin.serialization") version "1.8.10" apply false
}

