import org.gradle.api.artifacts.dsl.DependencyHandler

//Dependency Handler method
private fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.api(depName: String) {
    add("api", depName)
}

private fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

private fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.implementationPlatform(depName: String) {
    add("implementation", platform(depName))
}

fun DependencyHandler.androidX() {
    api(Dependencies.materialComponent)
    api(Dependencies.appCompat)
    api(Dependencies.constraintLayout)
    coreKtx()
}

fun DependencyHandler.mlkit() {
    implementation(Dependencies.mlKit)
}

fun DependencyHandler.testEspressoCore() {
    androidTestImplementation(TestDependencies.testEspressoCore)
}

fun DependencyHandler.jUnit() {
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.testExtJUnit)
}

fun DependencyHandler.cameraX() {
    api(Dependencies.camera2)
    api(Dependencies.cameraLifecycle)
    api(Dependencies.cameraView)
}

fun DependencyHandler.lifeCycleRuntimeKtx() {
    implementation(Dependencies.lifeCycleRuntimeKtx)
}
fun DependencyHandler.coroutines() {
    implementation(Dependencies.coroutines)
}

fun DependencyHandler.coreKtx() {
    api(Dependencies.coreKtx)
}

fun DependencyHandler.daggerHilt() {
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.navigationUi)
}
