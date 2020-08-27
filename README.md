# Skala Analytics
It's a library based on Analytics tools by DSC for Android Application

## Libraries and technologies
Libraries and technologies used in this libraries are: 

|  Tool  |  Name  |  Version  |
| ------ | ------ | ------ |
|Platform|Android|OS 4.4 KitKat Level 19 or higher|
|Programming Language| Kotlin|JDK 8.x|
|Rest Response|JSON||
|HttpClient|  OkHttpClient with Retrofit|[Okhttp Latest version](https://github.com/square/okhttp), [Retrofit Latest version](https://github.com/square/retrofit), [Logging Interceptor Latest version](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)|
|Reactive Programming|Coroutine|[Latest version](https://github.com/Kotlin/kotlinx.coroutines)|
|JSON Parser|GSON|Convert using Retrofit2Gson Converter [Latest version](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)|
>  Note: That the latest version based on the latest version by date.

## Download
To add this codebase to your  project, follow this step:
 - Add this line to your `gradle.properties` :

   ```sh
   authToken=$AskMaintainer
   ```
 - Add this line to your root `build.gradle`: 

   ```sh
    allprojects {
        repositories {
            google()
            jcenter()
            maven {
                url "https://jitpack.io"
                credentials { username authToken }
            }
    }
   ```
 - Add this line to your app `build.gradle`:
   ```sh
   implementation 'com.github.jimlyas:skala-analytic:${LatestVersion}'
   // Check the latest version at tag
   ```
 - Gradle sync, and that's it!

## Usage
To use this library to your  project, follow this step:
### Initialize class
Define the `SkalaAnalytic` class like this:
```kotlin
private val skala = SkalaAnalytic(context, endpoint, baseUrl)
```

or if you prefer using Dependency Injection, you can inject it like this for Koin:
```kotlin
val analyticModule = module {
    single{ SkalaAnalytic(androidApplication(), endpoint, baseUrl)   }
}
```
Then define it like this in your activity or your service:
```kotlin
private val skala by inject<SkalaAnalytic>()
```

### Logging Event
For logging event, you can just call `logEvent` method. Note that this method take `HashMap` of `Pair<String,String>` as parameter, so you can add as much any parameters as you need for your analytic need. So it will look something like this:
```kotlin
skala.logEvent(
    hashMapOf(
        Pair("key", "value"),
        Pair("event_name", "example event"),
        Pair("username", "example username"),
    )
)
```
 Beside all the parameters you defined, the library automatically add other **default parameters** every time you log an event, such as:
- dateTime of event (format: dd-MMM-yyyy HH:mm:ss:SSS)
- device name
- ram usage
- storage used
- cpu
- firmware
- ip address *if supported*
- carrier *if supported*
- network type *if supported*
- platform, and default value is Android. Cause it's Android library *duh*

### Canceling Event
There's a case where you want to cancel when you logging an event, e.g when you opening another activity. To do this you can call `cancel` method like this:
```kotlin
skala.cancel()
```