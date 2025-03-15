plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.core.adapter"
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
    implementation(project(":core-untils"))
    implementation(project(":core-model"))
    implementation(project(":core-Interface"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation (libs.glide)
    //evenbus
    implementation(libs.eventbus)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}