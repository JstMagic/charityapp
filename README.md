# CharityApp

CharityApp is an Android application built in Java that enables users to browse charities, make donations, and manage charity-related content through a role-based interface.

The application supports donors, charity administrators, and content managers, providing a complete end-to-end charity and donation workflow.

## Project Structure

The application is organised using logical, feature-based package grouping within Android Studio. This approach separates UI components, business logic, and data models to improve maintainability and scalability.

The source code is primarily organized under `app/src/main/java/com/kodemakers/charity/`:

```text
activities/    Screens and flows
adapter/       RecyclerView adapters
app/           Application-wide config
custom/        Helpers, networking, storage utilities
fonts/         Custom text widgets
model/         Response and domain models
service/       Firebase and notification services
```

## Key Features

- User authentication: login, registration, and password recovery
- Donation system: browse charities and make donations
- Charity management: add and manage stories, videos, staff, and charity details
- User profiles: account and contributor profile management
- Content management: stories, video feeds, and onboarding flows
- Administrative features: user and staff listings, plus in-app notifications

## Prerequisites

- Android Studio
- JDK compatible with Android Gradle Plugin `3.3.2`
- A Firebase project for `com.kodemakers.charity`

## Local Setup

1. Clone the repository:

```bash
git clone https://github.com/JstMagic/charityapp.git
cd charityapp
```

2. Add Firebase config:
   Place your own `google-services.json` file in `app/google-services.json`.

3. Open the project in Android Studio and let Gradle sync.

4. Run the `app` configuration on an emulator or device.

## Secrets And Local Files

This repo does not track signing keys or Firebase config.

- Keep keystores out of version control.
- Keep `google-services.json` local to your machine.
- Use Android Studio's default debug signing for local development.

## Technical Details

- Language: Java
- Package: `com.kodemakers.charity`
- Platform: Android
- Backend services: Firebase for authentication, data storage, and notifications
- Gradle wrapper: `4.10.1`
- Android Gradle Plugin: `3.3.2`
- `compileSdkVersion` / `targetSdkVersion`: `27`

## Contribution Ideas

- Upgrade Gradle, Android Gradle Plugin, and SDK targets
- Replace deprecated Firebase Instance ID usage
- Remove legacy storage assumptions for newer Android versions
- Add setup validation and clearer error handling around Firebase-dependent flows
- Add contributor docs and screenshots

## Repository

- Main project: https://github.com/JstMagic/charityapp
