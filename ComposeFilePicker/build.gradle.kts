import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish") version "0.30.0"
}

android {
    namespace = "github.mahdiasd.composefilepicker"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        minSdk = 21

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.coil.compose)
    implementation(libs.coil.video)
}



mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("io.github.mahdiasd", "ComposeFilePicker", "1.0.4")

    pom {
        name.set("Compose File Picker")
        description.set("An Android file picker library built with Jetpack Compose.")
        inceptionYear.set("2024")
        url.set("https://github.com/mahdiasd/ComposeFilePicker")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("mahdiasd")
                name.set("Mahdi Asadollahpur")
                url.set("https://github.com/mahdiasd/")
            }
        }
        scm {
            url.set("https://github.com/mahdiasd/ComposeFilePicker")
            connection.set("scm:git:https://github.com/mahdiasd/ComposeFilePicker.git")
            developerConnection.set("scm:git:git@github.com:mahdiasd/ComposeFilePicker.git")
        }
    }
}