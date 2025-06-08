@echo off
echo Cleaning .class files from src directory...
cd /d "%~dp0src"
for /r %%i in (*.class) do (
    echo Deleting: %%i
    del "%%i"
)
echo Done! src directory is now clean.
pause 