plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.core.activity"
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
    implementation(project(":core-adapter"))
    implementation(project(":core-model"))
    implementation(project(":core-untils"))
    implementation(project(":core-retrofit"))
    implementation(fileTree(mapOf(
        "dir" to "D:\\TT_DA\\zalopay",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    implementation(libs.appcompat)
    //paper
    implementation(libs.firebase.auth)
    //RxJava
    implementation (libs.rxjava)
    implementation (libs.rxandroid)
    // (Tùy chọn) Để hỗ trợ RxJava với Retrofit
    //lottie
    implementation (libs.glide)
    //badge
    //evenbus
    implementation(libs.eventbus)
    implementation (libs.notification.badge)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)
    //Gson
    implementation (libs.gson)
    implementation (libs.lottie)
    implementation (libs.adapter.rxjava3)
    implementation (libs.paperdb)
    implementation(libs.material)
    implementation(libs.activity)
    //momo
    implementation(libs.mobile.sdk)
    //zalo pay
    implementation(libs.okhttp)
    implementation(libs.commons.codec)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}