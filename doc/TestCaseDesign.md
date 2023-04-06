# Design and Implementation of Test Suite

## Introduction:
The purpose of this document is to provide an overview of the test cases designed for the Base Language. The test cases are designed to test individual features and also to test the interactions among different features. We have created a set of test cases that cover various aspects of the language, including expressions, statements, variables, functions, and control structures. The test cases are designed to cover positive and negative scenarios to ensure the compiler handles the inputs correctly.

## Primitive Data Types, Struct and Array
All the primitive data types (`Int`, `Boolean`, `String`) are all tested as storing into a variable and printing to the console to check the correctness and compared equality with direct values. All test cases are implemented in the file `VariableTest.bl`. Similarly, `Struct` and `Array` are tested based on assigning values and printing them, also an array of primitive data types and struct were tested. They are implemented in the file `StructTest.bl` and `ArrayTest.bl`. This ensures that basic data types, structs and arrays work correctly and exist. Indexing the array using the negative number is not supported with antlr grammar (basically it does not wrap around the values in the array). Float data type is not supported. Combining arithmetic or logical operators with array values is not supported. The test case includes positive and negative scenarios.

## Arithmetic Operators (+, -, *, %)
With the test cases written in the file `ArithmeticTest.bl`, proves that all the arithmetic operators works correctly and exists in the grammar. Division (/) is not supported with antlr grammar and not implemented but used for comments.

## Equality and Comparison Operators (==, !=, >, >=, <, <=)
Equality and Comparison Operators are very important for any programming language to have them implemented in the grammar. They are used to create a flow path to determine the solution, but with aditional features like `if else`, `while` (bascially conditional expression and loops). The working of these operators are tested in the file `EqualityTest.bl`. Grouping of variables to determine the result is not supported with antlr grammar.

## Logical Operators (!, &&, ||)
Similarly, like Equality and Comparison Operators, logical operators are very important and to determine the solution. The testing of these operator's functionality is done in the file `LogicalTest.bl`.

## If Else Conditional Expression
To determine the flow of a solution, conditional expression are very necessary. In order to test the functionality of `if else`, the test cases are designed in the file `ConditionalIfTest.bl`. The functionality tested are nested if else statements and combined with functions in the file `FunctionTest.bl`. Tests the functionality of an if statement with logical operators (&&, ||) in the condition.

## While Loop
Repetition statements such as while are important constructs in any programming language. They allow one or more statements to be executed many times based on a condition. While loop is tested in the file `WhileTest.bl` with a variety of conditional expressions and statements in the body.    
## Builtin Functions
The built in functions (print, str, concat) are tested in the file `BuiltinFunctionsTest.bl`.
## Functions
The functionality of the functions are tested in the file `FunctionTest.bl`. This is combined with other functionality like `if else` and `while`. Recusion of functions is not supported with antlr grammar. It also includes positive and negative scenarios for checking the scope of local variables in different contexts.

## Advance Functionality
This test suite (`AdvanceTest.bl`) includes some of the functionality combined with each other like `if else`, `while`, `fun`, etc. We implemented binary search algoithm to verify that everything works together correctly. We implemented 

