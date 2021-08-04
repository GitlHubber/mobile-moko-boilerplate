/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.icerock.gradle.talaiot") version("3.+")
}

include(":android-app")
include(":mpp-library")
include(":mpp-library:feature:auth")
include(":mpp-library:feature:listSample")
