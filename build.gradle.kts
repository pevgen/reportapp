import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ml.pevgen"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

var modelMapperVersion = project.extra["org.modelmapper"]
var webjarsLocatorVersion = project.extra["org.webjars.webjars-locator"]
var webjarsBootstrapVersion = project.extra["org.webjars.bootstrap"]
var webjarsJQueryVersion = project.extra["org.webjars.jquery"]

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.liquibase:liquibase-core") {
        exclude(group  = "commons-logging", module = "commons-logging")
    }
    implementation("com.opencsv:opencsv"){
        exclude(group  = "commons-logging", module = "commons-logging")
    }
    implementation("org.modelmapper:modelmapper:$modelMapperVersion")
    implementation("org.webjars:webjars-locator:$webjarsLocatorVersion")
    implementation("org.webjars:bootstrap:$webjarsBootstrapVersion")
    implementation("org.webjars:jquery:$webjarsJQueryVersion")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
//    imageName = "docker.example.com/library/${project.name}"
    imageName = "example/${project.name}"
//    publish = true
//    docker {
//        publishRegistry {
//            username = "user"
//            password = "secret"
//            url = "https://docker.example.com/v1/"
//            email = "user@example.com"
//        }
//    }
}
