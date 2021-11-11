
## BorrowPower SDK

The BorrowPower Android SDK is designed to work on both Android Powered mPos and Android Mobile apps running on **Android version 6 and later.** This doc shows how to integrate the SDK using Android Studio

## Requirements
- Android Studio 3.5.0 and later

## Installation

### Android Studio (using Gradle)
Add the following lines to your project level `build.gradle`:
```gradle
allprojects {
  repositories {
   ...
   maven { url 'https://jitpack.io' }
  }
}
```
Add the following lines to your app level `build.gradle`:
```
dependencies {
   implementation 'com.github.BorrowPower:borrow-power-android:v1.0.0'
}
```

## Usage

### 1.0) Add Required permission

Head over to  your project  AndroidManifest.xml and add the `uses-permission` line below

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 1.1) Initialize SDK

Initialize the SDK:

```java
    // Environment.SANDBOX or Environment.LIVE
    new BorrowPower().with("PartnerID", Environment.SANDBOX)
    .build(MainActivity.this, new onSuccess() {
      @Override
      public void onSuccess() {

      }
    }, new onFailed() {
      @Override
      public void onFailed(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
      }
    });
```
## Issues and Contributions
[Issue Tracker](https://github.com/BorrowPower/borrow-power-android/issues)
