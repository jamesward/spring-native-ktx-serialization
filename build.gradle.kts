plugins {
    id("org.springframework.boot")             version "2.6.2" apply false
    id("io.spring.dependency-management")      version "1.0.11.RELEASE" apply false
    id("org.springframework.experimental.aot") version "0.11.1" apply false
    kotlin("jvm")                              version "1.6.10" apply false
    kotlin("multiplatform")                    version "1.6.10" apply false
    kotlin("plugin.serialization")             version "1.6.10" apply false
    kotlin("plugin.spring")                    version "1.6.10" apply false
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/release")
    }
}
