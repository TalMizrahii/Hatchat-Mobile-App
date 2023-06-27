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
 
## The Application
  
### MainActivity

The MainActivity is the logging screen of the app. Here, the user can enter his credentials (username an password) to log in. If the user is not registerd, he can press the register button in the buttom of the screen to move to the register activity. 

  
### RegisterScreenActivity
The rigister screen gives the user an option to sign up to the app. The register screen will process the request, check if all the fields were entered valid values, and will send a post request  


### ContactListActivity

  
### ChatScreenActivity

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
