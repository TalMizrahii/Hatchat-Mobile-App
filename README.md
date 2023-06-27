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
 
The chat application is divided into two parts: the [server-side](https://github.com/TalMizrahii/MVC-HatChat-Server), implemented in Node.js and utilizing [mongoDB](https://www.mongodb.com/), and the client-side, implemented using Java, can communicate also with the [Hatchat-Web](https://github.com/TalMizrahii/MVC-HatChat-Server) application, written in React.
 
  ## The Client-Side
  
### Client-side: Login and Registration
The client-side of the application starts with the user browsing to the client's index page, which serves as the login screen for the app.
  
  <img width="400" alt="rm1" src="https://user-images.githubusercontent.com/103560553/234780293-e618d743-7ac0-4805-a298-e5d668767660.PNG">


  If the user is not registered, they can click on the "Register" button at the bottom of the screen to access the registration page. The registration screen includes a form for new users to fill out. Styling is done using [Bootstrap](https://getbootstrap.com/), ensuring a responsive design across different devices. The registration form includes input fields for username, password, display name, and profile picture. Similar to the login screen, the fields are validated, and appropriate visual feedback is provided.
  
<img width="400" alt="rm2" src="https://user-images.githubusercontent.com/103560553/234783338-8691a917-fed8-44a5-86be-e5060ddf356e.PNG">
  <img width="200" alt="1" src="https://github.com/TalMizrahii/AP2-EX2/assets/103560553/2b496ddb-4061-445f-a5ac-c1a8ae1aac41">
<img width="200" alt="2" src="https://github.com/TalMizrahii/AP2-EX2/assets/103560553/448fbd76-beae-4599-b1db-2d4ec3738959">

  
### Chat Screen
The chat screen consists of two main parts. Firstly, there is a list of recent conversations displaying the participants' profile pictures, along with a timestamp of the last message sent in each conversation. If the user has previous conversations, they will be presented in this list.

<img width="400" alt="Capture" src="https://github.com/TalMizrahii/AP2-EX2/assets/103560553/46412cf8-22df-466d-a163-9b432efbdfa9">

### Updating Contact List
When adding a new user, the app updates the contact list by placing the contact who sent the most recent message at the top. The timestamp of the last message is set, and a snippet of it is displayed within the contact entry in the list.
  
<img width="200" alt="3 1" src="https://github.com/TalMizrahii/AP2-EX2/assets/103560553/087ba499-acf6-459c-bd0e-1774ca6e6b83">
<img width="200" alt="4" src="https://github.com/TalMizrahii/AP2-EX2/assets/103560553/433bcdb9-037c-437d-9306-6bbab6f07885">
  
*Please note that a user cannot be simultaneously logged in with multiple tabs. Therefore, if you wish to establish a new connection, it may be necessary to refresh your page.*
  
  
## The Server-Side

The server-side of the application is responsible for handling the backend logic and communication with the database. It utilizes the following technologies and frameworks:

* Node.js - The server-side is implemented using Node.js, a JavaScript runtime built on Chrome's V8 JavaScript engine.
  
* Express.js - Express.js is used as the web application framework for handling HTTP requests and setting up API routes.
  
* Socket.IO - Socket.IO is integrated to enable real-time communication between the server and clients.
  
* MongoDB - The server connects to a MongoDB database to store and retrieve data.
  
* Mongoose - Mongoose is used as an Object Data Modeling (ODM) library for MongoDB, providing a simplified interface for interacting with the database.

<img width="400" alt="1" src="https://github.com/TalMizrahii/AP2-Ex3/assets/103560553/cf8f79b6-f0b0-4b9d-89cf-7e8bc1a6c323">


In the provided code snippet, the server-side code is structured using the Model-View-Controller (MVC) design pattern. The express module is used to create an instance of the Express app, which handles incoming HTTP requests. The server also establishes a Socket.IO connection to enable real-time communication.

The code connects to a MongoDB database using the mongoose library, with the connection string and port specified in environment variables. API routes for users, tokens, and chats are set up using the respective routers (users, authenticator, and chat). CORS is enabled to allow requests from the specified origin

 
## Installing And Executing
  
To clone and run this application, you'll need [Git](https://git-scm.com) installed on your computer. From your command line:

```bash
# Clone this repository.
$ git clone https://github.com/TalMizrahii/AP2-Ex3

# Go into the repository.
$ cd AP2-Ex3
  
# Go into the project folder.
$ cd hatchat_server
  
# Install the needed libraries.
$ npm install
  
# Start the program in your default browser.
$ npm start
```
After that, you can browse to http://localhost:5000/ to enter the app.
## Authors
* [@Yuval Arbel](https://github.com/YuvalArbel1)
* [@Tal Mizrahi](https://github.com/TalMizrahii)
