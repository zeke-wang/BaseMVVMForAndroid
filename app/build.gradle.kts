plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.zekewang.basemvvmandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zekewang.basemvvmandroid"
        minSdk = 24
        targetSdk = 35
        versionCode = 100
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildFeatures {
        buildConfig = true
    }
    // 自动打包
    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = "orderdeliver123"
            storeFile = file("E:/android/keyStore/orderdeliver/orderdeliver.jks")
            storePassword = "orderdeliver123"
        }
    }

    buildTypes {
        getByName("debug") {
            // 业务接口服务器地址
            buildConfigField("String", "BASE_URL", "\"http://117.74.66.60:8107/prod-api/\"")
            // app版本升级服务器地址
            buildConfigField("String", "VERSION_URL", "\"http://120.24.230.150:9000/\"")
            // 升级对应的projectId
            buildConfigField("String", "PROJECT_ID", "\"1\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"http://117.74.66.60:8107/prod-api/\"")
            buildConfigField("String", "VERSION_URL", "\"http://120.24.230.150:9000/\"")
            buildConfigField("String", "PROJECT_ID", "\"1\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release") // 确保应用签名配置
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }

    // 自定义打包名称
    android.applicationVariants.all {
        // 编译类型
        val buildType = this.buildType.name
        outputs.all {
            // 判断是否是输出 apk 类型
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                outputFileName = "MyApplication_V${defaultConfig.versionName}_$buildType.apk"
            }
        }
    }
}

dependencies {

    // base
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // koin with mavenCentral
    implementation("io.insert-koin:koin-android:3.5.3")

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // navigation
    implementation(libs.navigation)
    implementation(libs.navigationUi)
}