plugins {
    id("com.android.application")
}

android {
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

        defaultConfig {
            applicationId = "net.foucry.pilldroid"
            compileSdk = 35
            minSdk = 26
            targetSdk = 35
            versionCode = 1
            versionName = "1.0"
            javaCompileOptions {
                annotationProcessorOptions {
                    arguments += mapOf(
                        "room.schemaLocation" to "$projectDir/schemas"
                    )
                }
            }
            buildFeatures {
                buildConfig = true
            }
            androidResources {
                generateLocaleConfig = true
            }

            buildTypes {
                debug {
                    isMinifyEnabled = false
                    isDebuggable = true
                    applicationIdSuffix = ".debug"
                }
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android.txt"),
                        "proguard-rules.pro"
                    )
                    isShrinkResources = true
                    isDebuggable = false
                    signingConfig = signingConfigs.getByName("releaseConfig")
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            namespace = "net.foucry.pilldroid"
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
    }
}
