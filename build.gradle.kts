
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val agp_version by extra("8.6.0")
    val agp_version1 by extra("8.5.0")
    repositories {
        maven {
        url = uri("https://maven.google.com")
    }
        mavenCentral()
        //noinspection JcenterRepositoryObsolete

        google()

        jcenter()

    }


    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")

        // Hilt
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.43.2")

        // Safe Args "2.5.3"
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")

        //firebase
//        classpath 'com.google.gms:google-services:4.3.13'
    }



}



plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    kotlin("kapt") version "2.0.20"
}