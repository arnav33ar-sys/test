# Solve Overlay — Android starter project

This is a **demo/starter app**, not a finished AI question solver. It provides a movable floating “Solve” bubble and a demo answer card. It does **not** yet capture other apps' screens, read questions with OCR, or call an AI model.

## Get a demo APK using GitHub Actions

This project includes `.github/workflows/build-apk.yml`, which can build a debug APK on GitHub's servers.

1. On a computer or phone browser, sign in to GitHub and create a **new private repository**.
2. Upload the contents of this folder to the repository (upload the files inside `SolveOverlayAndroid`, including the hidden `.github` folder).
3. Open the repository's **Actions** tab and select **Build Android APK**.
4. Tap **Run workflow** if it is not already running. Wait for the workflow to finish successfully.
5. Open the completed workflow run and download the artifact named **SolveOverlay-debug-apk**.
6. Extract the downloaded ZIP; it contains `app-debug.apk`.
7. Move `app-debug.apk` to your Android phone and open it. If Android asks, allow your browser or file manager to **install unknown apps**, then install.

If the build fails, the APK was not created; check the workflow's error log.

## Run the demo

1. Install and open Solve Overlay.
2. Tap **Allow display over other apps**, enable permission, and return.
3. Tap **Start floating Solve button**.
4. Tap the floating Solve bubble to see the demo card. Reopen the app to stop the bubble.

## Important limitations

- This demo does not yet capture screenshots, perform OCR, or generate AI answers.
- Android requires explicit screen-capture consent and foreground-service handling for screen capture.
- Some secure screens and full-screen apps block overlays or screenshots.
- A real AI feature needs a vision-capable model and a secure backend. Do not put a private API key directly in the APK.
- The project has not been compiled or tested in this environment.
