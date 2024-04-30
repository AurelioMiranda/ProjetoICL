# MicroML Project - Progress Report

This document outlines the progress made on the MicroML interpreter and compiler project.

## Implemented Features
The following grammar elements have been successfully implemented in this project phase:

E ::= Num | true | false | id   
E + E | E - E | E * E | E / E  
-E | (E)  
E = E | E != E | E > E | E >= E  
E < E | E <= E  
E && E | E || E | ~E  
let (x(: T)? = E) + in E  
if E then E else E end  
if E then E end

These features enable the interpreter to parse and evaluate a subset of MicroML expressions.

## Features Not Yet Implemented
The following functionalities are planned for future development:

newE  
E := E  
!E  
while E do E end  
println E  
print E  
E; E  
fun PL -> E end  
E(EL?)  
()

Integrating these features is crucial for the interpreter's capabilities to handle a wider range of MicroML programs and will be implemented in the very near future.

## Testing the Program
Here's a step-by-step guide on how to test the program:

1. **Build the Project**: Double-click the `build.bat` file to compile the project.

2. **Prepare Test Input**: Create a file named `input.txt` and enter the MicroML expressions you want to test (one expression per line).

3. **Run the Interpreter**: Navigate to the project directory using a terminal and execute the following command:

   `java -classpath bin main.Console input.txt`

This command launches the interpreter and processes the expressions listed in `input.txt`.


### Project done by:  
Aurélio Miranda - 69369  
Francisco Silva - 57824  

### Our private Git repository:  
`https://github.com/AurelioMiranda/ProjetoICL.git`