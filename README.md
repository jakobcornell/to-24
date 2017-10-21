# To 24

This application runs in the background and executes a task (changing the system clock to 24-hour format) on a randomized interval. It's designed to run undetected and survive reboots without intervention.

## Build process

You'll need the Android SDK. I downloaded it to <code><i>dir</i></code> and set

<pre>
sdk.dir=<i>dir</i>
</pre>

in `local.properties`. Android Studio users probably don't have to worry about this.

Then, build the project using your IDE or Gradle wrapper. For a signed build, you'll need to configure your project to use a Java keystore as described [here](https://developer.android.com/studio/publish/app-signing.html#secure-key).

## Installation

To protect users from apps like this, Android requires an app to be launched by the user before startup hooks are registered. After installing the app on a device, an activity called "To 24" will appear in the launcher. When the installing party launches this activity, it will close automatically and remove its entry from the launcher, and the background task will be scheduled the next time the device is rebooted.

## Termination

The app will run in the background until it's killed. You can kill the app by finding its entry in the list of running applications in the settings app. Once killed, the app cannot be restarted from the Android UI, and it may need to be reinstalled and relaunched.
