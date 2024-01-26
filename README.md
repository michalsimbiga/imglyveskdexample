
# Overview
This minimal working example (MWE) project is designed to demonstrate working example of IMGLY Video editor SDK implementation to use with Jetpack Compose that produces the bug.

The bug involves an unexpected crash when attempting to add an audio overlay to a video in vesdk editor.
This README provides a step-by-step guide to set up and reproduce the bug.


# Getting Started
Prerequisites
- Android Studio
- Any android device / emulator

# Reproduction steps
- Run the application on mobile phone/ emulator
- Click on "Select video to edit" button
- Select any video from the device gallery view
- Click on "Load to vesdk" button
- In video editor click on "Audio" to add audio overlay

# Expected Behavior
Audio paths should be visible on screen or either user should be able to select local audio path to use

# Observed behaviour
App crashes 


## Stack trace:
```
E  FATAL EXCEPTION: main
                 Process: com.msimbiga.imglvesdkexample, PID: 7135
                 java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
                 	at java.util.ArrayList.get(ArrayList.java:437)
                 	at ly.img.android.pesdk.ui.panels.AudioGalleryToolPanel.onAttached(AudioGalleryToolPanel.kt:115)
                 	at ly.img.android.pesdk.ui.panels.AbstractToolPanel.lambda$callAttached$2$ly-img-android-pesdk-ui-panels-AbstractToolPanel(AbstractToolPanel.java:254)
                 	at ly.img.android.pesdk.ui.panels.AbstractToolPanel$$ExternalSyntheticLambda2.run(Unknown Source:6)
                 	at android.os.Handler.handleCallback(Handler.java:942)
                 	at android.os.Handler.dispatchMessage(Handler.java:99)
                 	at android.os.Looper.loopOnce(Looper.java:201)
                 	at android.os.Looper.loop(Looper.java:288)
                 	at android.app.ActivityThread.main(ActivityThread.java:7872)
                 	at java.lang.reflect.Method.invoke(Native Method)
                 	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
                 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:936)
```