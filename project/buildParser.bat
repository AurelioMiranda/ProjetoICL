@echo off
echo Compiling Parser.jj...

cd src\parser\
javacc parser.jj

echo.

pause