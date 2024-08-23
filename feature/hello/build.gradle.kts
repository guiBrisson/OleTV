plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {

        }

        commonMain.dependencies {

        }

        iosMain.dependencies {

        }
    }
}

android {
    namespace = "br.tv.ole.feature"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
