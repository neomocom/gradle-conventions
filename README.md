# gradle-conventions

## Publish to gradle plugin portal
*Publishing to the gradle plugin portal can only be done by Benedikt Arnold.*

`./gradlew publishPlugins`

## Publish to maven local

`./gradlew publishToMavenLocal`

## Usage in other projects

build.gradle.kts
```
plugins {
    id("com.neomo.gcf") version "<latest-version>"
}
```

settings.gradle.kts
```
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}
```

## Documentation regarding gradle convention plugins

https://docs.gradle.org/current/samples/sample_publishing_convention_plugins.html
