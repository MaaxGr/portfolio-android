plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
    id("com.github.ben-manes.versions") version "0.45.0"
}

android {
    namespace = "de.grossmax.portfolioandroid"
    compileSdk = 33

    defaultConfig {
        applicationId = "de.grossmax.portfolioandroid"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            //minifyEnabled = false
            //proguardFiles = getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // androidx core
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.material:material:1.3.1")

    // testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    val composeRuntimeVersion = "1.3.3"
    implementation("androidx.compose.runtime:runtime-livedata:$composeRuntimeVersion")

    val composeUIVersion: String by rootProject.extra
    implementation("androidx.compose.ui:ui:$composeUIVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUIVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUIVersion")

    // icons
    implementation("androidx.compose.material:material-icons-extended:1.3.1")

    // jetpack navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // koin
    implementation("io.insert-koin:koin-core:3.3.3")
    implementation("io.insert-koin:koin-android:3.3.3")
    implementation("io.insert-koin:koin-androidx-compose:3.4.2")

    // implement ktor client with serialization
    implementation("io.ktor:ktor-client-core:2.2.3")
    implementation("io.ktor:ktor-client-android:2.2.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")

    // kotlinx extensions
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // image loading
    implementation("io.coil-kt:coil:2.2.2")
    implementation("io.coil-kt:coil-compose:2.2.2")

}

configureDependencyUpdateTask()

fun Project.configureDependencyUpdateTask() {
    fun isNonStable(version: String): Boolean {
        if (version.toLowerCase().contains("alpha") || version.toLowerCase().contains("rc")) {
            return true
        }

        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates").configure {
        revision = "release"

        rejectVersionIf {
            isNonStable(this.candidate.version)
        }
    }
}
