// Top-level build file where you can add configuration options common to all subprojects/modules.
plugins {
    id("com.android.application") version "8.13.2" apply false
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
