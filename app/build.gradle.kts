plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.oxyethylene.easynote"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.oxyethylene.easynote"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.4.5.6-beta"

        // 使 DialogX 的实时模糊效果生效
        renderscriptTargetApi = 21
        renderscriptSupportModeEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(project(mapOf("path" to ":AlbumDialog")))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // 下面是自己导入的
    // viewmodel + livedata + Jetpack Compose State 整合
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.1")

    // navigation
//    val nav_version = "2.5.3"

    // Kotlin
//    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
//    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support
//    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
//    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Jetpack Compose Integration
//    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Jetpack Room
    val room_version = "2.3.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
//    ksp("androidx.room:room-compiler:$room_version")

    // 老版的 Material Design2
    implementation("androidx.compose.material:material:1.6.3")

    // 更强大的对话框 DialogX
//    val dialogx_version_release = "0.0.49"
    val dialogx_version_beta = "0.0.50.beta11"
    // 正式版
//    implementation ("com.kongzue.dialogx:DialogX:$dialogx_version_release")
    // 测试版
    implementation("com.github.kongzue.DialogX:DialogX:$dialogx_version_beta")

    // DialogX MIUI 主题
    // 正式版
//    implementation ("com.kongzue.dialogx.style:DialogXMIUIStyle:$dialogx_version_release")
    // 测试版
    implementation("com.github.kongzue.DialogX:DialogXMIUIStyle:$dialogx_version_beta")

    // DialogX 扩展组件
    val dialogx_sample_version = "0.0.13"
    //文件选择对话框
    implementation("com.github.kongzue.DialogXSample:FileDialog:$dialogx_sample_version")

    // PermissionX
    implementation("com.guolindev.permissionx:permissionx:1.7.1")

    // Cascade 下拉菜单
    val cascade_version = "2.3.0"
    implementation("me.saket.cascade:cascade-compose:$cascade_version")

    // 支持子菜单的 FAB
    implementation("com.leinardi.android:speed-dial.compose:1.0.0-alpha04")

    // 序列化工具 Serialize
    implementation("com.github.liangjingkanji:Serialize:3.0.1")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")
    // Gson 解析容错：https://github.com/getActivity/GsonFactory
    implementation("com.github.getActivity:GsonFactory:9.6")
    // Kotlin 反射库：用于反射 Kotlin data class 类对象
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")

    // HANLP 文本处理模型，用于分析文章提取关键词和文章摘要
    implementation("com.hankcs.hanlp.restful:hanlp-restful:0.0.15")

    // 简化 Android 开发的 Kotlin 工具类集合
    val androidKTX_version = "1.1.0"
    implementation("io.github.androidktx:android-ktx:$androidKTX_version")

    // kotlin 协程
    val coroutines_version = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version") // 协程(版本自定)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    // OkHttp
    val okhttp_version = "4.12.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version") // 要求OkHttp4以上

    // Net 基于协程和 OkHttp3 的网络请求框架
    val net_version = "3.6.4"
    implementation("com.github.liangjingkanji:Net:$net_version")

}