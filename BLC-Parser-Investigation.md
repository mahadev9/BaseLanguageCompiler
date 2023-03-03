# BLC: Parser Investigation
ou might find the [Antlr website](https://www.antlr.org/) and the [Kotlin Language Overview](https://kotlinlang.org/docs/home.html) useful resources.

Each group should create or designate a fork of the [blc repository](https://gitlab.eecis.udel.edu/clause/blc) as their primary branch. You may also want to create a new group: https://gitlab.eecis.udel.edu/groups/new. In either case, both I and the TA will need access (clause@udel.edu; minasmz@udel.edu).

1) To start, work though the [Advanced Code Reading Exercises](https://github.com/CodeReadingClubs/Resources/blob/trunk/exercises.md#advanced-exercises) for both the antlr-based and hand-written parsers. You should include your notes for this exercise as part of the report. I also strongly recommend you start adding comments to your copy of the repository.

2) Answer the following for each implementation of the parser:
    a) Where is the grammar encoded?
    b) What notation is used?
    c) What is the representation of a token?
    d) What is the representation of a production rule?

3) PEGs and CFGs are syntactically similar but are linked to different formalisms. What are the difference(s) between the formalisms? What motivates the difference(s)? What is the formalism used in each parser?

4) In both PEGs and CFGs [left-recursion](https://en.wikipedia.org/wiki/Left_recursion#Removing_left_recursion) is a significant issue as it can lead to infinite loops. Is there left recursion in the Base Language grammar? (Hint: Yes) What parts are left recursive? How does each implementation of the parser resolve this issue? What are the strengths of each?

5) Recall the desirable properties of a compiler (speed, generated code quality, useful errors messages, etc.). How well (or not) does each implementation of the parser support these properties?

6) Compare and contrast the hand-written parser with [javac's parser](https://github.com/openjdk/jdk/tree/master/src/jdk.compiler/share/classes/com/sun/tools/javac/parser).  What aspects are similar? What are different? What features are missing in the hand-written parser? How do they impact the desirable properties of a compiler? Are there differences quantitative or qualitative? (Some of these differences might be good inspiration for blc extensions/improvements.)

7) Both implementations of the parser create an AST. Are there any differences in how this is accomplished? Is the AST directly generated or is an intermediate representation created (concrete syntax tree)?

8) Does each implementation recognize the same language? Justify your answer. (Hint: investigate string literals)

Only one copy of the report should be submitted per team.