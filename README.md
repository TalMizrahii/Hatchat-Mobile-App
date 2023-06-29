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

 
## Installing And Executing
  
To clone and run this application, you'll need [Git](https://git-scm.com) installed on your computer. From your command line:

```bash
# Clone this repository.
$ git clone https://github.com/TalMizrahii/HatchatMobileApp


# Go into the repository.
$ cd HatchatMobileApp
  
# Here, you can install Android Studio and run the app on an emulator, or alternatively, you can run the app by connecting a physical device to the IDE.

## Authors
* [@Yuval Arbel](https://github.com/YuvalArbel1)
* [@Tal Mizrahi](https://github.com/TalMizrahii)
