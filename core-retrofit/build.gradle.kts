plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.core_retrofit"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

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
    implementation(project(":core-model"))
    // Retrofit
    implementation (libs.retrofit)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    // Chuyển đổi JSON (gson) cho Retrofit
    implementation (libs.converter.gson)
    // (Tùy chọn) Để hỗ trợ RxJava với Retrofit
    implementation (libs.adapter.rxjava3)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}