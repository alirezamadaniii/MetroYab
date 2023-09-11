pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io")  }
        maven {
            url = uri("https://maven.google.com")
        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://maven.neshan.org/artifactory/public-maven") }
        jcenter() // Warning: this repository is going to shut down soon
        maven { url = uri("https://jitpack.io")  }
        maven {
            url = uri("https://maven.google.com")
        }
    }
}

rootProject.name = "MetroYab"
include(":app")
 