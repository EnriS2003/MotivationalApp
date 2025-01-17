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

The main files are MainActivity, ElevateApplication, and AppNavHost. These files serve as the backbone of the application, coordinating various components and ensuring seamless operation.

- MainActivity acts as the entry point of the application, setting up the Compose environment and applying the app's theme. It initializes the navigation graph and provides access to the app's ViewModels, including DiariesViewModel, TaskViewModel, QuoteViewModel, and ThemeViewModel. This activity ensures a smooth user experience by integrating navigation and theming functionality.

- ElevateApplication is a custom Application class responsible for initializing global resources such as the database and repositories. It adheres to the Singleton pattern, ensuring that a single instance of critical resources, such as DiaryDatabase and repositories for diaries, tasks, and quotes, is shared across the application. This centralized initialization simplifies dependency management and ensures consistency throughout the app.

- AppNavHost defines the navigation graph for the app, detailing how users transition between screens such as Home, Diaries, Tasks, and Favorites. It integrates seamlessly with the navigation controller and ViewModels to provide a cohesive and user-friendly navigation experience.
  
These files collectively implement the core functionality of the app, leveraging the MVVM architecture to separate concerns and maintain clean, modular code. Together, they ensure the app is well-structured, scalable, and easy to maintain.


Another important components of the MVVM architecture are the viewmodels. These are contained in the viewmodel folder. These classes that handle business logic and interact with the corresponding views are:
- DiariesViewModel: Manages the business logic and state for the Diaries section of the app. It handles data retrieval, updates, and communication with the repository for diary-related operations.
- QuoteViewModel: Manages the logic behind the Quotes feature, including fetching random quotes, managing favorites, and handling user interactions.
- TaskViewModel: Responsible for the logic related to task management, including adding, updating, deleting, and organizing user tasks.
- ThemeViewModel: Maintains and updates the application’s theme state, enabling dynamic theme changes based on user preferences.

Each ViewModel is purpose-built to handle the specific logic for its corresponding feature, ensuring modularity and a clear separation between the UI and the underlying data logic.


The 'screens' package contains all the screen's file. These file contain the layout definition of each screen. These are:
- BottomBar & NavigationRail: This file defines a responsive navigation system for the app that adapts based on the device's orientation. It is defined the logic to automatically display a bottom bar and a navigation rail. It displays a BottomBar in portrait mode. Otherwise it displays the NavigationRail in landscape mode. Each navigation item uses the NavController to navigate between screens.

- DiariesScreen & DiaryDetailScreen & AddDiaryScreen: this file displays the list of diaries saved by the user. Allows users to view, edit, and delete existing diaries or navigate to add a new one. Another composable funciton "DiaryDetailScreen" is defined to display the details of every single diary entity, allowing the user to view or update its content. In this other the screen, it is possible to update the content. AddDiaryScreen is another composable function defined for adding a new Diary. This diary is then added to the database.
  
- HomeScreen & FavoriteScreen: in this Composable function, the main screen of the application is represented. Here a random quote is fetched and displayed from the API. While fetching a quote a loading indicator is displayed. An error message is shown on the quote fetch fails.in this case, it shows a saved quote from the database (if available) as a fallback. An icon shows that the quote is one of the favorites one. Therefore, this quote is also shown in the Favourites screen. In the home screen it is possible to save or remove a quote from the database. In the favourite screen it is responsible to see the saved quotes and remove them.

- SettingsScreen: this Composable function displays a setting to permit the user to change the app's theme. The information of the version and the death Looper of the app as long as the copyright are written here.

- TasksScreen & TaskItem & AddTaskScreen: similarly to the diaries screen, this file defines the composable functions that handle the user interface and the logic for managing tasks in the application. Task screen is the main screen displaying the list of tasks.it allows the user to add new tasks, view existing ones, and filter completed task. TaskItem is a Composable component that represents an individual task, along with its associated actions, such as marking as completed or deleting. AddTaskScreen is a screen for adding new tasks, including fields for the task, title and deadline. This fields are validated, and when the task is added is then saved in the database and shown in the task screen.
This file separation ensures modularity for such architecture, making the code easy to understand, scale and debug.

The network package in this project is responsible for managing API interactions and handling network requests. It contains the following key files:
1.	RetrofitInstance.kt:
	- This file sets up the Retrofit instance used for making network requests.
	- It includes the base URL for the API and provides the configuration for the HTTP client.
	- A singleton object ensures that the Retrofit instance is reused throughout the application, optimizing performance and resource usage.

2.	ZenQuotesApi.kt:
	- Defines the interface for interacting with the ZenQuotes API.
	- Contains method definitions annotated with Retrofit’s annotations like @GET, specifying endpoints for retrieving data.
	- Ensures a clean abstraction for API calls, making the logic easy to maintain and test.
 
3.	ZenQuotesResponse.kt:
	- Represents the data model for responses received from the ZenQuotes API.
	- The model uses annotations like @SerializedName to map JSON fields to Kotlin data class properties.
	- Ensures seamless deserialization of API responses into structured objects that can be used within the application.

By separating these responsibilities into distinct files, the network package maintains a clean and modular architecture, making the app easier to scale and debug.


Data management.

In the data package the database and related entity tables with respective DAO, repository and eventual local state files are defined. AppDB
is the file that defines the Room database configuration for the application. It integrates the different DAOs (TaskDAO, among others) and defines the database schema. The file is responsible for setting up the database and managing migrations if the database schema changes in future updates.
Inside the data package other packages are defined for structural purposes. These packages are:
1. DiaryData
   - DiaryDAO
This file defines the Data Access Object (DAO) interface responsible for database operations related to diaries. It provides methods for retriveing all the diaries in the database, adding new diaries, deleting a diary entity, get a single diary by id, and update a diary attributes.

   - DiaryEntity
This file defines the database entry for diaries. It maps the diary_table in the database to a Kotlin data class, containing fields such as the title, content, and creation date, with Room annotations to manage table creation and data persitence.
  
   - DiaryRepository
This file implements the repository layer for quotes, acting as a bridge between the database and the application's business logic. It manages operations such as retrieving all saved diaries, adding a new diary, deleting a diary entity, get a diary by its ID, and updating a diary informations (that is the diary attributes). 
     
3. QuoteData
   - QuoteDAO
This file defines the Data Access Object (DAO) interface responsible for database operations related to quotes. It provides methods for retrieving all quotes from the database, adding new quotes, deleting specific quotes, and checking if a particular quote exists.

   - QuoteEntity
This file represents the database entity for quotes. It maps the quotes_table in the database to a Kotlin data class, containing fields such as id, quote, author, and date, with Room annotations to manage table creation and data persistence.
  
   - QuoteRepository
This file implements the repository layer for quotes, acting as a bridge between the database and the application's business logic. It manages operations such as retrieving all saved quotes, adding new quotes with timestamps, checking if a quote is saved, and removing quotes from the database.
  
   - QuoteState
This file defines a data class that models the state of the quotes-related UI. It includes properties for the current quote and author, a loading status indicator, and an optional field for handling error messages.

5. tasksData
   - TaskDao:
This file defines the Data Access Object (DAO) for tasks. It provides the methods to interact with the database for CRUD (Create, Read, Update, Delete) operations related to tasks. These include fetching all tasks, inserting new tasks, updating existing tasks, and deleting tasks. It acts as the interface between the app’s logic and the database.

   - TaskEntity:
This file defines the TaskEntity, which represents the data model for tasks stored in the database. Each task contains fields like id, title, deadline, and isCompleted, which correspond to the columns in the tasks table in the database. It serves as the blueprint for storing task data.

   - TaskRepository:
This file implements the repository pattern for tasks. It acts as a single source of truth for all task-related data, abstracting the logic of data fetching and manipulation from the ViewModel. The repository interacts with the TaskDAO to perform database operations.
  
   - TaskState:
This file defines the state model for tasks. It represents the UI state for task-related components, encapsulating properties like the current list of tasks, any loading state, or error messages. It ensures a clean separation between the UI and the logic.


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
