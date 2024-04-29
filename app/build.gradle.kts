plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.dicoding.dixam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.dixam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "GITHUB_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "GITHUB_TOKEN", "\"YOUR_TOKEN_HERE\"")
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Circular image libraries
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Retrofit libraries
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // ViewModel libraries
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Coroutine support
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") //viewModelScope
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0") //liveData
    implementation("androidx.room:room-ktx:2.6.1")

    // Room library
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.0")

}