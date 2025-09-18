# 📚 My Books - Reading Progress Tracker

A beautiful and intuitive Android app for tracking your reading progress, built with modern Material Design principles.

![App Screenshot](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![API](https://img.shields.io/badge/API-21%2B-orange.svg)

## Features

### Core Functionality
- **Bookmark Management**: Add, edit, delete, and organize bookmarks
- **Search & Filter**: Powerful search functionality with category-based filtering
- **Categories**: Organize bookmarks into Work, Personal, and custom categories
- **Recent & Favorites**: Quick access to recently added and favorite bookmarks
- **Clean UI**: Modern Material Design interface with day/night theme support

### User Interface
- **Material Design 3**: Modern, clean, and intuitive interface
- **Search Bar**: Quick search functionality with real-time filtering
- **Category Chips**: Easy filtering by categories (All, Recent, Favorites, Work, Personal)
- **Card-based Layout**: Clean bookmark cards with favicon, title, URL, and description
- **Floating Action Button**: Quick add bookmark functionality
- **Empty State**: Helpful guidance when no bookmarks are present

### Technical Features
- **Kotlin**: Written in modern Kotlin for Android
- **RecyclerView**: Efficient list rendering for large bookmark collections
- **Material Components**: Uses Google's Material Design Components
- **Responsive Design**: Optimized for different screen sizes
- **Dark Theme**: Automatic dark theme support

## Project Structure

```
BookmarkApp/
├── app/
│   ├── build.gradle                    # App-level build configuration
│   └── src/main/
│       ├── AndroidManifest.xml         # App manifest
│       ├── java/com/bookmark/app/
│       │   ├── MainActivity.kt         # Main activity with bookmark list
│       │   ├── Bookmark.kt            # Bookmark data model
│       │   └── BookmarkAdapter.kt     # RecyclerView adapter
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml   # Main activity layout
│           │   └── item_bookmark.xml   # Bookmark item layout
│           ├── values/
│           │   ├── colors.xml         # Color definitions
│           │   ├── strings.xml        # String resources
│           │   └── themes.xml         # Light theme
│           ├── values-night/
│           │   └── themes.xml         # Dark theme
│           └── drawable/
│               └── circle_background.xml # Circular icon background
├── build.gradle                       # Project-level build file
├── gradle.properties                  # Gradle properties
└── settings.gradle                    # Project settings
```

## Installation & Setup

### Prerequisites
- Android Studio (latest version recommended)
- Android SDK (API level 21 or higher)
- Kotlin plugin

### Building the App
1. Open Android Studio
2. Click "Open an existing project"
3. Navigate to the `BookmarkApp` folder and select it
4. Wait for Gradle sync to complete
5. Click "Run" or press Ctrl+F5 to build and run the app

### Dependencies
The app uses the following key dependencies:
- **AndroidX Core**: Core Android libraries
- **Material Components**: Google's Material Design components
- **RecyclerView**: For efficient list display
- **CardView**: For bookmark card layout
- **ConstraintLayout**: For flexible layouts

## Usage

### Adding Bookmarks
1. Tap the floating action button (+) to add a new bookmark
2. Enter the bookmark details (title, URL, description, category)
3. Save to add to your collection

### Searching & Filtering
- Use the search bar to find bookmarks by title, URL, or description
- Tap category chips to filter by type (All, Recent, Favorites, Work, Personal)
- Combine search and filters for precise results

### Managing Bookmarks
- Tap any bookmark to open it in your browser
- Tap the menu button (⋮) on bookmark cards for additional options
- Long press for bulk selection and management

## Customization

### Adding New Categories
To add new bookmark categories:
1. Update the category chips in `activity_main.xml`
2. Add corresponding filter logic in `MainActivity.kt`
3. Update the adapter to handle new category colors/icons

### Theming
The app supports light and dark themes automatically based on system settings. To customize:
- Edit colors in `res/values/colors.xml`
- Modify themes in `res/values/themes.xml` and `res/values-night/themes.xml`

## Technical Details

### Architecture
- **MVVM Pattern**: Model-View-ViewModel architecture
- **Single Activity**: Modern single-activity architecture
- **Material Design**: Following Google's Material Design guidelines

### Performance Optimizations
- **RecyclerView**: Efficient list rendering with view recycling
- **Data Filtering**: In-memory filtering for fast search results
- **Lazy Loading**: Ready for future database integration

## Future Enhancements

### Planned Features
- **Database Storage**: SQLite/Room database for persistent storage
- **Cloud Sync**: Backup and sync across devices
- **Import/Export**: Import from browsers, export to various formats
- **Tags System**: Advanced tagging for better organization
- **Web Preview**: In-app web preview without leaving the app
- **Sharing**: Share bookmarks with other apps and users

### Technical Improvements
- **Repository Pattern**: Data layer abstraction
- **Dependency Injection**: Hilt/Dagger for dependency management
- **Testing**: Unit and UI tests
- **CI/CD**: Automated build and deployment pipeline

## Contributing

Feel free to contribute to this project by:
1. Reporting bugs and issues
2. Suggesting new features
3. Submitting pull requests
4. Improving documentation

## License

This project is open source and available under the MIT License.

## Contact

For questions or support, please open an issue in the project repository.