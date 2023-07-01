<h1 align="center">
  <br>
  <a href="https://github.com/TalMizrahii/HatchatMobileApp"><img src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/99ceecd9-213f-49a0-bc61-73f01389f566" alt="HTML" width="300"></a>
  <br>
  Hatchat Mobile
  <br>
</h1>

<h4 align="center">Hatchat is an Android chat application designed for online communication, developed as a project for the Advanced Programming 2 course at Bar-Ilan University.


<p align="center">
  <a href="#description">Description</a> •
  <a href="#implementation">Implementation</a> •
  <a href="#installing-and-executing">Installing And Executing</a> •
  <a href="#authors">Authors</a> 
</p>

## Description
  
This project is a web application developed using [Java](https://www.java.com/en/) and [nodeJS](https://nodejs.org/en).
 
The chat application is divided into 3 parts: the [Hatchat-MVC-server](https://github.com/TalMizrahii/MVC-HatChat-Server), implemented in Node.js and utilizing [mongoDB](https://www.mongodb.com/), the [Hatchat-web](https://github.com/TalMizrahii/AP2-Ex3) app implemented in [React](https://react.dev/), and this [Hatchat-Mobalie](https://github.com/TalMizrahii/HatchatMobileApp) [Android](https://www.android.com/) app, implemented in Java.
 
## The Application
  
### MainActivity

The MainActivity is the logging screen of the app. Here, the user can enter his credentials (username an password) to log in. If the user is not registerd, he can press the register button in the buttom of the screen to move to the register activity. 

<img width="250" alt="LoginScreen" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/43d45c4e-36ba-4d4f-84ee-b6072e91d710">




### RegisterScreenActivity
The rigister screen gives the user an option to sign up to the app. The register screen will process the request, check if all the fields were entered valid values, and will send a post request  

<img width="250" alt="RegistrationScreen" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/af347012-2d58-4e9d-9614-2a3eefa0c20f">

Valid password and Camera


<img width="250" alt="RegistrationScreen2" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/556c2b62-696f-4f68-9a2b-82b81335095f">
<img width="250" alt="RegistrationScreen3" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/8116223f-a06f-472f-9cf4-997b90bb72df">


### ContactListActivity

On this screen, users can view all their contacts with whom they have active conversations. The system displays the current data stored on the device, but in the background, an update [HTTP](https://en.wikipedia.org/wiki/HTTP) request is sent to the server to ensure the app's data is up to date. Additionally, the screen features an "Add" button, which directs users to the "Add Contact" activity where they can add a new contact to their list. Furthermore, a "Settings" button is available to navigate to the settings screen. To move to a chat screen with a contact, the user needs simply to press a contact from his list. 

<img width="250" alt="ContactList" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/3b244130-7c13-48d2-a556-4430626b4707">

### AddContactActivity
The AddContactActivity provides a screen where users can enter a new contact's information and add them to their contact list. The activity interacts with the ContactViewModel to handle the addition of contacts and utilizes data binding to access and update the UI components.

<img width="250" alt="AddContact" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/9a83fb94-8c31-4ef4-b8ba-ada6fc7ce4a7">
<img width="246" alt="ContactList2" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/21bdda47-bdaf-4fce-b6c6-81042475094a">


### ChatScreenActivity

The ChatScreenActivity represents the chat screen where users can exchange messages with a specific contact. This activity handles various functionalities related to messaging and displaying the conversation. This ChatScreenActivity provides the main interface for users to interact with a specific contact's chat conversation. It handles sending and displaying messages, updating the message list when changes occur, and retrieving data from the server to ensure data consistency.

<img width="200" alt="ChatScreen2" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/2cb8724a-0281-47e6-9cab-caba129d5170">
<img width="400" alt="web new message" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/e0a69176-8cf0-4cff-b508-e479e945289b">
<img width="200" alt="ChatScreen3" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/64617991-f7cb-47ef-a644-cf38c560682e">


### SettingsActivity

The SettingActivity provides a screen where users can adjust the application settings. It includes options for enabling/disabling dark mode, entering a new base URL, and a logout button. The activity interacts with the SettingsViewModal to handle the settings data and utilizes data binding to access and update the UI components. This activity is accessible from all other actvities.

<img width="232" alt="Settings" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/05092f75-dd2e-4893-b13e-8a2578d78735">
<img width="245" alt="settingsNight" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/66469708-b73f-4a24-9964-733bec082de8">
<img width="235" alt="ContactsNight" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/6e883b13-5906-4783-a6dc-f8f41a1fa344">


## Implementation
The app utilizes the power of [Android Room](https://developer.android.com/training/data-storage/room) , a robust data storage library, to efficiently store and retrieve large amounts of data on the user's device. This approach offers several advantages, such as seamlessly loading the current data from the Room Database into the UI components. Additionally, the app leverages the asynchronous nature of Room to make HTTP requests to the Hatachat MVC server, ensuring that the data remains up-to-date. The app leverages  [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData?hl=en), an Android Architecture Component, for real-time updates of the UI components, including a RecyclerView for messages and a ListView for the contact list. LiveData allows the UI to automatically reflect changes in the underlying data sources without manual intervention.

The system utilizes the ViewModel and Repository design pattern commonly used in Android apps. This design separates data handling from activities, allowing the ViewModel to act as a data API. The ViewModel relies on the Repository to handle data processing and interactions with the server and Room database, promoting code organization and testability.

<img width="320" alt="dataTransfer" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/37168635-4f3a-49c7-8378-ad5f2447aadb">


To enhance real-time data updates, the app integrates [Firebase](https://firebase.google.com/) services. This integration enables the app to instantly receive messages from the server and process them, providing users with a dynamic and responsive messaging experience. By leveraging the server-side capabilities of the Hatchat application, messages originating from the web app are intelligently routed to the appropriate destination device, specifically the Hatchat Mobile app's Firebase service. Using Firebase allows us to create push notifications when a new message arrive.

<img width="233" alt="notifications" src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/637b19e7-52b6-4128-9394-fd8dbed1ba8f">


This combination of Android Room for efficient data storage and retrieval, along with Firebase for real-time messaging, enhances the overall functionality and responsiveness of the Hatchat mobile application. It's important to note that the server enforces a single device login policy, preventing simultaneous logins from multiple devices (web or mobile). In the event a user attempts to login from two devices, they are prompted to refresh the app to ensure a consistent and secure user experience.

## Installing And Executing
  
To clone and run this application, you'll need [Git](https://git-scm.com) installed on your computer. From your command line:

```bash
# Clone this repository.
$ git clone https://github.com/TalMizrahii/HatchatMobileApp


# Go into the repository.
$ cd HatchatMobileApp
  
# Here, you can install Android Studio and run the app on an emulator, or alternatively, you can run the app by connecting a physical device to the IDE.
```
## Authors
* [@Yuval Arbel](https://github.com/YuvalArbel1)
* [@Tal Mizrahi](https://github.com/TalMizrahii)
