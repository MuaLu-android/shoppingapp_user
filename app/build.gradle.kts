import com.android.utils.TraceUtils.simpleId

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.user.appbanhang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.user.appbanhang"
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    packagingOptions{
        exclude("META-INF/DEPENDENCIES")
    }
}

dependencies {
    implementation(project(":core-service"))
    implementation(project(":core-activity"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)
    implementation(fileTree(mapOf(
        "dir" to "D:\\TT_DA\\zalopay",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.glide)
    //RxJava
    implementation (libs.rxjava)
    implementation (libs.rxandroid)

    // Retrofit
    implementation (libs.retrofit)

    // Chuyển đổi JSON (gson) cho Retrofit
    implementation (libs.converter.gson)

    // Chuyển đổi XML (nếu cần) cho Retrofit
    implementation (libs.converter.simplexml)

    // (Tùy chọn) Để hỗ trợ RxJava với Retrofit
    implementation (libs.adapter.rxjava3)
    //badge
    implementation (libs.notification.badge)
    //evenbus
    implementation(libs.eventbus)
    //paper
    implementation (libs.paperdb)
    //Gson
    implementation (libs.gson)
    //lottie
    implementation (libs.lottie)
    //neumophism
    implementation (libs.neumorphism)
    //imagepicker
    implementation (libs.imagepicker)
    //accestoken
    implementation(libs.google.auth.library.oauth2.http)
    //momo
    implementation(libs.mobile.sdk)
    //zalo pay
    implementation(libs.okhttp)
    implementation(libs.commons.codec)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}