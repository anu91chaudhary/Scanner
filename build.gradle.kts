// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id (Plugin.androidApplication) version Versions.kotlin apply false
    id (Plugin.androidLibrary) version Versions.kotlin apply false
    id (Plugin.androidKotlin) version Versions.androidKotlin apply false
    id (Plugin.daggerHilt) version Versions.daggerHilt apply false
}