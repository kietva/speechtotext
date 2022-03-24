
# Speech To Text
[![](https://jitpack.io/v/kietva/speechtotext.svg)](https://jitpack.io/#kietva/speechtotext)

This is a simple build of the library for some personal project use.

Combination of voice recording and speech to text api.


## Installing
```gradle
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```

```gradle
dependencies {
        implementation 'com.github.kietva:speechtotext:v1.0.0'
}
```
## Permission
Add these permissions into your `AndroidManifest.xml` and [request for them in Android 7.0+](https://developer.android.com/training/permissions/requesting.html)
```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
```
## Usage/Examples
#### Init
```kotlin
val speechService = SpeechManager.create(this)
            .setLifecycle(lifecycle)
            .setKey("")
            .setRegion("")
            .setLanguage("")
            .setPathFile(path,fileName) // if use custom path
            .build()
```
#### Callback
```kotlin
 speechService.setVoiceListener(object : SpeechListener{
            override fun onStart() {
            }

            override fun onComplete(result: String) {
            }

            override fun onError(error: String) {
            }
        })
```


## Acknowledgements

 - [Android-Wave-Recorder](https://github.com/squti/Android-Wave-Recorder)
 - [Androidspeech](https://github.com/mozilla/androidspeech)
 - [Azure Speech SDK](https://github.com/Azure-Samples/cognitive-services-speech-sdk)


## License

MIT License

Copyright (c) 2022 KIETVA

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

