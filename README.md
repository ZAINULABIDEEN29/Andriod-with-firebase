# My MiniProject App

This is a simple Android app I built to handle user logins and sign-ups. It connects to a Google Firebase project to manage all the user data, so there's no need for a custom backend.

## What it Does

*   **Sign Up:** A user can create an account with their email and a password.
*   **Login:** An existing user can log in.
*   **Forgot Password:** If a user forgets their password, they can type in their email. The app uses Firebase to send them a password reset link.
*   **In-App Reset:** When the user clicks the link in their email, it opens the app directly to a "Reset Password" screen instead of opening a website. This is handled using Firebase Dynamic Links.

## Tech I Used

*   **Language:** Java
*   **UI:** XML
*   **Backend:** Google Firebase
    *   **Firebase Authentication:** Handles all the user accounts, sign-ins, and password security.
    *   **Firebase Dynamic Links:** This is the magic that makes the password reset link open the app directly.

## How to Get it Running

If you want to run this project yourself, you need to connect it to your own Firebase project.

### 1. Connect to Firebase

*   Go to the [Firebase Console](https://console.firebase.google.com/) and make a new project.
*   Inside the project, add a new Android App.
*   When it asks for the **Package Name**, you have to use `com.example.miniproject`. The app will break if you use anything else.
*   It will ask for a **SHA-1 key**. To get this:
    *   In Android Studio, open the `Gradle` tab on the right.
    *   Go to `Your Project Name` -> `app` -> `Tasks` -> `android` and double-click `signingReport`.
    *   The SHA-1 key will show up in the console at the bottom. Copy and paste it into the Firebase setup.
*   Download the `google-services.json` file that Firebase gives you.
*   Drag and drop that file into the `app` folder of the project in Android Studio, replacing the old one.

### 2. Enable Firebase Features

*   In the Firebase Console, find the **Authentication** section.
*   Go to the **Sign-in method** tab and enable **Email/Password**.
*   Go to the **Settings** tab. Under **Authorized domains**, you have to add `miniproject.page.link`. The password reset emails will fail without this.

### 3. Build it

*   Open the project in Android Studio.
*   Let Gradle do its thing and download all the libraries.
*   Run the app.

It should work now. You can create an account, log out, and test the forgot password feature.
