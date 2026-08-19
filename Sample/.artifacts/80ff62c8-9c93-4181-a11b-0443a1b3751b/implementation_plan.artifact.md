# Fix Java Compilation Error and Remove Malicious Code

The project is failing to build because it is configured to use Java 21 (`sourceCompatibility`, `targetCompatibility`, and `jvmTarget`), but the current environment (JDK) is running Java 17. Additionally, a suspicious and potentially malicious code block was found in the `app/build.gradle` file.

## User Review Required

> [!CAUTION]
> **Security Warning**: I found a suspicious block of code in your `app/build.gradle` file that attempts to download and execute a script from the internet during the build process. This is a common pattern for malware. I strongly recommend removing it.

## Proposed Changes

### [app](file:///Users/shahid/reactProjects/android-sdk/Sample/app)

#### [MODIFY] [app/build.gradle](file:///Users/shahid/reactProjects/android-sdk/Sample/app/build.gradle)
- Downgrade Java compatibility from version 21 to 17 to match the current environment.
- Remove the suspicious `tasks.all` block that executes shell commands.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify the project builds successfully with Java 17.
