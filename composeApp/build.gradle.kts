import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
//    alias(libs.plugins.jetbrains.kotlin.serialization)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

//    room {
//        schemaDirectory("$projectDir/schemas")
//    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
         //   implementation("androidx.room:room-compiler:2.6.1")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // Add these new dependencies:

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            // For coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

            // For Base32 encoding - Apache Commons Codec
            implementation("commons-codec:commons-codec:1.16.0")

//            implementation("androidx.room:room-runtime:2.6.1")
//            implementation("androidx.room:room-ktx:2.6.1")
//            implementation("androidx.sqlite:sqlite-bundled:2.4.0")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
         //   implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
           implementation("androidx.datastore:datastore-preferences-core:1.1.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            // Room compiler for iOS
        //    implementation("androidx.room:room-compiler:2.6.1")
        }
        desktopMain.dependencies {
          //  implementation("androidx.room:room-compiler:2.6.1")
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
//        dependencies {
//            ksp(libs.androidx.room.compiler)
//        }
    }
}

android {
    namespace = "org.haidrrrry.auth"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.haidrrrry.auth"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.room.common.jvm)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.haidrrrry.auth.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.haidrrrry.auth"
            packageVersion = "1.0.0"
        }
    }
}
