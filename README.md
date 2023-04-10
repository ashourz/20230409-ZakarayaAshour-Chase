# 20230409-ZakarayaAshour-Chase
Coding Challenge: Weather

# Project Assignment
- Create a browser or native-app-based application to serve as a basic weather app.
- Search Screen
  - Allow customers to enter a US city
  - Call the openweathermap.org API and display the information you think a user would be interested in seeing. Be sure to have the app download and display a weather icon.
  - Have image cache if needed
- Auto-load the last city searched upon app launch.
- Ask the User for location access, If the User gives permission to access the location, then retrieve weather data by default

# Key Libraries
- **Java**
  - RxJava
- **Kotlin**
  - Kotlin Coroutines 
- **Android Jetpack**
  - Compose (MaterialDesign 2)
  - Lifecycle
  - Navigation
  - Room
- **Dependency Injection**
  - Hilt
- **REST API**
  - Retrofit 2
  - OkHttp
  - FasterXML Jackson
- **Unit and Integration Testing**
  - Compose UI Test
  - Espresso
  - HiltAndroidTest
  - JUnit4

# Key Features
  - Current Weather Location Screen
  - 5 Day/3 Hour Weather Forecast Screen
  - Drop Down City Search to pull weather data for specified city
  - Current Location Button to pull weather data for devices current location
  - Bottom Navigation Bar to toggle between screens
  - Lazy List with sticky date header to show forecasted weather information
  - Smooth and aesthetic portrait and landscape configuration
  - Auto search for current locations weather data if permissions are granted
  - Permission Request Intent launched on pressing Current Location Button if Coarse Location permission have not been granted
  - Permission rationale dialog box explains to the user why location data is required when permissions are denied
  - LruCache Image Cache to store Weather Condition Icon.

# Screenshots
<p float="left">
<img src="(https://user-images.githubusercontent.com/39238415/230876683-1c12a77e-144b-44ab-adc8-e13aba768ec4.png" width="160" />  
<img src="https://user-images.githubusercontent.com/39238415/230876727-661598ba-b719-48f4-924b-2caedd070917.png" width="160" />
<img src="https://user-images.githubusercontent.com/39238415/230876746-88d26d4a-6091-4ab9-8a36-6795abd5aa23.png" width="160" />
<img src="https://user-images.githubusercontent.com/39238415/230876761-48a27e8a-de15-419a-b8ab-2e2096cf4c94.png" width="160" />
<img src="https://user-images.githubusercontent.com/39238415/230876777-4f70c77a-3ebc-4893-b906-3ea7362496e4.png" width="160" />
</p>
<p float="left">
 <img src="https://user-images.githubusercontent.com/39238415/230876907-62cb5c3d-9390-4552-aa61-a0faea854ca7.png" height="160" />
 <img src="https://user-images.githubusercontent.com/39238415/230876917-fcffd5e7-233c-4087-b9a6-c48adc719836.png" height="160" />
</p>


