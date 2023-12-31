

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    //id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")



}

android {
    namespace = "com.example.localguide"
    compileSdk = 34
    buildFeatures {
        viewBinding = true
        compose = true
        dataBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }



    defaultConfig {
        applicationId = "com.example.localguide"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview-android:1.5.4")
    implementation("com.google.firebase:firebase-database:20.3.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.github.ajalt:timberkt:1.5.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-auth-ktx")




    val navigationVersion = "2.7.5"


    implementation ("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation ("androidx.preference:preference:1.2.1")

    val activity_version = "1.8.1"

    implementation("androidx.activity:activity:$activity_version")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.ui:ui-framework:0.1.0-dev03")

    val material3_version = "1.1.2"
    implementation("androidx.compose.material3:material3:$material3_version" )

    //implementation("androidx.compose.ui:ui-tooling:1.8.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    //implementation("androidx.compose.ui:ui-tooling-preview:1.8.1")

    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation ("com.google.maps.android:maps-compose:2.4.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:20.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-storage")
}