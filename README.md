# NoteApp 📝

NoteApp is an Android application that helps users create, organize, and manage their notes with priority levels. The app features a clean interface with local SQLite database storage for persistent note management.

## Features ✨

- Create notes with titles, descriptions, and priority levels
- Priority system (High, Urgent, Medium, Normal) with color coding
- Edit existing notes with automatic timestamp updates
- Delete notes with confirmation dialog
- View note details in a dedicated dialog
- Sorting by creation date (newest first)
- Splash screen for better user experience

## Screenshots 📸
| Splash Screen | Home Screen | Add/Edit Note | Note Details | Delete Confirmation |
|---------------|-------------|------------|------|---------|
| <img src="https://github.com/user-attachments/assets/bd351be0-4973-4408-96f9-7cab1d3da4ad" width="200"> | <img src="https://github.com/user-attachments/assets/15671999-1212-4af0-bb0b-073926d0343f" width="200"> | <img src="https://github.com/user-attachments/assets/e1f01d63-af02-46d4-83d7-d1bd040e7ff9" width="200"> | <img src="https://github.com/user-attachments/assets/4f57b4a4-3bba-445b-a66e-f85210e08cbf" width="200"> | <img src="https://github.com/user-attachments/assets/09a0348a-6d78-433a-a123-a84d8259a784" width="200"> |






## Demo Video 🎥

https://github.com/user-attachments/assets/f635b7af-caa7-4a20-9c31-09c9c340aa04

## Technologies Used 🛠️

- **Kotlin** - Primary programming language
- **SQLite Database** - Local data persistence
- **RecyclerView** - Efficient note listing
- **ViewModel** - UI-related data management
- **AlertDialog** - User confirmation dialogs
- **SimpleDateFormat** - Date formatting
- **Material Design** - Clean user interface
  
## Key Components 🏗️

- **AddNoteActivity.kt** - Handles note creation and editing
- **DatabaseHelper.kt** - Manages SQLite database operations
- **MainActivity.kt** - Displays notes list and handles actions
- **Note.kt** - Data class for note structure
- **NoteAdapter.kt** - Custom adapter for note list

SplashScreen.kt - App introduction screen

## Installation ⚙️
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/note-app.git
