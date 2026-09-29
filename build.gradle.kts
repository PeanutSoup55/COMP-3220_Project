import java.util.Locale

plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val osName = System.getProperty("os.name").lowercase(Locale.ROOT)
val osArch = System.getProperty("os.arch").lowercase(Locale.ROOT)

val platformClassifier = when {
    osName.contains("win") -> "win"
    osName.contains("mac") -> {
        if (osArch.contains("aarch64") || osArch.contains("arm64")) "mac-aarch64" else "mac"
    }
    osName.contains("nix") || osName.contains("nux") -> "linux"
    else -> throw GradleException("Unsupported operating system: $osName")
}

dependencies {
    val javafxVersion = "21"

    implementation("org.openjfx:javafx-base:$javafxVersion")
    implementation("org.openjfx:javafx-base:$javafxVersion:$platformClassifier")

    implementation("org.openjfx:javafx-controls:$javafxVersion")
    implementation("org.openjfx:javafx-controls:$javafxVersion:$platformClassifier")

    implementation("org.openjfx:javafx-fxml:$javafxVersion")
    implementation("org.openjfx:javafx-fxml:$javafxVersion:$platformClassifier")

    implementation("org.openjfx:javafx-graphics:$javafxVersion")
    implementation("org.openjfx:javafx-graphics:$javafxVersion:$platformClassifier")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("org.example.Launcher")
}

tasks.test {
    useJUnitPlatform()
}
