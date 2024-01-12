plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "me.usuario"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("mysql:mysql-connector-java:8.0.23")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}