import com.android.build.gradle.internal.utils.isKspPluginApplied

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapp"
        minSdk = 24
        targetSdk = 34
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

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://reqres.in/api/\"")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
        buildConfig=true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // VIEW MODEL, LIFECYCLE & LIVE DATA
    implementation(libs.androidx.lifecycle.runtime.ktx)
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-common-java8:2.7.0")

    //GSON
    //noinspection UseTomlInstead
    implementation("com.google.code.gson:gson:2.10.1")


    //API
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.ksp)
    testImplementation(libs.androidx.room.testing)

    //GLIDE
    implementation(libs.glide)

   


}