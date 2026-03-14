plugins {
    id("com.android.application")
}

android {
    namespace = "net.foucry.pilldroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "net.foucry.pilldroid"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas"
                )
            }
        }
    }

    signingConfigs {
        signingConfigs {
            create("releaseConfig") {
                if (System.getenv("KEYSTORE_FILE") != null) {
                    storeFile = file(System.getenv("KEYSTORE_FILE"))
                    storePassword = System.getenv("KEYSTORE_PASSWORD")
                    keyAlias = System.getenv("KEYSTORE_ALIAS")
                    keyPassword = System.getenv("KEYSTORE_PASSPHRASE")
                } else {
                    storeFile = file("release.keystore")
                    storePassword = System.getenv("KEYSTORE_PASSWORD")
                    keyAlias = System.getenv("KEYSTORE_ALIAS")
                    keyPassword = System.getenv("KEYSTORE_PASSPHRASE")
                }
            }
            }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("releaseConfig")
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.room:room-common:2.8.4")
    annotationProcessor("androidx.room:room-compiler:2.8.4")
    implementation("androidx.room:room-testing:2.8.4")
    implementation("androidx.room:room-rxjava3:2.8.4")
    implementation("androidx.room:room-runtime:2.8.4")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation("junit:junit:4.13.2")



    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("androidx.core:core:1.16.0")

    //coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs:2.0.0'
}
