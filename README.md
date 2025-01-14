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

This project follows a MVVM (Model-view-viewmodel) Architecture. The project's file are divided in different folders. In the viewmodel folder it is possible to find the different viewmodels of the application. These are:
- DiariesViewModel
- QuoteViewModel
- TaskViewModel
- ThemeViewModel

For each file there is business logic for each screen they are related to. In the DiariwsViewModel the code and logic for the Diaries section is defined. In the QuoteViewModel the code defining the logic behind the Quotes is defined. So it is for the code and logic in the TaskViewModel related to the tasks feature. In the ThemViewModel the code and logic for mantainign and updating the application's theme is defined.


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
