import java.net.URI

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
        maven {
            this.url = URI("https://jitpack.io")
        }
    }
}

rootProject.name = "EasyNote"
include(":app")
include(":AlbumDialog")
 