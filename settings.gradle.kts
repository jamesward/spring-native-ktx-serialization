rootProject.name = "spring-native-ktx-serialization"

include("common", "server")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.spring.io/release")
    }
}
