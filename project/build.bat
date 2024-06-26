@echo off
echo Compiling source files
javac -d bin -classpath src src\ast\*.java src\interpreter\*.java src\main\*.java src\parser\*.java src\typechecker\*.java src\types\*.java src\values\*.java

echo Compilation complete.

echo Creating .jar files
jar cvfm Console.jar manifests\interpreter.txt -C bin/ .
jar cvfm GenerateCode.jar manifests\compiler.txt -C bin/ .
echo Creation complete

echo.

pause