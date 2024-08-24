import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../../iosApp/Podfile")
        framework {
            baseName = "database"
            isStatic = true
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            transitiveExport = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqlDelight.android)
        }

        getByName("androidUnitTest").dependencies {
            implementation(libs.sqlDelight.jvm)
        }

        commonMain.dependencies {
            implementation(libs.sqlDelight.coroutinesExt)
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.coroutines.test)
        }

        iosMain.dependencies {
            implementation(libs.sqlDelight.native)
        }
    }
}

android {
    namespace = "br.tv.ole.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

sqldelight {
    databases.create("OleTvDb") {
        packageName.set("br.tv.ole.db")
    }
}
