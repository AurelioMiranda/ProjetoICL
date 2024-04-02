# Interpretation and compilation of Programming Languages 2023-24





## Lab 2 - A compiler to the JVM and extending the interpreter

In this lab we will achieve two goals:
  - Compiling CALC to the JVM.
  - Extending the infrastructure to handle boolean values.

It is expected that you have completed [Lab 1](https://classroom.github.com/a/M8OLFUWv) successfully before starting Lab 2. If not, go ahead and do that now!

### Compiling CALC to the JVM

Write a compiler for CALC that emits JVM bytecode. To do this, we will make use of a bytecode "assembler" tool called [Jasmin](https://github.com/davidar/jasmin) that takes as input a textual representation of the bytecode of a class and produces a `.class` file that the JVM can actually execute. Jasmin is provided as a `jar` file in the starter code.

The `target` package contains the compiler infrastructure starter code. The `Instruction` abstract class encodes a JVM bytecode instruction, consisting of a `String` which contains the op-code and an optional list of `String`s that contains the operation's arguments (if any are needed). The `toString()` implementation produces Jasmin-appropriate syntax for the corresponding operation.
You can look at classes `IAdd` and `SIPush`  to see how to produce specific instructions as needed.
The `BasicBlock` class codifies a simple sequence of instructions (this will be augmented later once we have more sophisticated
control flow and procedures). It provides a way to append an instruction to the sequence (`addInstruction`) and ways to convert
the block into the appropriate textual representation (for efficiency purposes we will use `StringBuilder` rather than
simple String concatenation).

The `compiler.CodeGen` class encodes a visitor-style code generator that you may use as inspiration. Note how the visitor type parameter is `Void` because no value is actually returned by the code generator, it simply collects a sequence of instructions in its internal state which is then written to a file. A common idiom in these visitors is to introduce a `static` method that creates the visitor object and "runs it" (e.g. the `codeGen` static method). 
The `writeToFile` method calls the `codeGen` method (generating the sequence of JVM bytecode that the given expression compiles to) and inserts the appropriate preamble and footer code so that running the program prints the number that is left on the top of the stack.

If you want to write the code generator in a recursive style rather than using the visitor-based approach, you will want to have the recursive function that all AST nodes implement take as an argument the `BasicBlock` object. It is also possible to have the visitor take the `BasicBlock` as an argument of `visit` rather than storing it as a field, can you see how?

To actually run the generated code you will need to generate the corresponding `class` file using Jasmin:
```
java -jar jasmin.jar myfile.j
```
Jasmin looks at the .class directive contained in the file to decide where to place the output class file. So if `myfile.j` starts with:
```
    .class mypackage/MyClass
```
then Jasmin will place the output class file `MyClass.class` in the subdirectory `mypackage` of the current directory. It will create the mypackage directory if it doesn't exist.


**Note:** Once you have the interpreter and the compiler working for the same language, you can use one to validate the other in testing!

**Challenge:** Our language lacks a unary negative number operator (e.g. -5). Can you add it to the language as syntactic sugar (i.e. without adding a new AST node)?



### Boolean Values

We will now start growing our language in the first non-trivial way by making programs be expressions that can be either arithmetic or boolean expressions.

#### Parsing

For this, you will need to extend the parser the top-level non-terminal `bexp`, to have the following extra operations, still following the standard priorities between operators:

~~~
bexp ::= bool | cmp | exp '&&' exp | exp '||' exp | '!' exp 

cmp ::= exp | exp '<' exp | exp '>' exp | exp '==' exp | exp '!=' exp

exp ::= exp '+' exp | exp '-' exp | exp '*' exp | exp '/' exp | '-' exp | '(' exp ')' | num

bool ::= true | false

num ::= ['1'-'9']\['0'-'9'\]*
~~~

The operators have their expected meaning. You will want the parser to generate the AST accordingly, so you must also create
the corresponding new AST nodes.
Note that the grammar above cannot be directly implemented in JavaCC due to ambiguity. You will need to follow a similar
structure to the parser of Lab 1, which implemented the grammar:

~~~
exp ::= exp '+' exp | exp '-' exp | exp '*' exp | exp '/' exp | '-' exp | '(' exp ')' | num

num ::= ['1'-'9']\['0'-'9'\]*
~~~




#### Extending the Interpreter

Since `1+1`, `2 < 3` and `true && false` are all valid programs, evaluating a program can now result in one of three outcomes: a boolean value, an integer value, or a runtime error due to a misuse of the language operators (e.g. `2 < true` or `1+false`).
This means that our implementation of the interpreter can no longer simply produce a (Java) value of type `Integer` or `int`.

A way to solve this is to create our own `Value` interface or abstract class that we can then implement or extend with our own custom values `IntVal` and `BoolVal` classes, which wrap integers and booleans in the interpreter:

```java
  public interface Exp {
    public Value eval();
  }

  public interface Value {}
  public class IntVal {
    int value;
    
    public IntVal(int value) { this.value = value; }
  }
  ...
```

Or in visitor style:

```java
public class Interpreter implements Visitor<Value> { ... }
```

After doing the refactoring mentioned above, extend the implementation to account for the new operators. To account for bad uses of an operator we can either throw an exception or assume that such uses cannot happen (i.e., they are handled at an earlier stage of the pipeline).

#### A Typechecker for CALC + Booleans

We can ensure that no runtime errors can ever be raised by the interpreter by enforcing that only **well-typed** are ever actually executed. To do this, we must implement a type-checker for our language.

Do so now, following the material from the lectures.

**Note:** You will need to define a ``Type`` interface with two implementations ``IntType`` and ``BoolType``.
