pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://maven.neshan.org/artifactory/public-maven") }
        jcenter() // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "MetroYab"
include(":app")
 