plugins {
    scala
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("io.quarkus:quarkus-container-image-docker")
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-logging-json")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-scala_2.13")
    implementation("io.quarkus:quarkus-agroal")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("com.lihaoyi:scalatags_2.13:0.12.0")
    implementation("org.playframework.anorm:anorm_2.13:2.7.0")
    implementation("io.quarkus:quarkus-micrometer")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-scala")
    implementation("org.scala-lang:scala-library:2.13.11")
    implementation("org.scala-lang.modules:scala-java8-compat_2.13:1.0.2")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
}

group = "io.github.irumiha"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<ScalaCompile> {
    scalaCompileOptions.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

tasks.register("runJwtGenerate", JavaExec::class) {
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("quarkustemplate.util.GenerateToken")
    jvmArgs("-Dsmallrye.jwt.sign.key.location=privateKey.pem")
}