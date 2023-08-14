plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.exchangerateweather"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.exchangerateweather"
        minSdk = 26
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        viewBinding = true
    }
}

val lifecycleVersion = "2.6.1"
val coroutinesVersion = "1.6.4"
val androidxCoreVersion = "1.7.0"
val appcompatVersion = "1.4.1"
val materialVersion = "1.5.0"
val constraintVersion = "2.1.3"
val activityVersion = "1.7.2"
val navVersion = "2.6.0"

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    //LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")


    // Kotlin-Activity
    implementation("androidx.activity:activity-ktx:$activityVersion")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //For xml retrofit
    implementation("com.squareup.retrofit2:converter-simplexml:2.9.0"){
        exclude(module = "stax")
        exclude(module = "stax-api")
        exclude(module = "xpp3")
    }

    // Kotlin-navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    //Swipe refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // required to avoid crash on Android 12 API 31
    implementation("androidx.work:work-runtime-ktx:2.7.0")

    //To consume  soap service
    implementation("com.google.code.ksoap2-android:ksoap2-android:3.6.4")
}

kapt {
    correctErrorTypes = true
}