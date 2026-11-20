This is a Kotlin Multiplatform project targeting Android, Desktop (JVM).

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Local API key

Create `local-config/api.properties` in the repository root (the directory is Git-ignored):

```properties
apiKey=YOUR_DEVELOPMENT_API_KEY
```

Use the development API key expected by the API, not OAuth credentials. For Android, rebuild and reinstall the app after changing the file; Gradle packages it as an asset. For desktop development, run from the repository root or `desktopApp` directory and restart after changing it. On Windows, a file at `%LOCALAPPDATA%\XspPulse\api.properties` takes precedence if it exists. If the file or property is missing, the apps send an empty key and authenticated requests will fail.

This is development configuration, not secret storage: the Android key is embedded in the APK and can be extracted. Do not distribute builds containing sensitive credentials or commit the local file.

### Installing on Windows

Build the MSI with `./gradlew --no-configuration-cache :desktopApp:packageMsi`, (`--no-configuration-cache` avoids the current WiX task incompatibility), then run the installer from `desktopApp/build/compose/binaries/main/msi/`. Create `%LOCALAPPDATA%\XspPulse\api.properties` for your Windows account with the same `apiKey=YOUR_DEVELOPMENT_API_KEY` format shown above, and restart the application after changing it. The installer does not include this file. If it is absent, the application falls back to the development paths described above.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- Desktop tests: `./gradlew :shared:jvmTest`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…