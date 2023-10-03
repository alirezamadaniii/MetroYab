
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
        //noinspection JcenterRepositoryObsolete
        jcenter()
        maven {
            url = uri("https://maven.google.com")
        }
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
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}