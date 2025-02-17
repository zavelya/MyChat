import com.android.tools.r8.internal.de

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.mychat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mychat"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

   compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>{
       kotlinOptions {
           jvmTarget = "11"
           freeCompilerArgs = listOf("-Xjvm-default=all")
       }
   }
    buildFeatures {
        compose = true
        viewBinding=true
        dataBinding=true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.databinding.runtime)
    // implementation(libs.androidx.library)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.appcompat:appcompat:1.3.0")
    implementation ("com.google.firebase:firebase-auth:23.2.0")
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-firestore")





}}}}
