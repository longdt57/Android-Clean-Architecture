plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.kover)
    id("maven-publish")
}

android {
    namespace = "leegroup.module.designsystem"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeExtensionVersion.get()
    }
    lint {
        checkDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {

    implementation(projects.core.coreKtx)
    implementation(projects.core.data)

    implementation(libs.android.tracking)

    // Lifecycle
    api(libs.bundles.androidx.lifecycle)

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui.tooling.preview)

    api(libs.androidx.material3)
    api(libs.androidx.compose.material.icons.extended)

    api(libs.androidx.core.ktx)
    api(libs.androidx.navigation.compose)
    api(libs.androidx.appcompat)

    // Coil
    api(libs.bundles.coil)

    // Serialization
    api(libs.kotlinx.serialization.json)

    // Retrofit
    implementation(libs.bundles.networking)

    testImplementation(libs.bundles.test)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(platform(libs.androidx.compose.bom))
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "leegroup.module"
            artifactId = "designsystem"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
