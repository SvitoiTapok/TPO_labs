plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-property:5.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.3")
    implementation("org.seleniumhq.selenium:selenium-java:4.41.0")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("browser", System.getProperty("browser") ?: "all")
}
kotlin {
    jvmToolchain(17)
}