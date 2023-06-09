package edu.udel.blc.parse.antlr

import BaseBaseVisitor
import BaseLexer
import BaseParser
import edu.udel.blc.ast.*
import edu.udel.blc.ast.BinaryOperator.*
import edu.udel.blc.ast.UnaryOperator.LOGICAL_COMPLEMENT
import edu.udel.blc.ast.UnaryOperator.NEGATION
import org.antlr.v4.runtime.ParserRuleContext

class BaseVisitor : BaseBaseVisitor<Node>() {

    val ParserRuleContext.range: IntRange
        get() {
            return start.startIndex..stop.stopIndex
        }

    override fun visitCompilatonUnit(ctx: BaseParser.CompilatonUnitContext): CompilationUnitNode {
        val statements = ctx.declarations.map { it.accept(this) }
        return CompilationUnitNode(ctx.range, statements)
    }

    override fun visitFunctionDeclaration(ctx: BaseParser.FunctionDeclarationContext): FunctionDeclarationNode {
        val name = ctx.name.text
        val params = ctx.parameters.map { it.accept(this) as ParameterNode }
        val returnType = ctx.returnType.accept(this) as Node
        val body = ctx.body.accept(this) as BlockNode
        return FunctionDeclarationNode(ctx.range, name, params, returnType, body)
    }

    override fun visitParameter(ctx: BaseParser.ParameterContext): ParameterNode {
        val name = ctx.name.text
        val type = ctx.type.accept(this) as Node
        return ParameterNode(ctx.range, name, type)
    }

    override fun visitStructDeclaration(ctx: BaseParser.StructDeclarationContext): StructDeclarationNode {
        val name = ctx.name.text
        val fields = ctx.fields.map { it.accept(this) as FieldNode }
        return StructDeclarationNode(ctx.range, name, fields)
    }

    override fun visitField(ctx: BaseParser.FieldContext): FieldNode {
        val name = ctx.name.text
        val type = ctx.type.accept(this) as Node
        return FieldNode(ctx.range, name, type)
    }

    override fun visitVariableDeclaration(ctx: BaseParser.VariableDeclarationContext): VariableDeclarationNode {
        val name = ctx.name.text
        val type = ctx.type.accept(this)
        val initializer = ctx.initializer.accept(this)
        return VariableDeclarationNode(ctx.range, name, type, initializer)
    }

    // Stmt

    override fun visitBlock(ctx: BaseParser.BlockContext): BlockNode {
        val declarations = ctx.declarations.map { it.accept(this) }
        return BlockNode(ctx.range, declarations)
    }

    override fun visitExpressionStmt(ctx: BaseParser.ExpressionStmtContext): ExpressionStatementNode {
        val expr = ctx.expression.accept(this)
        return ExpressionStatementNode(ctx.range, expr)
    }

    override fun visitIfStmt(ctx: BaseParser.IfStmtContext): IfNode {
        val condition = ctx.condition.accept(this)
        val thenStmt = ctx.thenStatement.accept(this)
        val elseStmt = ctx.elseStatement?.accept(this)
        return IfNode(ctx.range, condition, thenStmt, elseStmt)
    }

    override fun visitReturnStmt(ctx: BaseParser.ReturnStmtContext): ReturnNode {
        val expression = ctx.expression?.accept(this)
        return ReturnNode(ctx.range, expression)
    }

    override fun visitWhileStmt(ctx: BaseParser.WhileStmtContext): WhileNode {
        val condition = ctx.condition.accept(this)
        val body = ctx.body.accept(this)
        return WhileNode(ctx.range, condition, body)
    }

    // expr

    override fun visitAssignment(ctx: BaseParser.AssignmentContext): AssignmentNode {
        val lvalue = ctx.lvalue.accept(this)
        val expression = ctx.expression.accept(this)
        return AssignmentNode(ctx.range, lvalue, expression)
    }

    override fun visitBinary(ctx: BaseParser.BinaryContext): BinaryExpressionNode {
        val left = ctx.left.accept(this)
        val right = ctx.right.accept(this)
        val operator = when (ctx.operator.type) {
            BaseLexer.CONJ -> LOGICAL_CONJUNCTION
            BaseLexer.DISJ -> LOGICAL_DISJUNCTION
            BaseLexer.EQUAL_EQUAL -> EQUAL_TO
            BaseLexer.BANG_EQUAL -> NOT_EQUAL_TO
            BaseLexer.LESS -> LESS_THAN
            BaseLexer.LESS_EQUAL -> LESS_THAN_OR_EQUAL_TO
            BaseLexer.GREATER -> GREATER_THAN
            BaseLexer.GREATER_EQUAL -> GREATER_THAN_OR_EQUAL_TO
            BaseLexer.PERCENT -> REMAINDER
            BaseLexer.STAR -> MULTIPLICATION
            BaseLexer.PLUS -> ADDITION
            BaseLexer.MINUS -> SUBTRACTION
            BaseLexer.SLASH -> DIVISION
            else -> error("Unknown binary operator: ${ctx.operator.text}")
        }
        return BinaryExpressionNode(ctx.range, operator, left, right)
    }

    override fun visitUnaryPrefix(ctx: BaseParser.UnaryPrefixContext): UnaryExpressionNode {
        val operand = ctx.operand.accept(this)
        val operator = when (ctx.operator.type) {
            BaseLexer.BANG -> LOGICAL_COMPLEMENT
            BaseLexer.MINUS -> NEGATION
            else -> error("Unknown unary operator: ${ctx.operator.text}")
        }
        return UnaryExpressionNode(ctx.range, operator, operand)
    }

    override fun visitCall(ctx: BaseParser.CallContext): CallNode {
        val callee = ctx.callee.accept(this)
        val arguments = ctx.arguments.map { it.accept(this) }
        return CallNode(ctx.range, callee, arguments)
    }

    override fun visitIndex(ctx: BaseParser.IndexContext): IndexNode {
        val expression = ctx.expression.accept(this)
        val index = ctx.index.accept(this)
        return IndexNode(ctx.range, expression, index)
    }

    override fun visitFieldSelect(ctx: BaseParser.FieldSelectContext): FieldSelectNode {
        val expression = ctx.expression.accept(this)
        val name = ctx.name.text
        return FieldSelectNode(ctx.range, expression, name)
    }

    // literal

    override fun visitArrayLiteral(ctx: BaseParser.ArrayLiteralContext): ArrayLiteralNode {
        val elements = ctx.elements.map { it.accept(this) }
        return ArrayLiteralNode(ctx.range, elements)
    }

    override fun visitBooleanLiteral(ctx: BaseParser.BooleanLiteralContext): BooleanLiteralNode {
        return BooleanLiteralNode(ctx.range, ctx.value.text.toBoolean())
    }

    override fun visitIntLiteral(ctx: BaseParser.IntLiteralContext): IntLiteralNode {
        return IntLiteralNode(ctx.range, ctx.value.text.toLong())
    }

    override fun visitFloatLiteral(ctx: BaseParser.FloatLiteralContext): FloatLiteralNode {
        return FloatLiteralNode(ctx.range, ctx.value.text.toDouble())
    }

    override fun visitStringLiteral(ctx: BaseParser.StringLiteralContext): StringLiteralNode {
        return StringLiteralNode(ctx.range, ctx.value.text)
    }

    override fun visitUnitLiteral(ctx: BaseParser.UnitLiteralContext): UnitLiteralNode {
        return UnitLiteralNode(ctx.range)
    }

    // type

    override fun visitArrayType(ctx: BaseParser.ArrayTypeContext): ArrayTypeNode {
        val elementType = ctx.elementType.accept(this) as Node
        return ArrayTypeNode(ctx.range, elementType)
    }

    //

    override fun visitReference(ctx: BaseParser.ReferenceContext): ReferenceNode {
        val name = ctx.name.text
        return ReferenceNode(ctx.range, name)
    }

    override fun visitClassDeclaration(ctx: BaseParser.ClassDeclarationContext): ClassDeclarationNode {
        val members = ctx.members.map { it.accept(this) as Node }

        val fields = members.filterIsInstance<FieldNode>()
        val methods = members.filterIsInstance<FunctionDeclarationNode>()

        return ClassDeclarationNode(ctx.range, ctx.name.text, fields, methods)
    }

    override fun visitMethodCall(ctx: BaseParser.MethodCallContext): MethodCallNode {
        val arguments = ctx.arguments.map { it.accept(this) }
        val receiver = ctx.receiver.accept(this)

        return MethodCallNode(ctx.range, ctx.callee.text, arguments, receiver)
    }

    override fun visitFieldMember(ctx: BaseParser.FieldMemberContext): FieldNode {
        val type = ctx.type.accept(this) as Node

        return FieldNode(ctx.range, ctx.name.text, type)
    }

    override fun visitThisLiteral(ctx: BaseParser.ThisLiteralContext): ThisNode {
        return ThisNode(ctx.range)
    }


}