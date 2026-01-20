plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)

    kotlin("plugin.serialization") version "2.2.20"
    // Apply the application plugin to add support for building a CLI application.
    application

    // Apply the Ktor plugin for Ktor support.
    // id("io.ktor.plugin") version "3.3.2"
}

repositories {
    mavenCentral()
}

dependencies {
    // Use the Kotlin Test integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the JUnit 5 integration.
    testImplementation(libs.junit.jupiter.engine)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)

    // Database dependencies.
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.datetime)
    implementation(libs.exposed.javadatetime)
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    implementation("org.slf4j:slf4j-api:1.7.32")

    // KTOR dependencies.
    implementation("io.ktor:ktor-server-netty:3.3.2")
    implementation("io.ktor:ktor-server-html-builder:3.3.2")
    implementation("io.ktor:ktor-server-core:3.3.2")
    implementation("io.ktor:ktor-server-host-common:3.3.2")
    implementation("io.ktor:ktor-server-thymeleaf:3.3.2")
    implementation("io.ktor:ktor-server-status-pages:3.3.2")
    implementation("io.ktor:ktor-server-htmx:3.2.0")
    implementation("io.ktor:ktor-htmx:3.2.0")
    implementation("io.ktor:ktor-htmx-html:3.2.0")

    // Supabase dependencies
    implementation(platform("io.github.jan-tennert.supabase:bom:3.2.3"))
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")

    implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass.set("org.example.AppKt")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
