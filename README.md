# Interpretation and compilation of Programming Languages 2023-24





## MicroML Project

In this project, you will implement an interpreter and a compiler for the MicroML language, described in this document. 
Your project must consist of two executables, one implementing the interpreter pipeline and another implementing the compiler pipeline. 
Each pipeline must (at least) include a parser and a type-checker stage before the execution/code generation stages, using the tools and techniques described throughout the semester. 
The language you will be implementing in this project (MicroML) is a functional and imperative programming language in the style of OCaml.

### Compiling CALC to the JVM

The following is a grammar for the syntax of MicroML:
E ::= Num|true|false | id
    |    E+E | E−E | E∗E | E/E | −E | (E)                 
    |    E=E | E!=E | E>E | E>=E | E<E | E<=E
    |    E&&E | E||E |˜E
    |    let (x(: T)? = E)+inE
    |    newE | E := E | !E
    |    if E thenE elseE end | if E thenEend
    |    while E doEend
    |    println E | printE | E;E
    |    funPL → Eend|E(EL?)
    |    ()

EL ::= E(,E)∗
PL ::= (id:T)+
T  ::= unit | int | bool | ref T | (T)∗ → T

### Project done by:
Aurélio Miranda - 69369
Francisco Silva - 