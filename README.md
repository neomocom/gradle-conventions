# gradle-conventions

## Publish to maven local

`./gradlew publishToMavenLocal`

## Usage in other projects

build.gradle.kts
```
plugins {
    id("com.neomo.gcf") version "0.3.1"
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
