package edu.udel.blc.parse.hand_written

import edu.udel.blc.ast.*
import edu.udel.blc.ast.BinaryOperator.*
import edu.udel.blc.ast.UnaryOperator.LOGICAL_COMPLEMENT
import edu.udel.blc.ast.UnaryOperator.NEGATION
import edu.udel.blc.parse.SyntaxError
import edu.udel.blc.parse.hand_written.BaseToken.Kind.*

class BaseParser(
    private val tokens: Iterator<BaseToken>
) {

    constructor(lexer: BaseLexer) : this(lexer.tokens())

    private var currentToken: BaseToken = tokens.next()

    private fun advance(): BaseToken {
        val previousToken = currentToken
        if (!isAtEnd) {
            currentToken = tokens.next()
        }
        return previousToken
    }

    private val isAtEnd: Boolean
        get() = currentToken.kind == EOF

    fun compilationUnit(): CompilationUnitNode {
        return CompilationUnitNode(0..0, declarations())
    }

    fun declarations() = buildList {
        while (!isAtEnd) {
            this += declaration()
        }
    }

    fun declaration(): Node {
        return when {
            check(FUN) -> functionDeclaration()
            check(STRUCT) -> structDeclaration()
            check(CLASS) -> classDeclaration()
            check(VAR) -> variableDeclaration()
            else -> statement()
        }
    }

    fun functionDeclaration(): FunctionDeclarationNode {
        val keyword = consume(FUN) { "Expect 'fun'." }
        val name = consume(IDENTIFIER) { "Expect function name." }
        consume(LPAREN) { "Expect '(' after function name." }
        val parameters = list(RPAREN, ::parameter)
        consume(RPAREN) { "Expect ')' after parameters." }
        consume(ARROW) { "Expect '->' before return type" }
        val returnType = type()
        val body = block()
        return FunctionDeclarationNode(keyword.range, name.text, parameters, returnType, body)
    }

    fun parameter(): ParameterNode {
        val name = consume(IDENTIFIER) { "Expect parameter name." }
        consume(COLON) { "Expect ':' after parameter name" }
        val type = type()
        return ParameterNode(name.range, name.text, type)
    }

    fun structDeclaration(): StructDeclarationNode {
        val keyword = consume(STRUCT) { "Expect 'struct'." }
        val name = consume(IDENTIFIER) { "Expect struct name." }
        consume(LBRACE) { "Expect '{' before fields." }
        val fields = list(RBRACE, ::field)
        consume(RBRACE) { "Expect '}' after fields." }
        return StructDeclarationNode(name.range, name.text, fields)
    }


    fun classDeclaration(): ClassDeclarationNode {
        val keyword = consume(CLASS) { "Expect 'class'." }
        val name = consume(IDENTIFIER) { "Expect class name." }
        consume(LBRACE) { "Expect '{' before class declaration." }
        val members = list(RBRACE, ::classMember, false)
        consume(RBRACE) { "Expect '}' after class declaration." }
        val fields = members.filterIsInstance<FieldNode>()
        val methods = members.filterIsInstance<FunctionDeclarationNode>()
        return ClassDeclarationNode(name.range, name.text, fields, methods)
    }

    fun classMember(): Node {
        return when {
            check(VAR) -> field(true)
            check(FUN) -> functionDeclaration()
            else -> error("Unknown")
        }
    }

    fun field(classField: Boolean = false): FieldNode {
        if (classField) {
            consume(VAR) { "Expect 'var' after field name" }
        }
        val name = consume(IDENTIFIER) { "Expect field name." }
        consume(COLON) { "Expect ':' after field name" }
        val type = type()
        if (classField) {
            consume(SEMICOLON) { "Expect ';' after field name" }
        }
        return FieldNode(name.range, name.text, type)
    }

    fun variableDeclaration(): Node {
        val keyword = consume(VAR) { "Expect 'var'." }
        val name = consume(IDENTIFIER) { "Expect variable name." }
        consume(COLON) { "Expect ':' after variable name." }
        val type = type()
        consume(EQUAL) { "Expect '=' before initializer." }
        val initializer = expression()
        consume(SEMICOLON) { "Expect ';' after variable declaration." }
        return VariableDeclarationNode(name.range, name.text, type, initializer)
    }


    fun statement(): Node {
        return when {
            check(IF) -> ifStatement()
            check(RETURN) -> returnStatement()
            check(WHILE) -> whileStatement()
            check(LBRACE) -> block()
            else -> expressionStatement()
        }
    }

    fun block(): BlockNode {
        val lbrace = consume(LBRACE) { "Expect '{'." }
        val declarations = buildList {
            while (!check(RBRACE) && !isAtEnd) {
                this += declaration()
            }
        }
        consume(RBRACE) { "Expect '}'." }
        return BlockNode(lbrace.range, declarations)
    }

    fun expressionStatement(): Node {
        val expr = expression()
        consume(SEMICOLON) { "Expect ';' after statement." }
        return ExpressionStatementNode(expr.range, expr)
    }

    fun ifStatement(): Node {
        val keyword = consume(IF) { "Expect 'if'." }
        consume(LPAREN) { "Expect '(' after 'if'." }
        val condition = expression()
        consume(RPAREN) { "Expect ')' after if condition." }
        val thenStatement = statement()
        val elseStatement = when {
            match(ELSE) -> statement()
            else -> null
        }
        return IfNode(keyword.range, condition, thenStatement, elseStatement)
    }

    fun returnStatement(): Node {
        val keyword = consume(RETURN) { "Expect 'return'." }
        val value = when {
            check(SEMICOLON) -> null
            else -> expression()
        }
        consume(SEMICOLON) { "Expect ';' after return." }
        return ReturnNode(keyword.range, value)
    }

    fun whileStatement(): Node {
        val keyword = consume(WHILE) { "Expect 'while'." }
        consume(LPAREN) { "Expect '(' after 'while'." }
        val condition = expression()
        consume(RPAREN) { "Expect ')' after condition." }
        val body = statement()
        return WhileNode(keyword.range, condition, body)
    }


    fun expression(): Node {
        return assignment()
    }

    private fun assignment(): Node {
        var expr = disjunction()
        if (check(EQUAL)) {
            val operator = consume(EQUAL) { " Expect '='." }
            val value = assignment()
            expr = AssignmentNode(operator.range, expr, value)
        }
        return expr
    }

    private fun disjunction(): Node {
        var expr = conjunction()
        while (check(OR)) {
            val operator = consume(OR) { "Expect '||'." }
            val right = conjunction()
            expr = BinaryExpressionNode(operator.range, LOGICAL_DISJUNCTION, expr, right)
        }
        return expr
    }

    private fun conjunction(): Node {
        var expr = equality()
        while (check(AND)) {
            val operator = consume(AND) { "Expect '&&'." }
            val right = equality()
            expr = BinaryExpressionNode(operator.range, LOGICAL_CONJUNCTION, expr, right)
        }
        return expr
    }

    private fun equality(): Node {
        var expr = comparison()
        while (true) {
            expr = when {
                check(BANG_EQUAL) -> {
                    val operator = consume(BANG_EQUAL) { "Expect '!='." }
                    val right = comparison()
                    BinaryExpressionNode(operator.range, NOT_EQUAL_TO, expr, right)
                }
                check(EQUAL_EQUAL) -> {
                    val operator = consume(EQUAL_EQUAL) { "Expect '=='." }
                    val right = comparison()
                    BinaryExpressionNode(operator.range, EQUAL_TO, expr, right)
                }
                else -> break
            }
        }
        return expr
    }

    private fun comparison(): Node {

        var expr = term()

        while (true) {
            expr = when {
                check(RANGLE) -> {
                    val operator = consume(RANGLE) { "Expect '<'." }
                    val right = term()
                    BinaryExpressionNode(operator.range, GREATER_THAN, expr, right)
                }
                check(RANGLE_EQUAL) -> {
                    val operator = consume(RANGLE_EQUAL) { "Expect '<='." }
                    val right = term()
                    BinaryExpressionNode(operator.range, GREATER_THAN_OR_EQUAL_TO, expr, right)
                }
                check(LANGLE) -> {
                    val operator = consume(LANGLE) { "Expect '<'." }
                    val right = term()
                    BinaryExpressionNode(operator.range, LESS_THAN, expr, right)
                }
                check(LANGLE_EQUAL) -> {
                    val operator = consume(LANGLE_EQUAL) { "Expect '<='." }
                    val right = term()
                    BinaryExpressionNode(operator.range, LESS_THAN_OR_EQUAL_TO, expr, right)
                }
                else -> break
            }
        }

        return expr

    }

    private fun term(): Node {

        var expr = factor()

        while (true) {
            expr = when {
                check(MINUS) -> {
                    val operator = consume(MINUS) { "Expect '-'." }
                    val right = factor()
                    BinaryExpressionNode(operator.range, SUBTRACTION, expr, right)
                }
                check(PLUS) -> {
                    val operator = consume(PLUS) { "Expect '+'." }
                    val right = factor()
                    BinaryExpressionNode(operator.range, ADDITION, expr, right)
                }
                else -> break
            }
        }

        return expr

    }

    private fun factor(): Node {

        var expr = unaryPrefix()

        while (true) {
            expr = when {
                check(PERCENT) -> {
                    val operator = consume(PERCENT) { "Expect '%'." }
                    val right = factor()
                    BinaryExpressionNode(operator.range, REMAINDER, expr, right)
                }
                check(STAR) -> {
                    val operator = consume(STAR) { "Expect '*'." }
                    val right = factor()
                    BinaryExpressionNode(operator.range, MULTIPLICATION, expr, right)
                }
                check(SLASH) -> {
                    val operator = consume(SLASH) { "Expect '/'." }
                    val right = factor()
                    BinaryExpressionNode(operator.range, BinaryOperator.DIVISION, expr, right)
                }
                else -> break
            }
        }

        return expr
    }

    private fun unaryPrefix(): Node {
        return when {
            check(BANG) -> {
                val operator = consume(BANG) { "Expect '!'." }
                val right = unaryPrefix()
                UnaryExpressionNode(operator.range, LOGICAL_COMPLEMENT, right)
            }
            check(MINUS) -> {
                val operator = advance()
                val right = unaryPrefix()
                UnaryExpressionNode(operator.range, NEGATION, right)
            }
            else -> unaryPostfix()
        }
    }

    private fun unaryPostfix(): Node {
        var expr = primary()

        while (true) {
            expr = when {
                check(LPAREN) -> {
                    val lparen = consume(LPAREN) { "Expect '('." }
                    val arguments = list(RPAREN, ::expression)
                    consume(RPAREN) { "Expect ')' after arguments." }
                    CallNode(lparen.range, expr, arguments)
                }
                check(LBRACKET) -> {
                    val lbracket = consume(LBRACKET) { "Expect '['." }
                    val index = expression()
                    consume(RBRACKET) { "Expect ']' after index." }
                    IndexNode(lbracket.range, expr, index)
                }
                check(DOT) -> {
                    val dot = consume(DOT) { "Expect '.'." }
                    val name = consume(IDENTIFIER) { "Expect name" }
                    when {
                        check(LPAREN) -> {
                            val lparen = consume(LPAREN) { "Expect ')'. " }
                            val arguments = list(RPAREN, ::expression)
                            consume(RPAREN) { "Expect ')' after arguments." }
                            MethodCallNode(
                                lparen.range,
                                name.text,
                                arguments,
                                expr,
                            )
                        }
                        else -> {
                            FieldSelectNode(dot.range, expr, name.text)
                        }
                    }
                }
                else -> break
            }
        }

        return expr
    }

    fun primary(): Node {
        return when {
            check(FALSE, TRUE) -> booleanLiteral()
            check(NUMBER) -> intLiteral()
            check(FLOAT) -> floatLiteral()
            check(QUOTE_OPEN) -> stringLiteral()
            check(IDENTIFIER) -> identifier()
            check(UNIT) -> unitLiteral()
            check(LPAREN) -> parenthesizedExpression()
            check(LBRACKET) -> arrayLiteral()
            check(THIS) -> thisKeyword()
            else -> error("Expect expression.")
        }
    }

    fun thisKeyword(): ThisNode {
        val keyword = consume(THIS) { "Expect 'this'." }
        return ThisNode(keyword.range)
    }

    fun booleanLiteral(): BooleanLiteralNode {
        val literal = consume(TRUE, FALSE) { "Expect 'true' or 'false'." }
        return BooleanLiteralNode(literal.range, literal.text.toBoolean())
    }

    fun intLiteral(): IntLiteralNode {
        val literal = consume(NUMBER) { "Expect number." }
        return IntLiteralNode(literal.range, literal.text.toLong())
    }

    fun floatLiteral(): FloatLiteralNode {
        val literal = consume(FLOAT) { "Expect float number." }
        return FloatLiteralNode(literal.range, literal.text.toDouble())
    }

    fun stringLiteral(): StringLiteralNode {
        val openQuote = consume(QUOTE_OPEN) { "Expect '\"'." }
        val literal = buildString {
            while (check(CHARACTER)) {
                val c = consume(CHARACTER) { "Expect character." }
                append(c.text)
            }
            consume(QUOTE_CLOSE) { "Expect '\"'." }
        }
        return StringLiteralNode(openQuote.range, literal)
    }

    fun parenthesizedExpression(): Node {
        val lparen = consume(LPAREN) { "Expect '('." }
        val expr = expression()
        consume(RPAREN) { "Expect ')'." }
        return expr
    }

    fun arrayLiteral(): ArrayLiteralNode {
        val lbrace = consume(LBRACKET) { "Expect '['." }
        val elements = list(RBRACKET, ::expression)
        consume(RBRACKET) { "Expect ']'." }
        return ArrayLiteralNode(lbrace.range, elements)
    }

    fun unitLiteral(): UnitLiteralNode {
        val literal = consume(UNIT) { "Expect 'unit'." }
        return UnitLiteralNode(literal.range)
    }

    fun type(): Node {
        return when {
            check(IDENTIFIER) -> identifier()
            check(LPAREN) -> functionType()
            check(LBRACKET) -> arrayType()
            else -> error("Expect type.")
        }
    }

    fun identifier(): ReferenceNode {
        val name = consume(IDENTIFIER) { "Expect identifier." }
        return ReferenceNode(name.range, name.text)
    }
   fun functionType():FunctionTypeNode{
       consume(LPAREN){"Expect ( at the beginning of function type"}
       val from = list(RPAREN, ::type) //function domain
       consume(RPAREN){"Expect ( at the beginning of function type"}
       consume(ARROW) {"Expect -> after the from type"}
       val to = type() // function range
       return FunctionTypeNode(from[0].range, from, to)
   }

    fun arrayType(): ArrayTypeNode {
        val lbrace = consume(LBRACKET) { "Expect '[' after array type." }
        val elementType = type()
        consume(RBRACKET) { "Expect ']' after array type." }
        return ArrayTypeNode(lbrace.range, elementType)
    }

    fun <T> list(kind: BaseToken.Kind, element: () -> T, useSeparator: Boolean = true): List<T> = buildList {
        if (!check(kind)) {
            do {
                when {
                    check(kind) -> break
                    else -> add(element())
                }
            } while (if (useSeparator) match(COMMA) else true)
        }
    }

    private fun match(vararg kinds: BaseToken.Kind): Boolean {
        if (check(*kinds)) {
            advance()
            return true
        }
        return false
    }

    private fun consume(vararg kinds: BaseToken.Kind, lazyMessage: () -> String): BaseToken {
        when {
            check(*kinds) -> return advance()
            else -> error(lazyMessage())
        }
    }

    private fun check(vararg kinds: BaseToken.Kind): Boolean {
        return !isAtEnd && kinds.any { currentToken.kind == it }
    }

    private fun error(message: String): Nothing {
        throw SyntaxError(currentToken.range, "$message $currentToken")
    }

}
