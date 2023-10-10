object Plugin {
    const val androidApplication = "com.android.application"
    const val androidKotlin = "org.jetbrains.kotlin.android"
    const val androidLibrary = "com.android.library"
    const val daggerHilt = "dagger.hilt.android.plugin"
    const val kotlinAndroidKapt = "kapt"
    const val parcelize = "kotlin-parcelize"
}

object Dependencies {
    const val materialComponent =
        "com.google.android.material:material:${Versions.materialComponent}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val mlKit = "com.google.mlkit:barcode-scanning:${Versions.mlKit}"
    const val camera2= "androidx.camera:camera-camera2:${Versions.cameraX}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraX}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraX}"
    const val lifeCycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.daggerHilt}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object TestDependencies {
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val testExtJUnit = "androidx.test.ext:junit:${Versions.jUnitExt}"
    const val testEspressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}