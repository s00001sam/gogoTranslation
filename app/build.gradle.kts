import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mapsplatform.secrets)
    alias(libs.plugins.room)
    alias(libs.plugins.hilt)
}

// 存放 Room schema 的目錄
room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.sam.gogotranslation"
    compileSdk = 34
    signingConfigs {
        create("release") {
            val localProperties = Properties()
            localProperties.load(FileInputStream(rootProject.file("local.properties")))

            storeFile = localProperties["SIGN_STORE_FILE"]?.let { file(it) }
            storePassword = localProperties["SIGN_STORE_PASSWORD"] as String?
            keyAlias = localProperties["SIGN_ALIAS"] as String?
            keyPassword = localProperties["SIGN_KEY_PASSWORD"] as String?
        }
    }

    defaultConfig {
        applicationId = "com.sam.gogotranslation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // gemini ai
    implementation(libs.gemini)

    // room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Timber
    implementation(libs.timber)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.constraintlayout.compose)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.adapter)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.adapter)

    // Okhttp3
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Coil
    implementation(libs.coil.core)
    implementation(libs.coil.compose)

    // Flipper
    debugImplementation(libs.facebook.flipper)
    debugImplementation(libs.facebook.flipper.network)
    debugImplementation(libs.facebook.soloader)
    releaseImplementation(libs.facebook.flipper.noop)

    // Serialization
    implementation(libs.kotlin.serialization)

    // desugaring
    coreLibraryDesugaring(libs.android.desugaring)
}
