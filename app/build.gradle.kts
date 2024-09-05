plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.gadre.spotify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gadre.spotify"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures{
        viewBinding=true
        dataBinding=true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.core.ktx)
    implementation(libs.media3.session)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    dependencies {
        // Retrofit for REST API calls
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")

        // AndroidX AppCompat for backward-compatible features
        implementation("androidx.appcompat:appcompat:1.6.1")

        // Glide for image loading and caching
        implementation("com.github.bumptech.glide:glide:4.15.1")
        annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

        // Google Material Design components
        implementation("com.google.android.material:material:1.2.0")

        // WorkManager for background work
        implementation("androidx.work:work-runtime:2.8.1")

        // Core AndroidX libraries
        implementation("androidx.core:core:1.9.0")

        // Media-related functionality
        implementation("androidx.media:media:1.7.0")
        implementation("androidx.media3:media3-session:1.0.0")
        implementation("androidx.media3:media3-exoplayer:1.0.0")

        //RoomDatabase
        implementation ("androidx.room:room-runtime:2.5.0")
        annotationProcessor ("androidx.room:room-compiler:2.5.0")
    }


}