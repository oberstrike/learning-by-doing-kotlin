plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.allopen") version "1.3.72"
    id("io.quarkus")
}

repositories {
    mavenLocal()
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-keycloak-authorization")
    implementation("io.quarkus:quarkus-mongodb-panache")
    implementation("io.quarkus:quarkus-resteasy-jsonb")
    implementation("io.quarkus:quarkus-keycloak-admin-client")
    implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-jackson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "de.markus.learning"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

quarkus {
    setOutputDirectory("$projectDir/build/classes/kotlin/main")
}

tasks.withType<io.quarkus.gradle.tasks.QuarkusDev> {
    setSourceDir("$projectDir/src/main/kotlin")
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
