# MiniSpotify 🎵

**MiniSpotify** is a Java-based client-server application designed for managing music playlists.  
This educational project focuses on applying solid software engineering principles, including clean architecture, design patterns, and network communication.

> 🚨 **Note**: MiniSpotify is an academic project developed for learning purposes. It is not intended to replicate Spotify or offer real music streaming services to the public.

---

## 🎯 Project Context

MiniSpotify was developed as part of a software engineering module.  
The main goals of the project were:

- Apply the *SOLID* principles and ensure clean separation of concerns.
- Implement a layered architecture separating business logic, data access, and communication layers.
- Use Java *socket* programming to enable real-time client-server communication.
- Apply well-known design patterns such as *Singleton*, *Proxy*, *State*, *Observer*, and *Template* to reinforce good design practices.
- Focus on maintainability, extensibility, and testability throughout the codebase.

---

## 🛠️ How It Works

The project consists of two main components:

- **MiniSpotify-Client** (`MiniSpotify-client.jar`): The user-facing application where users can log in, manage playlists, and interact with the system.
- **MiniSpotify-Server** (`MiniSpotify-server.jar`): Handles client requests, manages users, playlists, and songs, and stores data in local JSON files.

Communication between the client and server is handled via **persistent Java socket connections**, ensuring efficient session management.  
Audio streaming (for preview purposes) uses **short-lived socket connections**.

---

## 🚀 Key Features

- Create, update, and delete playlists and songs.
- Search and add multiple songs to a playlist.
- Manage temporary playlists.
- Add multiple public playlists to a user’s library in one operation.
- Playback controls including *pause()* functionality.
- Passwords are securely hashed with salt for safe storage.
- Authentication is managed through a session-based system after initial login.
- JSON is used as a lightweight persistence layer.
- High test coverage with unit tests using *JUnit 5*.

---

## 🧩 Technologies Used

- Java 23
- Java Sockets (TCP)
- JSON (local persistence)
- JUnit 5 (testing)

---

## ⚠️ Disclaimer

MiniSpotify is **not** affiliated with Spotify and does not provide music streaming services.  
It is a **learning project** built to demonstrate principles of clean code, architecture, and networked application design.

---

## 📜 License

This project is open for educational and personal exploration.  
No license is currently applied.

---

Feel free to explore the code, review the architecture, or fork it for learning purposes! 😊
