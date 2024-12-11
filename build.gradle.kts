repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    kotlin("jvm") version "2.1.0"
    application
}

dependencies {
    implementation(libs.clikt)
    implementation(libs.jooq)
    implementation(libs.jooq.codegen)
    implementation(libs.logback.classic)
    implementation(libs.postgresql)
}
application {
    mainClass.set("JooqCustomArrayTypeMveKt")
}
