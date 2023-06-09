grammar Base;

compilatonUnit
     : ( declarations+=declaration )* EOF
     ;

declaration
    : functionDeclaration                                                               # functionDeclarations
    | STRUCT
        name=IDENTIFIER
        LBRACKET
        ( fields+=field ( COMMA fields+=field )* COMMA? )?
        RBRACKET                                                                         # structDeclaration
    | CLASS
        name=IDENTIFIER
        ( COLON superclass=IDENTIFIER )?
        LBRACKET
        ( members+=member )*
        RBRACKET                                                                         # classDeclaration
    | VAR name=IDENTIFIER COLON type=typeExpression EQUAL initializer=expr SEMICOLON     # variableDeclaration
    | stmt                                                                               # statementAsDeclaration
    ;

member
    : VAR name=IDENTIFIER (COLON type=typeExpression)? SEMICOLON                         # fieldMember
    | functionDeclaration                                                                # methodMember
    ;

functionDeclaration
    :  FUN
        name=IDENTIFIER
        LPAREN
        ( parameters+=parameter (',' parameters+=parameter )* (',')? )?
        RPAREN
        (ARROW returnType=typeExpression)?
        body=block
    ;

parameter
    : name=IDENTIFIER COLON type=typeExpression
    ;

field
    : name=IDENTIFIER COLON type=typeExpression
    ;

stmt
    : block                                                                              # blockAsStatement
    | expression=expr SEMICOLON                                                          # expressionStmt
    | IF LPAREN condition=expr RPAREN thenStatement=stmt ( ELSE elseStatement=stmt )?    # ifStmt
    | RETURN ( expression=expr )? SEMICOLON                                              # returnStmt
    | WHILE LPAREN condition=expr RPAREN body=stmt                                       # whileStmt
    ;

block
    : LBRACKET ( declarations+=declaration )* RBRACKET
    ;

expr
    : <assoc=right> lvalue=expr EQUAL expression=expr                                    # assignment
    | left=expr operator=DISJ right=expr                                                 # binary
    | left=expr operator=CONJ right=expr                                                 # binary
    | left=expr operator=( EQUAL_EQUAL | BANG_EQUAL) right=expr                          # binary
    | left=expr operator=( GREATER | GREATER_EQUAL | LESS | LESS_EQUAL ) right=expr      # binary
    | left=expr operator=( PLUS | MINUS ) right=expr                                     # binary
    | left=expr operator=SLASH right=expr                                                # binary
    | left=expr operator=( PERCENT | STAR ) right=expr                                   # binary
    | operator=( BANG | MINUS ) operand=expr                                             # unaryPrefix
    | receiver=expr DOT callee=expr
        LPAREN
        ( arguments+=expr ( COMMA arguments+=expr )* COMMA? )?
        RPAREN                                                                           # methodCall
    | callee=expr LPAREN ( arguments+=expr ( COMMA arguments+=expr )* COMMA? )? RPAREN   # call
    | expression=expr LBRACE index=expr RBRACE                                           # index
    | expression=expr DOT name=IDENTIFIER                                                # fieldSelect
    | LPAREN expression=expr RPAREN                                                      # parenthesized
    | value=( TRUE | FALSE )                                                             # booleanLiteral
    | value=INT_LITERAL                                                                  # intLiteral
    | value=STRING_LITERAL                                                               # stringLiteral
    | value=FLOAT_LITERAL                                                                # floatLiteral
    | UNIT                                                                               # unitLiteral
    | LBRACE elements+=expr ( COMMA elements+=expr )* COMMA? RBRACE                      # arrayLiteral
    | THIS                                                                               # thisLiteral
    | reference                                                                          # referenceAsExpression
    ;

typeExpression
    : reference                                                                          # referenceAsType
    | LBRACE elementType=typeExpression RBRACE                                           # arrayType
    ;

reference
    : name=IDENTIFIER
    ;


fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [a-zA-Z]
    ;

LPAREN: '(';
RPAREN: ')';

LBRACE: '[';
RBRACE: ']';

LBRACKET: '{';
RBRACKET: '}';

DOT: '.';
COMMA: ',';
COLON: ':';
SEMICOLON: ';';

CONJ: '&&';
DISJ: '||';
EQUAL: '=';
EQUAL_EQUAL: '==';
BANG_EQUAL: '!=';
BANG: '!';
PLUS: '+';
MINUS: '-';
STAR: '*';
SLASH: '/';
PERCENT: '%';

ARROW: '->';

FUN: 'fun';
IF: 'if';
ELSE: 'else';
STRUCT: 'struct';
CLASS: 'class';
WHILE: 'while';
RETURN: 'return';
VAR: 'var';
THIS: 'this';

LESS: '<';
LESS_EQUAL: '<=';
GREATER: '>';
GREATER_EQUAL: '>=';

TRUE: 'true';
FALSE: 'false';

UNIT: 'unit';

IDENTIFIER
    : (LETTER | '_') ( LETTER | DIGIT | '_' )*
    ;

INT_LITERAL
    : '-'? DIGIT+
    ;

FLOAT_LITERAL
	: '-'? DIGIT+ '.' DIGIT+
	;

STRING_LITERAL
    : '"' ( '\\"' | . )*? '"'
    ;

DelimitedComment
    : '/*' ( DelimitedComment | . )*? '*/' -> skip
    ;

LineComment
    : '//' ~[\r\n]* -> skip
    ;

WS
    : [\u0020\u0009\u000C\u000A\u000D] -> skip
    ;