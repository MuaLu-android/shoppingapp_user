plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.core.model"
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
    implementation(project(":core-payzalo"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(fileTree(mapOf(
        "dir" to "D:\\TT_DA\\zalopay",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    //zalo pay
    implementation(libs.okhttp)
    implementation(libs.commons.codec)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}