@echo off
color 0a
set "version=1.1"
Title Among Us Editor Launcher v%version%
cls
echo Welcome to the optional Among Us Editor launcher v%version% 
echo I only suggest using this if you're having an issue launching the Editor normally!
echo Make sure the Editor is in the same location as this batch file!
echo Make sure the Editor file name also has NO spaces in it!
:start
echo.
echo.
echo Launch Types
echo Normal Launch: 1
echo CMD Launch: 2
echo Java Launch: 3
echo.
set /p type="Enter Launch Type: "

:choosefile
set /p editor="Enter the Editor .jar file name (Press tab to auto complete): "


set editor=%editor:"=%

IF NOT exist %editor% (
	cls
	echo.
	echo File "%editor%" doesn't exist!
	GOTO choosefile
)

IF %type%==1 (
	start %editor%
	GOTO end
)

IF %type%==2 (
	echo.
	echo Please close this window after you close the Editor.
	echo If the editor doesn't run with this launch type, relaunch the launcher and try another Launch type.
	cmd /k %editor%
	GOTO end
)

IF %type%==3 (
	echo java -jar "%editor%"
	GOTO end
)

cls
echo Invalid Launch Type!
GOTO start

:end
echo.
echo Complete! You may now press any key to close this!
pause >nul