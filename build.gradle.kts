group = "me.jakejmattson"
version = "0.21.3"

plugins {
    kotlin("jvm") version "1.4.10"
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.22.0-SNAPSHOT")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}