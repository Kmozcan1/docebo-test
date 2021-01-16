# Docebo Test Application

This application was developed for Docebo® as a part of their hiring process. 

## Building the Application

### Android Studio (Recommended)

* Open Android Studio. If another project is open, select `File -> New -> Project from Version Control...`; if no project is open, select `Get from Version Control` from the launcher.
* Enter `https://github.com/Kmozcan1/docebo-test` inside the `URL` field, select your desired directory in the `Directory` field and click the `Clone` button.
* Gradle sync should start automatically. You can also force start it manually by selecting `File -> Sync Project with Gradle Files`, or by clicking the elephant icon with blue arrow on the Android Studio's toolbar.
* To build the project, select `Build -> Make Project` or click the green hammer icon on the Android Studio's toolbar.

### Terminal / Command Prompt

* Navigate to the desired project folder with `cd <path-to-desired-folder>`
* Type the following line in order to clone the repo: `git clone https://github.com/kmozcan1/docebo-test.git`. If you are currently not logged in, enter your GitHub credentials to proceed.
* Type `cd docebo-test` to navigate to the project folder
* For the following step, you need to define ANDROID_SDK_ROOT and JAVA_HOME environment variables:
  * For Windows systems:
    * `setx ANDROID_SDK_ROOT <path-to-android-sdk>` (example path: `C\:\Users\USER\AppData\Local\Android\Sdk`, without `<` and `>`)
    * `setx JAVA_HOME <path-to-java-jdk>` (example path: `C:\Program Files\Java\jdk1.8.0_121`, without `<` and `>`)
  * For Linux systems:
    * `setx ANDROID_SDK_ROOT=<path to android sdk>`
    * `setx JAVA_HOME=<path to java jdk>`
  * Alternatively, you can set these variables inside `local.properties` and `gradle.properties` files:
    * Create a file named `local.properties` and add the line: `sdk.dir=<path-to-android-sdk>` (example path: `C\:\\Users\\USER\\AppData\\Local\\Android\\Sdk`, without `<` and `>`)
    * Open `gradle.propertes` and add the line line `org.gradle.java.home=<path-to-java-jdk>` (example path: `C:\\Program Files\\Java\\jdk1.8.0_121`, without `<` and `>`)
* Enter the command `gradlew build` to build the project.

## Running application on your device

In order to run the debug APK on your device, you need to enable USB debugging by [following the instructions provided by Android Development team.](https://developer.android.com/studio/debug/dev-options#enable)

### Android Studio
* Open the project by selecting `File -> Open...`
* Make sure that your device is connected to your PC. Select your device from Android Studio's toolbar in the dropdown menu that lists the available devices.
* Select `Run -> Run app` or click the green `play` icon.

### Terminal / Command Prompt
* Navigate to the project folder with the following command: `cd <path-to-the-project-folder>` 
* Make sure that your device is connected to your PC.
* Enter `gradlew installDebug` to deploy the debug apk to your device. 
  
  
  
  
