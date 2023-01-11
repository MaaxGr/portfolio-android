
// buildscript ext with gradle kotlin dsl
buildscript {
    extra.apply {
        set("composeUIVersion", "1.1.1")
    }
}

plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

