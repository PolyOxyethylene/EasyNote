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
        versionName = "0.4.3.0-beta"

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
    implementation(project(mapOf("path" to ":DrawerBox")))
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
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // 下面是自己导入的
    // viewmodel + livedata + Jetpack Compose State 整合
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.1")

    // navigation
    val nav_version = "2.5.3"

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

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
    val dialogx_version_release = "0.0.49"
    val dialogx_version_beta = "0.0.50.beta10"
    // 正式版
//    implementation ("com.kongzue.dialogx:DialogX:$dialogx_version_release")
    // 测试版
    implementation ("com.github.kongzue.DialogX:DialogX:$dialogx_version_beta")

    // DialogX MIUI 主题
    // 正式版
//    implementation ("com.kongzue.dialogx.style:DialogXMIUIStyle:$dialogx_version_release")
    // 测试版
    implementation ("com.github.kongzue.DialogX:DialogXMIUIStyle:$dialogx_version_beta")

    // DialogX 扩展组件
    val dialogx_sample_version = "0.0.12"
    //文件选择对话框
    implementation ("com.github.kongzue.DialogXSample:FileDialog:$dialogx_sample_version")
    //照片选择器
    implementation ("com.github.kongzue.DialogXSample:AlbumDialog:$dialogx_sample_version")

    // 富文本编辑器
    implementation ("jp.wasabeef:richeditor-android:2.0.0")

    // PermissionX
    implementation ("com.guolindev.permissionx:permissionx:1.7.1")

    // 关键词提取，暂时不可用
    implementation (files("libs/jar/ahanlp-1.3.jar"))

    // Cascade 下拉菜单
    val cascade_version = "2.3.0"
    implementation ("me.saket.cascade:cascade-compose:$cascade_version")

    // 支持子菜单的 FAB
    implementation("com.leinardi.android:speed-dial.compose:1.0.0-alpha04")

}