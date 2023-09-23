pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "kommander", "kash", "symphony", "koncurrent", 
    "geo-api", "cinematic", "kase", "lexi"
).forEach {
    includeBuild("../$it")
}

rootProject.name = "geo-client"

includeSubs("geo", ".", "fields")