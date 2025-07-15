plugins {

    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.quizletfake"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quizletfake"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation ("com.google.android.gms:play-services-auth:20.6.0")
    implementation ("androidx.room:room-runtime:2.5.1")
    implementation ("androidx.room:room-common:2.5.1")
    annotationProcessor ("androidx.room:room-compiler:2.5.1")
    androidTestImplementation ("androidx.room:room-testing:2.5.1")

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}