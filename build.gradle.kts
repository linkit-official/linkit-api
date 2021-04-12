plugins {
    base
    id("org.jetbrains.kotlin.jvm") version "1.4.32"
    id("org.jetbrains.kotlin.kapt") version "1.4.32"
    id("org.springframework.boot") version "2.4.4"
    id("org.jetbrains.kotlin.plugin.spring") version "1.4.32"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.32"
}

group = "com.linkit"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
}

apply(plugin = "kotlin")
apply(plugin = "kotlin-kapt")
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.servlet:javax.servlet-api:3.0.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.13")
}

val javaVersion = JavaVersion.VERSION_1_8.toString()

tasks {
    compileJava {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
        dependsOn(processResources)
    }

    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
    }
}