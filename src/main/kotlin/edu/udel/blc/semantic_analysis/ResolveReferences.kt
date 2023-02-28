package edu.udel.blc.semantic_analysis

import edu.udel.blc.ast.*
import edu.udel.blc.semantic_analysis.scope.*
import edu.udel.blc.util.uranium.Attribute
import edu.udel.blc.util.uranium.Reactor
import edu.udel.blc.util.visitor.ReflectiveAccessorWalker
import edu.udel.blc.util.visitor.WalkVisitType.POST_VISIT
import edu.udel.blc.util.visitor.WalkVisitType.PRE_VISIT
import java.util.function.Consumer


class ResolveReferences(
    private val reactor: Reactor,
) : Consumer<CompilationUnitNode> {

    private var scope: Scope = BuiltinScope

    val walker = ReflectiveAccessorWalker(Node::class.java, PRE_VISIT, POST_VISIT).apply {

        register(CompilationUnitNode::class.java, PRE_VISIT, ::enterCompilationUnit)
        register(CompilationUnitNode::class.java, POST_VISIT, ::exitCompilationUnit)

        register(FunctionDeclarationNode::class.java, PRE_VISIT, ::enterFunctionDeclaration)
        register(FunctionDeclarationNode::class.java, POST_VISIT, ::exitFunctionDeclaration)

        register(ParameterNode::class.java, PRE_VISIT, ::parameter)
        register(VariableDeclarationNode::class.java, PRE_VISIT, ::variableDeclaration)

        register(StructDeclarationNode::class.java, PRE_VISIT, ::enterStructDeclaration)
        register(StructDeclarationNode::class.java, POST_VISIT, ::exitStructDeclaration)
        register(FieldNode::class.java, PRE_VISIT, ::field)

        register(BlockNode::class.java, PRE_VISIT, ::enterBlock)
        register(BlockNode::class.java, POST_VISIT, ::exitBlock)

        register(ReferenceNode::class.java, PRE_VISIT, ::reference)

        register(ReturnNode::class.java, PRE_VISIT, ::returnStmt)
    }

    override fun accept(compilationUnit: CompilationUnitNode) {
        walker.accept(compilationUnit)
    }

    private fun enterCompilationUnit(node: CompilationUnitNode) {
        val globalScope = GlobalScope(scope)
        scope = globalScope
    }

    private fun exitCompilationUnit(node: CompilationUnitNode) {
        scope = scope.containingScope!!
    }

    private fun enterFunctionDeclaration(node: FunctionDeclarationNode) {
        val functionScope = FunctionSymbol(node.name, scope)
        scope.declare(functionScope)

        reactor.supply(
            name = "function declaration: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            functionScope
        }

        scope = functionScope
    }

    private fun exitFunctionDeclaration(node: FunctionDeclarationNode) {
        scope = scope.containingScope!!
    }

    private fun enterBlock(node: BlockNode) {
        val localScope = LocalScope(scope)
        scope = localScope
    }

    private fun exitBlock(node: BlockNode) {
        scope = scope.containingScope!!
    }

    private fun parameter(node: ParameterNode) {
        val symbol = VariableSymbol(node.name, scope)
        scope.declare(symbol)

        reactor.supply(
            name = "parameter: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }
    }

    private fun variableDeclaration(node: VariableDeclarationNode) {
        val symbol = VariableSymbol(node.name, scope)
        scope.declare(symbol)

        reactor.supply(
            name = "variable declaration: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }
    }

    private fun enterStructDeclaration(node: StructDeclarationNode) {

        val symbol = StructSymbol(node.name, scope)
        scope.declare(symbol)

        reactor.supply(
            name = "struct declaration: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }

        scope = symbol
    }

    private fun exitStructDeclaration(node: StructDeclarationNode) {
        scope = scope.containingScope!!
    }

    private fun field(node: FieldNode) {

        val symbol = FieldSymbol(node.name, scope)
        scope.declare(symbol)

        reactor.supply(
            name = "field: set symbol",
            attribute = Attribute(node, "symbol")
        ) {
            symbol
        }
    }

    private fun reference(node: ReferenceNode) {

        val symbol = scope.lookup(node.name)

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

    private fun returnStmt(node: ReturnNode) {

        val function = containingFunction(scope)

        reactor.supply(
            name = "return: set containing function",
            attribute = Attribute(node, "containingFunction")
        ) {
            when (function) {
                null -> SemanticError(node, "return outside of function")
                else -> function
            }
        }

    }

    private fun containingFunction(start: Scope): FunctionSymbol? {
        var scope: Scope? = start

        while (scope != null) {
            if (scope is FunctionSymbol) return scope
            scope = scope.containingScope
        }

        return null
    }

}
