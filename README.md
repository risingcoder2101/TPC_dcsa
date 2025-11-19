# TPC Portal (Training & Placement Cell)

A modern Android app for managing students, companies, and placements for the Training & Placement Cell (TPC) of your institution. This app is built in Java, uses Firebase for authentication and data storage, and supports real-time updates across devices.

## Features

- **Login System**: Secure login for admin (e.g., admin@tpc.com / admin123 via Firebase Authentication).
- **Dashboard**: Quick overview of statistics, upcoming drives, and actions.
- **Student Management**:
  - Add new students manually or import from Excel (.xlsx).
  - View, edit, and manage student details (Name, Roll Number, Gender, Batch, Email, Phone, Campus, Percentage).
  - Placed students are shown in a dedicated section.
- **Company Management**:
  - Add new companies (Name, Location, Role(s), Package, Drive Date, Status).
  - View, edit, and delete companies.
  - Real-time sync across devices (Firebase Firestore).
- **Placement Drives**:
  - View upcoming and completed drives.
  - Assign students to companies.
- **Modern UI**: Material Design, gradient headers, cards, and responsive layouts.

## Getting Started

1. **Clone the repository**
2. **Open in Android Studio**
3. **Add your Firebase `google-services.json` to `app/`**
4. **Sync Gradle and build the app**
5. **Run on device/emulator**

## Firebase Setup
- Enable Authentication (Email/Password)
- Create users (e.g., admin@tpc.com)
- Enable Firestore Database
- Structure:
  - `students` collection
  - `companies` collection

## Usage
- Login as admin
- Add/view/edit students and companies
- Import students from Excel
- View placed students and placement statistics

## Screenshots
- Login
- Dashboard
- Add Student
- Add Company
- Placed Students

## Tech Stack
- Java (Android)
- Firebase Authentication & Firestore
- Material Design Components

## License
This project is for educational purposes only.

---
For any issues or feature requests, please open an issue in this repository.

