echo off
title Amey the Chatbot - Launcher
:: See the title at the top. And this comment will not appear in the command prompt.
cd "./Assignment 1 - Chatbot/AIAssignment1 - Eclipse Project"
javac -cp lib/*;. src/MainGUI.java
java -cp lib/*;src MainGUI
pause