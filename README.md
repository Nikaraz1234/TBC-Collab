# Event Management Application

**A full-featured Event Management app written in Kotlin using MVI architecture.**

---

## Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [License](#license)

---

## About the Project

This project is a modern Event Management application that allows users to:

- Create account and login.
- Browse, search, and filter events.
- View event details with images, descriptions, and schedules.
- Register for events and manage registrations.
- Receive notifications about upcoming events.
- Rate and review events.

The application is implemented using **Kotlin**, **MVI architecture**, **Coroutines** and maintainable codebase.

---

## Features

- **Event Browsing**: List events with filters by category, date, or popularity.
- **Event Details**: View detailed information, images, and participant info.
- **Registration System**: Register/unregister for events.
- **Notifications**: Receive updates and reminders for events.
- **Search Functionality**: Search events by name.
- **Reactive UI**: State-driven UI using MVI pattern.

---

## Architecture

**MVI (Model-View-Intent)** pattern ensures a unidirectional data flow and predictable state management:

- **Model**: Represents the app state. Immutable and reactive.
- **View**: Observes state changes and renders UI accordingly.
- **Intent**: Handles user actions and triggers business logic.

**Other patterns and principles used:**

- Repository pattern for data abstraction
- Dependency Injection with **Hilt**
- Coroutines for asynchronous operations
- Clean separation of layers: **Data → Domain → Presentation**

---

## Technologies Used

- **Kotlin** – Programming language.
- **MVI Architecture** – State management.
- **Coroutines & Flow** – Asynchronous programming.
- **Retrofit** – API communication.
- **Hilt** – Dependency injection.
- **Navigation Component** – In-app navigation.
- **Git & GitHub** – Version control.

## Installation

1. Clone the repository:

```bash
git clone https://github.com/Nikaraz1234/TBC-Collab.git
