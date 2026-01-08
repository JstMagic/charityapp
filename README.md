# CharityApp

CharityApp is an Android application built in Java that enables users to browse charities, make donations, and manage charity-related content through a role-based interface.

The application includes functionality for donors, charity administrators, and content managers, providing a complete end-to-end charity and donation workflow.

---

## Project Structure

The application is organised using logical, feature-based package grouping within Android Studio.  
This approach separates UI components, business logic, and data models to improve maintainability and scalability.

> **Note:** The structure below represents the *conceptual organisation* of the code as viewed in Android Studio, rather than the raw folder layout displayed on GitHub.

```text
com.kodemakers.charity/
├── activities/
│   ├── authentication/      # LoginActivity, SignUpActivity, ForgotPasswordActivity
│   ├── donor/               # MainActivity, CharityDetailsActivity, DonationActivity
│   ├── charity/             # AddNewStoryActivity, UpdateCharityDetailsActivity, AddStaffActivity
│   ├── user/                # ProfileActivity, AccountDetailsActivity, UsersListActivity
│   ├── content/             # StoriesActivity, PlayVideoActivity, AddVideoFeedActivity
│   └── common/              # SplashActivity, NotificationActivity, IntroSteppersActivity
├── adapter/                 # RecyclerView adapters
├── model/                   # Data models (User, Charity, Donation)
├── service/                 # Business logic & API services
├── custom/                  # Custom UI components
├── util/                    # Fonts, helpers, extensions
└── CharityApp.java          # Application class
```
## Key Features

# User Authentication

Login, registration, and password recovery

# Donation System

Browse charities and make donations

- View charity details and donation-related content

# Charity Management

- Add and manage charity stories, videos, and staff

- Update charity profiles and information

# User Profiles

- Account and contributor profile management

# Content Management

- Stories, video feeds, and onboarding flows

# Administrative Features

- User and staff listings

- In-app notifications

## Technical Details
- **Language**: Java (100%)
- **Package**: `com.kodemakers.charity`
- **Platform**: Android
- **Backend Services**: Firebase (Authentication, data storage, notifications)
 
##  Quick Start

### Prerequisites
- Android Studio
- Firebase account

### Installation
 Clone the repository:
   ```bash
   git clone https://github.com/JstMagic/charityapp.git
   cd charityapp
```

1. Firebase Setup:
   · Create a Firebase project
   · Add Android app with package: com.kodemakers.charity
   · Download google-services.json
   · Replace the file in app/ directory
2. Build and Run:
   ```bash
   # Open in Android Studio
   # Click Run → Run 'app'
   ```

Features (Based on Activity Names)

- User Authentication: Login, SignUp, ForgotPassword
- Charity Management: Add stories, videos, staff, update details
- Donation System: Process and track donations
- User Profiles: Contributor and account management
- Content Management: Stories, videos, onboarding
- Admin Features: User and staff listings
- Notifications: In-app notifications system

 Important Notes
 
- Update dependencies for current Android versions
- Requires Firebase configuration
- Contains both donor and admin interfaces

 Links

· Repository: https://github.com/JstMagic/charityapp
