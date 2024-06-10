@echo off

:start

echo Running GenerateCode...
java -jar GenerateCode.jar

if not exist result.txt (
    echo result.txt not created.
    exit /b 1
)

echo Running Jasmin to assemble result.txt...
java -jar jasmin.jar result.txt

if not exist Demo.class (
    echo Demo.class not created.
    exit /b 1
)

echo Running Demo class...
java Demo

echo.
echo.
goto start

pause