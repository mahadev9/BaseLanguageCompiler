package edu.udel.blc.parse.hand_written

class BaseToken(
    val kind: Kind,
    val text: String,
    val range: IntRange
) {

    enum class Kind {
        LPAREN, RPAREN,
        LBRACE, RBRACE,
        LBRACKET, RBRACKET,
        LANGLE, RANGLE,
        COMMA,
        DOT,

        PLUS, MINUS, SLASH, STAR, PERCENT,
        COLON, SEMICOLON,
        BANG,

        EQUAL,
        BANG_EQUAL, EQUAL_EQUAL,
        RANGLE_EQUAL,
        LANGLE_EQUAL,
        ARROW,

        IDENTIFIER,
        NUMBER,
        FLOAT,

        QUOTE_OPEN,
        QUOTE_CLOSE,
        CHARACTER,

        AND,
        OR,

        IF,
        ELSE,

        TRUE,
        FALSE,

        FUN,

        RETURN,

        VAR,
        WHILE,

        STRUCT,

        CLASS,
        THIS,

        EOF,
        COMMENT,
        WHITESPACE,

        UNIT,

        UNKNOWN,
    }

}