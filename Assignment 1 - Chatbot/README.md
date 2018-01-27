# Chatbot
->  This chatbot is called "Amey" (after my lazy colleague). He is capable of answering all kinds of basic questions.  
->  He can search through the internet for questions starting with "Who, when, where, how".  
->  For questions starting with "What",he assumes that the question is about the current temperature in a city whose name is the last word in the question.  
  e.g. "What is the temperature in Hyderabad?" will give you the current temperature in Hyderabad.  
  e.g. "What Hyderabad?" will also give you the current temperature in Hyderabad.  
->  When the user types "exit", the chat window closes and a log file for the current conversation is automatically created in the "amey/logs" folder.  

## GUI

1.  This is the first window that appears and prompts the user to enter a username.    
![image](https://user-images.githubusercontent.com/18630586/35207079-f69e7c3a-ff66-11e7-8acc-d81f392fc373.png)

2.  After the user enters the username, the chat window opens and the user is greeted with a welcome message.    
![image](https://user-images.githubusercontent.com/18630586/35207125-43c847fc-ff67-11e7-86a7-2ffcb1472103.png)

3.  The user can then chat with Amey!    
![image](https://user-images.githubusercontent.com/18630586/35207235-daf81d3c-ff67-11e7-9f9d-7dfcf6afa233.png)

4.  If the user enters the command "exit", then the application closes and a log file is created in "Assignment 1 - Chatbot/bots/amey/logs" folder.    
![image](https://user-images.githubusercontent.com/18630586/35207341-6539fd08-ff68-11e7-8ee5-4d4289067b17.png)

## Installation Steps

-> "Amey - The Chatbot" can be installed in 2 ways, as an Eclipse Project (for Eclipse lovers) or as a standalone application (for those who simply want to use Amey).

### A - As an Eclipse project

1.  Simply download this repository from github. To do this, go to the repository "AI_Assignments" and click on "clone/download" (on the top-right hand corner) as shown below,  

![image](https://user-images.githubusercontent.com/18630586/35471215-abc240d8-037c-11e8-933a-78ff8f1d4408.png)

2.  Next, fire up your Eclipse and choose the "File -> Open Projects from File System" option. Navigate to the "AI_Assignments\Assignment 1 - Chatbot\AIAssignment1 - Eclipse Project" folder and open it.  Then you simply have to run the "MainGUI.java" file using,

![image](https://user-images.githubusercontent.com/18630586/35471256-86279656-037d-11e8-8d70-6e283661f697.png)  this icon.  

### B - As a standalone application

1.  Make sure that you have Java properly configured on your Windows system and by that I mean that the Java environment variables are properly set.  
2.  Simply download this repository from github. To do this, go to the repository "AI_Assignments" and click on "clone/download" (on the top-right hand corner) as shown below,  

![image](https://user-images.githubusercontent.com/18630586/35471215-abc240d8-037c-11e8-933a-78ff8f1d4408.png)

3.  Now, all you have to do is run the "Amey the Chatbot - Launcher" bat file by double clicking on it!
