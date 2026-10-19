@echo off
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\ch\tbz\m320\helpdesk\*.java
if errorlevel 1 exit /b 1
java -cp out ch.tbz.m320.helpdesk.Main
