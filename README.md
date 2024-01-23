<h1 align="center">Reminder demo app</h1>

<p float="left">
  <img src="https://github.com/Feketerig/Reminder-demo-app/blob/master/images/Login_framed.png" height="400px">
  <img src="https://github.com/Feketerig/Reminder-demo-app/blob/master/images/Create%20a%20new%20Reminder_framed.png" height="400px">
  <img src="https://github.com/Feketerig/Reminder-demo-app/blob/master/images/Main%20menu_framed.png" height="400px">
  <img src="https://github.com/Feketerig/Reminder-demo-app/blob/master/images/Map_framed.png" height="400px">  
</p>



## About
Reminder demo app is a project to create a reminder app. 
The main purpose of this project is to demonstrate modern Android development skills in real production environment, reduce the learning curve when jump into Android development world.

**Features**
- Log in and synchronize data between multiple devices
- Create new reminders
  - With optional notification
  - With optional geolocation
  - With optional calendar event
  - With optional photo
- View reminders with location data on map

## Architecture

- [Use Case](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576)
- [Clean Architecture](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029)
- [MVVM Architecture](https://medium.com/swlh/understanding-mvvm-architecture-in-android-aa66f7e1a70b)


## Libraries
- [Material 3](https://m3.material.io/) - Material 3 design.
- [Coil](https://coil-kt.github.io/coil/) - Loading images.
- [Ktor](https://ktor.io/) - Construct the REST APIs and paging network data.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Google Maps for map view.
- Google Geofence Api
- Coroutines
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
