import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.springframework.experimental.aot")
}

dependencies {
    implementation(project(":common"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.0")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

application {
    mainClass.set("kotlinbars.server.MainKt")
}

springAot {
    //mode.set(org.springframework.aot.gradle.dsl.AotMode.NATIVE_AGENT)
    removeXmlSupport.set(true)
    removeSpelSupport.set(true)
    removeYamlSupport.set(true)
    removeJmxSupport.set(true)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    builder = "paketobuildpacks/builder:tiny"
    environment = mapOf("BP_NATIVE_IMAGE" to "1")
}
