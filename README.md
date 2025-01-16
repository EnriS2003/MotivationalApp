## Elevate project - motivational app

## Description
Elevate is a mobile application designed to empower users in organizing their thoughts and managing their daily lives. Life can often feel overwhelming, and Elevate provides a sanctuary to inspire users with carefully curated motivational quotes, help them track their achievements, and offer a personal space to reflect and grow.

## Key Highlights
 -  Inspiration Through Quotes
 
Explore a vast collection of motivational quotes, designed to uplift and energize. Save your favorites to revisit whenever you need a boost of positivity.

  - Personal Diary
  
A private and secure space for journaling your thoughts, experiences, and reflections. The intuitive interface ensures a seamless writing experience.

 - Achieve Your Goals
 
Use the Task Manager to define, track, and complete your goals. Set deadlines and reminders to ensure you stay on top of your priorities.

  - Seamless Design and Functionality
  
The app features a modern, user-friendly interface that adapts to your needs. Whether you’re planning your day, reflecting on your week, or seeking a moment of inspiration, Elevate is here for you.

  - Offline Support
 
Access your saved quotes, diaries, and tasks even when you’re offline, ensuring you’re never disconnected from your personal growth.

## Project Architecture

This project is structured using the MVVM (Model-View-ViewModel) architecture to ensure clear separation of concerns and maintainable code. The project files are organized into distinct folders for better modularity and scalability. 
The viewmodel folder contains the various ViewModel classes that handle business logic and interact with the corresponding views. These include:
	- DiariesViewModel: Manages the business logic and state for the Diaries section of the app. It handles data retrieval, updates, and communication with the repository for diary-related operations.
	- QuoteViewModel: Manages the logic behind the Quotes feature, including fetching random quotes, managing favorites, and handling user interactions.
	- TaskViewModel: Responsible for the logic related to task management, including adding, updating, deleting, and organizing user tasks.
	- ThemeViewModel: Maintains and updates the application’s theme state, enabling dynamic theme changes based on user preferences.

Each ViewModel is purpose-built to handle the specific logic for its corresponding feature, ensuring modularity and a clear separation between the UI and the underlying data logic.

The 'screens' folder contains all the screen's file. These file contain the layout of each screen. These are:
- BottomBar & NavigationRail: This file defines a responsive navigation system for the app that adapts based on the device's orientation. It is defined the logic to automatically display a bottom bar and a navigation rail. It displays a BottomBar in portrait mode. Otherwise it displays the NavigationRail in landscape mode. Each navigation item uses the NavController to navigate between screens.

- DiariesScreen & DiaryDetailScreen & AddDiaryScreen: this file displays the list of diaries saved by the user. Allows users to view, edit, and delete existing diaries or navigate to add a new one. Another composable funciton "DiaryDetailScreen" is defined to display the details of every single diary entity, allowing the user to view or update its content. In this other the screen, it is possible to update the content. AddDiaryScreen is another composable function defined for adding a new Diary. This diary is then added to the database.
  
- HomeScreen & FavoriteScreen: in this Composable function, the main screen of the application is represented. Here a random quote is fetched and displayed from the API. While fetching a quote a loading indicator is displayed. An error message is shown on the quote fetch fails.in this case, it shows a saved quote from the database (if available) as a fallback. An icon shows that the quote is one of the favorites one. Therefore, this quote is also shown in the Favourites screen. In the home screen it is possible to save or remove a quote from the database. In the favourite screen it is responsible to see the saved quotes and remove them.

- SettingsScreen: this Composable function displays a setting to permit the user to change the app's theme. The information of the version and the death Looper of the app as long as the copyright are written here.

- TasksScreen & TaskItem & AddTaskScreen: similarly to the diaries screen, this file defines the composable functions that handle the user interface and the logic for managing tasks in the application. Task screen is the main screen displaying the list of tasks.it allows the user to add new tasks, view existing ones, and filter completed task. TaskItem is a Composable component that represents an individual task, along with its associated actions, such as marking as completed or deleting. AddTaskScreen is a screen for adding new tasks, including fields for the task, title and deadline. This fields are validated, and when the task is added is then saved in the database and shown in the task screen.

## Features Overview

Motivational Quotes
	- Random Quotes: Fetch a new motivational quote each time the user opens the app or presses a refresh button.
	- Favorites: Save motivational quotes to the database. These favorite quotes are shown in a dedicated section for future reference. It is possible to remove each of them at user's choice.
	- Offline Support: Display saved quotes from the local database when the app is offline or API fetching is not working.

Diaries
	- Diary Management: Add, edit, and delete diary entries. Each diary entry includes a title, content, and timestamp.
	- Detailed View: View and edit the content of individual diary entries.
	- Persistent Storage: All diary entries are saved locally in a database.

Tasks
	- Task Tracking: Add tasks with titles and deadlines. Mark tasks as completed or delete them.
	- Deadline Highlighting: Visually distinguish tasks with upcoming deadlines or overdue tasks.
	- Filtering: Filter tasks by completion status.

Theme Management
	- Dynamic Themes: Allow users to choose between light, dark, or system default themes.
	- User Preferences: Persist theme preferences across app sessions.


## Badges
[![CI](https://github.com/EnriS2003/MusicPlayerApp/actions/workflows/blank.yml/badge.svg)](https://github.com/EnriS2003/MusicPlayerApp/actions/workflows/blank.yml)
Version 1.0.0

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation
To work on the project, or run the app you would need **Andorid Studio Koala Feature Drop | 2024.1.2** or a recent version.

These are the information of the system used to develope the app:
**Build Version: AI-241.18034.62.2412.12266719
Runtime Version: OpenJDK 17.0.11
Sistema: Aarch64 architecture (Make sure your system supports the correct version of Android Studio).

You can download the latest version of Android Studio [here](https://developer.android.com/studio).

## Usage

Home Screen
	- Fetches random motivational quotes from an API.
	- Save quotes to your favorites or remove them as desired.
 - Move to the other pages.

Tasks
	- 	Add tasks with a title and deadline.
	- 	Mark tasks as complete or delete them.
	- 	Filter completed and expired tasks.
 -  Move to the other pages.

Diary
	- 	Create diary entries with titles and detailed descriptions.
	- 	Edit or delete existing entries.
 -  Move to the other pages.

Favorites
	- Access all saved quotes.
	- Remove quotes from favorites.

## Support
If you encounter any issues, feel free to:
	•	Open an issue on the GitLab repository.
	•	Contact me directly at: enrisulejmani@icloud.com 
 
## Roadmap
Upcoming Features:
	1.	Push notifications for daily motivational quotes.
	2.	Cloud synchronization for tasks and diaries.
	3.	Enhanced filtering and search for saved quotes.

## Contributing
Contributions are welcome! Please follow these steps:
	1.	Fork the repository.
	2.	Create a new branch (feature/your-feature-name).
	3.	Commit your changes with detailed messages.
	4.	Push the branch and open a merge request.

## Authors and acknowledgment
Enri Sulejmani - Developer & Maintainer.

## License
This project is licensed under the UniBz License.

## Project status
Currently the develpement has been stopped. New features and enhancements could be added in the future.
