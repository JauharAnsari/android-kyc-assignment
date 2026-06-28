# KYCFlow (Android KYC Assignment)

KYCFlow is a modern, fully-functional Android application designed to manage and verify customer KYC (Know Your Customer) information. This assignment project showcases modern Android development practices, emphasizing clean architecture, reactive programming, and a declarative UI.

## 🚀 Features

- **Customer Management**: View lists of customers categorized by their verification status (Pending vs. Verified).
- **Intuitive UI**: Smooth, swipeable tabs and polished animations built entirely with Jetpack Compose.
- **Detailed Profiles**: In-depth account details screen for reviewing individual customer data.
- **Camera Integration**: Built-in camera functionality (CameraX) for capturing KYC documents or user photos.
- **Offline Support**: Local caching using Room Database to ensure data persistence and offline capabilities.
- **Robust Networking**: API interactions handled efficiently with Retrofit and OkHttp.

## 🛠️ Tech Stack & Architecture

This project is built using the latest Android tech stack and follows the **MVVM (Model-View-ViewModel)** architectural pattern along with Clean Architecture principles.

- **[Kotlin](https://kotlinlang.org/)**: 100% Kotlin codebase.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Android’s modern toolkit for building native UI declaratively.
- **[Dagger Hilt](https://dagger.dev/hilt/)**: Dependency injection for scalable and testable architecture.
- **[Coroutines & Flow](https://kotlinlang.org/docs/coroutines-overview.html)**: Asynchronous programming and reactive data streams.
- **[Room Database](https://developer.android.com/training/data-storage/room)**: SQLite object mapping library for local data caching.
- **[Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)**: Type-safe HTTP client for network requests.
- **[CameraX](https://developer.android.com/training/camerax)**: Jetpack support library for camera app development.
- **[Coil](https://coil-kt.github.io/coil/)**: Fast, lightweight image loading library backed by Kotlin Coroutines.
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**: Routing and screen transitions.

## 📁 Project Structure

The project is modularized by layer (Data, Domain, Presentation) to maintain a clear separation of concerns:

- **`data/`**: Contains API services, Room DAOs, repositories implementations, and data mappers.
- **`domain/`**: Houses the core business logic, domain models (e.g., `Customer`), and repository interfaces.
- **`presentation/`**: Contains the UI layer components including Compose screens (`AccountsScreen`, `AccountDetailsScreen`, `CameraScreen`), view models, navigation routes, and custom UI components/theme.

## ⚙️ Getting Started

### Prerequisites
- Android Studio (Koala or later recommended)
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 36

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/JauharAnsari/android-kyc-assignment.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync and download the dependencies.
4. Build and run the app on an emulator or physical device.

## 📸 Screenshots & Highlights

- **Accounts Dashboard**: Displays a paginated list of accounts with filter chips and a swipeable tab layout (Pending/Verified).
- **KYC Details**: View detailed information about specific customers.
- **Document Capture**: A seamless camera interface to quickly capture verification documents.

---
*Developed as part of the Signzy Android Assignment.*
