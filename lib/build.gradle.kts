plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.gringauz.gitlabpublishsample.lib"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.gringauz.gitlabpublishsample"
                artifactId = "lib"
                version = "1.0.1"
            }
        }
        repositories {
            maven {
                name = "GitLab"
                val gitlabHost = project.findProperty("gitlabHost")!!
                val projectId = project.findProperty("gitlabProjectId")!!
                url = uri("$gitlabHost/api/v4/projects/$projectId/packages/maven")
                credentials {
                    username = "Private-Token"
                    password = project.findProperty("gitlabToken")!!.toString()
                }
            }
        }
    }
}
