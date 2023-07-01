<h1 align="center">
  <br>
  <a href="https://github.com/TalMizrahii/AP2-Ex1"><img src="https://github.com/TalMizrahii/HatchatMobileApp/assets/103560553/a718fa2e-2d6b-4feb-8d91-9304eea35d45" alt="HTML" width="300"></a>
  <br>
  Advanced-Programming-2
  <br>
</h1>

<h4 align="center">Hatchat is an Android chat application designed for online communication, developed as a project for the Advanced Programming course at Bar-Ilan University.


<p align="center">
  <a href="#description">Description</a> •
  <a href="#implementation">Implementation</a> •
  <a href="#installing-and-executing">Installing And Executing</a> •
  <a href="#authors">Authors</a> 
</p>

## Description
  
*The final project seats in the "main" branch!*
  
This project is a web application developed using [Java](https://www.java.com/en/) and [nodeJS](https://nodejs.org/en).
 
The chat application is divided into 3 parts: the [Hatchat-MVC-server](https://github.com/TalMizrahii/MVC-HatChat-Server), implemented in Node.js and utilizing [mongoDB](https://www.mongodb.com/), the [Hatchat-web](https://github.com/TalMizrahii/AP2-Ex3) app implemented in [React](https://react.dev/), and this [Hatchat-Mobalie](https://github.com/TalMizrahii/HatchatMobileApp) [Android](https://www.android.com/) app, implemented in Java.
 
## The Application
  
### MainActivity

The MainActivity is the logging screen of the app. Here, the user can enter his credentials (username an password) to log in. If the user is not registerd, he can press the register button in the buttom of the screen to move to the register activity. 

  
### RegisterScreenActivity
The rigister screen gives the user an option to sign up to the app. The register screen will process the request, check if all the fields were entered valid values, and will send a post request  


### ContactListActivity

On this screen, users can view all their contacts with whom they have active conversations. The system displays the current data stored on the device, but in the background, an update [HTTP](https://en.wikipedia.org/wiki/HTTP) request is sent to the server to ensure the app's data is up to date. Additionally, the screen features an "Add" button, which directs users to the "Add Contact" activity where they can add a new contact to their list. Furthermore, a "Settings" button is available to navigate to the settings screen. To move to a chat screen with a contact, the user needs simply to press a contact from his list. 

### ChatScreenActivity

The ChatScreenActivity represents the chat screen where users can exchange messages with a specific contact. This activity handles various functionalities related to messaging and displaying the conversation. This ChatScreenActivity provides the main interface for users to interact with a specific contact's chat conversation. It handles sending and displaying messages, updating the message list when changes occur, and retrieving data from the server to ensure data consistency.

### AddContactActivity
The AddContactActivity provides a screen where users can enter a new contact's information and add them to their contact list. The activity interacts with the ContactViewModel to handle the addition of contacts and utilizes data binding to access and update the UI components.

### SettingsActivity

The SettingActivity provides a screen where users can adjust the application settings. It includes options for enabling/disabling dark mode, entering a new base URL, and a logout button. The activity interacts with the SettingsViewModal to handle the settings data and utilizes data binding to access and update the UI components.

## Implementation
The app utilizes the power of [Android Room](https://developer.android.com/training/data-storage/room) , a robust data storage library, to efficiently store and retrieve large amounts of data on the user's device. This approach offers several advantages, such as seamlessly loading the current data from the Room Database into the UI components. Additionally, the app leverages the asynchronous nature of Room to make HTTP requests to the Hatachat MVC server, ensuring that the data remains up-to-date. The app uses [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData?hl=en) for real-time apdate of the UI components.

To enhance real-time data updates, the app integrates [Firebase](https://firebase.google.com/) services. This integration enables the app to instantly receive messages from the server and process them, providing users with a dynamic and responsive messaging experience. By leveraging the server-side capabilities of the Hatchat application, messages originating from the web app are intelligently routed to the appropriate destination device, specifically the Hatchat Mobile app's Firebase service. It's important to note that the server enforces a single device login policy, preventing simultaneous logins from multiple devices (web or mobile). In the event a user attempts to login from two devices, they are prompted to refresh the app to ensure a consistent and secure user experience.

This combination of Android Room for efficient data storage and retrieval, along with Firebase for real-time messaging, enhances the overall functionality and responsiveness of the Hatchat mobile application.

  

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
