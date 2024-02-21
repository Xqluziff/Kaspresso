import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("convention.kotlin-base")
    id("convention.android-base")
    kotlin("android")
}

android {
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    testBuildType = "debug"

    defaultConfig {
        multiDexEnabled = true
    }

    variantFilter {
        if (name != testBuildType) {
            ignore = true
        }
    }

    buildTypes {
        getByName(testBuildType) {
            matchingFallbacks += listOf("release")
        }
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        viewBinding = true // for the samples
    }

    kotlin {
//        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))

        jvmToolchain(JavaVersion.VERSION_1_8.majorVersion.toInt())
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
