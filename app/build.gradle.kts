plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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
        kotlinCompilerExtensionVersion = "2.0.0"
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
    implementation(project(":core"))
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
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //RxJava
    implementation (libs.rxjava3.rxjava)
    implementation (libs.rxjava3.rxandroid)

    // Retrofit
    implementation (libs.retrofit2)

    // Chuyển đổi JSON (gson) cho Retrofit
    implementation (libs.converter.gson)

    // Chuyển đổi XML (nếu cần) cho Retrofit
    implementation ("com.squareup.retrofit2:converter-simplexml:2.9.0")

    // (Tùy chọn) Để hỗ trợ RxJava với Retrofit
    implementation (libs.adapter.rxjava3)
    //badge
    implementation ("com.nex3z:notification-badge:1.0.4")
    //evenbus
    implementation("org.greenrobot:eventbus:3.3.1")
    //paper
    implementation ("io.github.pilgr:paperdb:2.7.2")
    //Gson
    implementation ("com.google.code.gson:gson:2.11.0")
    //lottie
    implementation ("com.airbnb.android:lottie:6.5.2")
    //neumophism
    implementation ("com.github.fornewid:neumorphism:0.3.2")
    //imagepicker
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    //accestoken
    implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
    //momo
    implementation("com.github.momo-wallet:mobile-sdk:1.0.7")
    //zalo pay
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}