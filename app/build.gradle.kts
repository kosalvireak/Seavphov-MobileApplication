plugins {
    alias(libs.plugins.androidApplication)
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "kh.edu.rupp.seavphov"
    compileSdk = 35

    defaultConfig {
        applicationId = "kh.edu.rupp.seavphov"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    signingConfigs {
        create("release") {
            storeFile =
                file("./Seavphov.jks")
            storePassword = "123456789"
            keyAlias = "Seavphov"
            keyPassword = "123456789"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true // make our code smaller, prevent de-compile
            proguardFiles( // config file for minify
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "Seavphov"
    productFlavors {
        create("uat") {
            dimension = "Seavphov"
            applicationId = "kh.edu.rupp.seavphov.uat"
            resValue("string", "app_name", "Seavphov UAT")

//            As we have one source of api, im just gonna put it the same one
//            buildConfigField(
//                "String",
//                "apiBaseUrl",
//                "\"https://raw.githubusercontent.com/kosalvireak/Seavphov-MobileApplication/master/dummy/\""
//            )
        }

        create("prd") {
            dimension = "Seavphov"
            applicationId = "kh.edu.rupp.seavphov"
            resValue("string", "app_name", "Seavphov PRD")

//            As we have one source of api, im just gonna put it the same one
//            buildConfigField(
//                "String",
//                "apiBaseUrl",
//                "\"https://seavphov-mobileapplication.onrender.com/api/\""
//            )
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
    implementation("androidx.core:core-ktx:+")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.2.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.8.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
    implementation("com.google.firebase:firebase-storage:21.0.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}