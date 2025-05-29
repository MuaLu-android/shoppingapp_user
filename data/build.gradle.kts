plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    //rxjava3
    implementation (libs.rxjava3.rxjava)
    implementation (libs.rxjava3.rxandroid)
    // Retrofit
    implementation (libs.retrofit2)
    //Gson
    implementation (libs.converter.gson)
    // RxJava với Retrofit
    implementation (libs.adapter.rxjava3)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}