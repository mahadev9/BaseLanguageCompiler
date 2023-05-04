package edu.udel.blc.semantic_analysis

import edu.udel.blc.ast.*
import edu.udel.blc.semantic_analysis.scope.*
import edu.udel.blc.util.uranium.Attribute
import edu.udel.blc.util.uranium.Reactor
import java.util.function.Consumer


class ResolveReferences(
    private val reactor: Reactor,
) : Node.Visitor<Unit, Scope>, Consumer<CompilationUnitNode> {


    override fun accept(node: CompilationUnitNode) {
        node.accept(this, BuiltinScope)
    }

    override fun arrayLiteral(node: ArrayLiteralNode, arg: Scope) {
        node.elements.forEach { it.accept(this, arg) }
    }

    override fun arrayType(node: ArrayTypeNode, arg: Scope) {
        node.elementType.accept(this, arg)
    }

    override fun assignment(node: AssignmentNode, arg: Scope) {
        node.expression.accept(this, arg)
        node.lvalue.accept(this, arg)
    }

    override fun binaryExpression(node: BinaryExpressionNode, arg: Scope) {
        node.left.accept(this, arg)
        node.right.accept(this, arg)
    }

    override fun block(node: BlockNode, arg: Scope) {
        val blockScope = LocalScope(arg)
        node.statements.forEach { it.accept(this, blockScope) }
    }

    override fun booleanLiteral(node: BooleanLiteralNode, arg: Scope) {

    }

    override fun call(node: CallNode, arg: Scope) {
        node.callee.accept(this, arg)
        node.arguments.forEach { it.accept(this, arg) }
    }

    override fun compilationUnit(node: CompilationUnitNode, arg: Scope) {
        val globalScope = GlobalScope(arg)
        node.statements.forEach { it.accept(this, globalScope) }
    }

    override fun expressionStatement(node: ExpressionStatementNode, arg: Scope) {
        node.expression.accept(this, arg)
    }

    override fun field(node: FieldNode, arg: Scope) {
        val symbol = FieldSymbol(node.name, arg)
        arg.declare(symbol)

        reactor.supply(
            name = "field: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }

        node.type.accept(this, arg)
    }

    override fun fieldSelect(node: FieldSelectNode, arg: Scope) {
        node.expression.accept(this, arg)
    }

    override fun functionDeclaration(node: FunctionDeclarationNode, arg: Scope) {
        node.returnType.accept(this, arg)

        if (arg is ClassSymbol) {
            val containingScope = MethodSymbol(node.name, arg)
            arg.declare(containingScope)

            reactor.supply(
                name = "function declaration: set symbol",
                attribute = Attribute(node, "symbol")
            ) {
                containingScope
            }

            node.parameters.forEach { it.accept(this, containingScope) }
            node.body.accept(this, containingScope)
        } else {
            val containingScope = FunctionSymbol(node.name, arg)
            arg.declare(containingScope)

            reactor.supply(
                name = "function declaration: set symbol",
                attribute = Attribute(node, "symbol")
            ) {
                containingScope
            }

            node.parameters.forEach { it.accept(this, containingScope) }
            node.body.accept(this, containingScope)
        }
    }

    override fun `if`(node: IfNode, arg: Scope) {
        node.condition.accept(this, arg)
        node.thenStatement.accept(this, arg)
        node.elseStatement?.accept(this, arg)
    }

    override fun index(node: IndexNode, arg: Scope) {
        node.expression.accept(this, arg)
        node.index.accept(this, arg)
    }

    override fun intLiteral(node: IntLiteralNode, arg: Scope) {

    }

    override fun floatLiteral(node: FloatLiteralNode, arg: Scope) {

    }

    override fun parameter(node: ParameterNode, arg: Scope) {
        val symbol = VariableSymbol(node.name, arg)
        arg.declare(symbol)

        reactor.supply(
            name = "parameter: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }

        node.type.accept(this, arg)
    }

    override fun reference(node: ReferenceNode, arg: Scope) {
        val symbol = arg.lookup(node.name)

        reactor.supply(
            name = "reference: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            when (symbol) {
                null -> SemanticError(node, "unknown identifier: ${node.name}")
                else -> symbol
            }
        }

    }

    override fun `return`(node: ReturnNode, arg: Scope) {
        val function = containingFunction(arg)

        reactor.supply(
            name = "return: set containing function",
            attribute = Attribute(node, "containingFunction")
        ) {
            when (function) {
                null -> SemanticError(node, "return outside of function")
                else -> function
            }
        }

        node.expression?.accept(this, arg)
    }

    override fun stringLiteral(node: StringLiteralNode, arg: Scope) {

    }

    override fun structDeclaration(node: StructDeclarationNode, arg: Scope) {
        val structScope = StructSymbol(node.name, arg)
        arg.declare(structScope)

        reactor.supply(
            name = "struct declaration: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            structScope
        }

        node.fields.forEach { it.accept(this, structScope) }
    }

    override fun unaryExpression(node: UnaryExpressionNode, arg: Scope) {
        node.operand.accept(this, arg)
    }

    override fun unitLiteral(node: UnitLiteralNode, arg: Scope) {

    }

    override fun variableDeclaration(node: VariableDeclarationNode, arg: Scope) {

        val symbol = VariableSymbol(node.name, arg)
        arg.declare(symbol)

        reactor.supply(
            name = "variable declaration: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }

        node.type.accept(this, arg)
        node.initializer.accept(this, arg)
    }

    override fun `while`(node: WhileNode, arg: Scope) {
        node.condition.accept(this, arg)
        node.body.accept(this, arg)
    }

    private fun containingFunction(start: Scope): FunctionSymbol? {
        var scope: Scope? = start

        while (scope != null) {
            if (scope is FunctionSymbol) return scope
            scope = scope.containingScope
        }

        return null
    }

    override fun classDeclaration(node: ClassDeclarationNode, arg: Scope) {
        val classScope = ClassSymbol(node.name, containingScope = arg)
        arg.declare(classScope)

        reactor.supply(
            name = "class declaration: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            classScope
        }

        node.fields.forEach { it.accept(this, classScope) }
        node.methods.forEach { it.accept(this, classScope) }
    }

    override fun methodCall(node: MethodCallNode, arg: Scope) {
        node.arguments.forEach{ it.accept(this, arg) }
        node.receiver.accept(this, arg)
    }
}
