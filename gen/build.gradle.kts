repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    kotlin("jvm") version "2.1.0"
}

dependencies {
    implementation(libs.jooq)
}
