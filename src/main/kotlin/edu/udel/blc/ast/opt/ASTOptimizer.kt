package edu.udel.blc.ast.opt

import edu.udel.blc.ast.*
import edu.udel.blc.ast.BinaryOperator.*
import edu.udel.blc.util.visitor.ValuedVisitor

class ExpressionOptimizer : ValuedVisitor<Node, Node>() {
    val operatorReductionLimit = 3
    init {
        // TODO: Add implementation for ALL AST Nodes types
        register(ArrayLiteralNode::class.java, ::arrayLiteral)
        register(ArrayTypeNode::class.java, ::arrayType)
        register(AssignmentNode::class.java, ::assignment)
        register(BinaryExpressionNode::class.java, ::binaryExpression)
        register(BlockNode::class.java, ::block)
        register(BooleanLiteralNode::class.java, ::booleanLiteral)
        register(CallNode::class.java, ::call)
        register(CompilationUnitNode::class.java, ::compilationUnit)
        register(ExpressionStatementNode::class.java, ::expressionStatement)
        register(FieldNode::class.java, ::fieldNode)
        register(FieldSelectNode::class.java, ::fieldSelect)
        register(FunctionDeclarationNode::class.java, ::functionDeclaration)
        register(IfNode::class.java, ::`if`)
        register(IndexNode::class.java, ::index)
        register(IntLiteralNode::class.java, ::intLiteral)
        register(ParameterNode::class.java, ::parameter)
        register(ReferenceNode::class.java, ::reference)
        register(ReturnNode::class.java, ::`return`)
        register(StringLiteralNode::class.java, ::stringLiteral)
        register(StructDeclarationNode::class.java, ::structDeclaration)
        register(UnaryExpressionNode::class.java, ::unaryExpression)
        register(UnitLiteralNode::class.java, ::unitLiteral)
        register(VariableDeclarationNode::class.java, ::variableDeclaration)
        register(WhileNode::class.java, ::`while`)
    }
    private fun arrayLiteral(node:ArrayLiteralNode):Node{
        val elements = node.elements.map { n -> apply(n) }
        return ArrayLiteralNode(
            range = node.range,
            elements = elements
        )
    }
    private fun arrayType(node:ArrayTypeNode):Node{
        val elementType = apply(node.elementType)
        return ArrayTypeNode(
            range = node.range,
            elementType = elementType
        )
    }
    private fun assignment(node:AssignmentNode):Node{
        val lvalue = apply(node.lvalue)
        val expression = apply(node.expression)
        return AssignmentNode(
            range = node.range,
            lvalue = lvalue,
            expression = expression
        )
    }
    private fun binaryExpression(node: BinaryExpressionNode): Node {
        return when (node.operator) {
            ADDITION -> addition(node)
            SUBTRACTION -> subtraction(node)
            MULTIPLICATION -> multiplication(node)
            REMAINDER -> remainder(node)
            EQUAL_TO -> equalTo(node)
            NOT_EQUAL_TO -> notEqualTo(node)
            GREATER_THAN -> greaterThan(node)
            GREATER_THAN_OR_EQUAL_TO -> greaterThanOrEqualTo(node)
            LESS_THAN -> lessThan(node)
            LESS_THAN_OR_EQUAL_TO -> lessThanOrEqualTo(node)
            LOGICAL_CONJUNCTION -> logicalConjunction(node)
            LOGICAL_DISJUNCTION -> logicalDisjunction(node)
        }
    }
    private fun addition(node: BinaryExpressionNode): Node {
        // visit left child to get the updated (copy/modified version)
        val left = apply(node.left)
        // visit right child to get the updated (copy/modified version)
        val right = apply(node.right)

        return when {
            // if left and right children are both int literals, replace the node with the result of the operation
            left is IntLiteralNode && right is IntLiteralNode -> {
                if (left.value == 0.toLong()) {
                    return IntLiteralNode(
                        range = -1..-1,
                        value = right.value
                    )
                } else if (right.value == 0.toLong()) {
                    return IntLiteralNode(
                        range = -1..-1,
                        value = left.value
                    )
                }
                IntLiteralNode(
                    range = -1..-1, // what should the range of the new node be? How does this impact error reporting?
                    value = left.value + right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun subtraction(node:BinaryExpressionNode): Node{
        val left = apply (node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                if (left.value == 0.toLong()) {
                    return IntLiteralNode(
                        range = -1..-1,
                        value = -right.value
                    )
                } else if (right.value == 0.toLong()) {
                    return IntLiteralNode(
                        range = -1..-1,
                        value = left.value
                    )
                }
                IntLiteralNode (
                    range = -1..-1,
                    value = left.value - right.value
                )
            }
            else -> {
                BinaryExpressionNode (
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun multiplication(node: BinaryExpressionNode): Node {
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode &&
                    (left.value == 0.toLong() || right.value == 0.toLong()) -> {
                IntLiteralNode(
                    range = -1..-1,
                    value = 0
                )
            }

            left is IntLiteralNode && right is IntLiteralNode && left.value == 1.toLong() -> {
                IntLiteralNode(
                    range = -1..-1,
                    value = right.value
                )
            }

            left is IntLiteralNode && right is IntLiteralNode && right.value == 1.toLong() -> {
                IntLiteralNode(
                    range = -1..-1,
                    value = left.value
                )
            }

            left is IntLiteralNode && right is IntLiteralNode && left.value > 0 && left.value <= operatorReductionLimit -> {
                var result: Long = 0
                for (i in 1 until left.value+1) {
                    result += right.value
                }
                IntLiteralNode(
                    range = -1..-1,
                    value = result
                )
            }

            left is IntLiteralNode && right is IntLiteralNode && right.value > 0 && right.value <= operatorReductionLimit -> {
                var result: Long = 0
                for (i in 1 until right.value+1) {
                    result += left.value
                }
                IntLiteralNode(
                    range = -1..-1,
                    value = result
                )
            }

            left is IntLiteralNode && right is IntLiteralNode -> {
                IntLiteralNode(
                    range = -1..-1,
                    value = left.value * right.value
                )
            }

            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )

            }
        }
    }
    private fun remainder(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            // if left and right children are both int literals, replace the node with the result of the operation
            left is IntLiteralNode && right is IntLiteralNode -> {
                IntLiteralNode(
                    range = -1..-1, // what should the range of the new node be? How does this impact error reporting?
                    value = left.value % right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun equalTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value == right.value
                )
            }
            left is StringLiteralNode && right is StringLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value == right.value
                )
            }
            left is BooleanLiteralNode && right is BooleanLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value == right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun notEqualTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value != right.value
                )
            }
            left is StringLiteralNode && right is StringLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value != right.value
                )
            }
            left is BooleanLiteralNode && right is BooleanLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value != right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun greaterThan(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value > right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun greaterThanOrEqualTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value >= right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun lessThan(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value < right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun lessThanOrEqualTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is IntLiteralNode && right is IntLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value <= right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun logicalConjunction(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is BooleanLiteralNode && right is BooleanLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value && right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun logicalDisjunction(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            left is BooleanLiteralNode && right is BooleanLiteralNode -> {
                BooleanLiteralNode(
                    range = node.range,
                    value = left.value || right.value
                )
            }
            else -> {
                BinaryExpressionNode(
                    range = node.range,
                    operator = node.operator,
                    left = left,
                    right = right
                )
            }
        }
    }
    private fun block(node:BlockNode):Node{
        val statements = node.statements.map { s -> apply(s) }

        var returnExists = false
        val modifiedStatements = statements.filter { it ->
            val foundReturn = returnExists
            if (it is ReturnNode) {
                returnExists = true
            }
            !foundReturn
        }

        if (modifiedStatements.isEmpty()) {
            return UnitLiteralNode(
                node.range
            )
        }

        return BlockNode(
            range = node.range,
            statements = modifiedStatements
        )
    }
    private fun booleanLiteral(node: BooleanLiteralNode): Node{
        return BooleanLiteralNode(
            range = node.range,
            value = node.value
        )
    }
    private fun call(node:CallNode):Node{
        val callee = apply(node.callee)
        val arguments = node.arguments.map { a -> apply(a) }

        return CallNode(
            range = node.range,
            callee = callee,
            arguments = arguments
        )
    }
    private fun compilationUnit(node:CompilationUnitNode):Node{
        val statements = node.statements.map { s -> apply(s) }

        return CompilationUnitNode(
            range = node.range,
            statements = statements
        )
    }
    private fun expressionStatement(node: ExpressionStatementNode): Node{
        val expression = apply(node.expression)
        return ExpressionStatementNode(
            range = node.range,
            expression = expression
        )
    }
    private fun fieldNode(node:FieldNode): Node{
        val type = apply(node.type)
        return FieldNode(
            range = node.range,
            name = node.name,
            type = type
        )
    }
    private fun fieldSelect(node:FieldSelectNode): Node{
        val expression = apply(node.expression)
        return FieldSelectNode(
            range = node.range,
            expression = expression,
            name = node.name
        )
    }
    private fun functionDeclaration(node:FunctionDeclarationNode):Node{
        val parameters = node.parameters.map { p -> apply(p) as ParameterNode}
        val returnType = apply(node.returnType)
        val body = apply(node.body) as BlockNode
        return FunctionDeclarationNode(
            range = node.range,
            name = node.name,
            parameters = parameters,
            returnType = returnType,
            body = body
        )
    }
    private fun `if`(node:IfNode):Node{
        val condition = apply(node.condition)
        val thenStatement = apply(node.thenStatement)
        val elseStatement = node.elseStatement?.let { apply(it) }

        return when{
            condition is BooleanLiteralNode && condition.value ->{
                ExpressionStatementNode(
                    range = node.range,
                    expression = thenStatement
                )
            }
            condition is BooleanLiteralNode && !(condition.value) ->{
                when(elseStatement){
                    null -> UnitLiteralNode(range = node.range)
                    else -> ExpressionStatementNode(
                        range = node.range,
                        expression = elseStatement
                    )
                }
            }
            else -> {
                IfNode(
                    range = node.range,
                    condition = node.condition,
                    thenStatement = thenStatement,
                    elseStatement = elseStatement
                )
            }
        }
    }
    private fun index(node:IndexNode): Node{
        val expression = apply(node.expression)
        val index = apply(node.index)
        return IndexNode(
            range = node.range,
            expression = expression,
            index = index
        )
    }
    private fun intLiteral(node: IntLiteralNode): Node{
        // return a copy of the literal node
        return IntLiteralNode(
            range = node.range,
            value = node.value)
    }
    private fun parameter(node:ParameterNode): Node{
        val type = apply(node.type)
        return ParameterNode(
            range = node.range,
            name = node.name,
            type = type
        )
    }
    private fun reference(node:ReferenceNode): Node{
        return ReferenceNode(
            range = node.range,
            name = node.name,
        )
    }
    private fun `return` (node:ReturnNode): Node{
        val expression = node.expression?.let { apply(node.expression) }
        return ReturnNode(
            range = node.range,
            expression = expression,
        )
    }
    private fun stringLiteral(node: StringLiteralNode): Node{
        return StringLiteralNode(
            range = node.range,
            value = node.value
        )
    }
    private fun structDeclaration(node: StructDeclarationNode): Node {
        val fields = node.fields.map { f -> apply(f) as FieldNode}
        return StructDeclarationNode(
            range = node.range,
            name = node.name,
            fields = fields
        )
    }
    private fun unaryExpression(node:UnaryExpressionNode):Node{
        val operand = apply(node.operand)
        return UnaryExpressionNode(
            range = node.range,
            operator =  node.operator,
            operand = operand
        )
    }
    private fun unitLiteral(node: UnitLiteralNode): Node {
        // return a copy of the literal node
        return UnitLiteralNode(
            range = node.range
        )
    }
    private fun variableDeclaration(node: VariableDeclarationNode): Node {
        val type = apply(node.type)
        val initializer = apply(node.initializer)
        return VariableDeclarationNode(
            range = node.range,
            name = node.name,
            type = type,
            initializer = initializer
        )
    }
    private fun `while`(node: WhileNode): Node {
        val condition = apply(node.condition)
        val body = apply(node.body)
        return when {
            condition is BooleanLiteralNode && !condition.value -> {
                UnitLiteralNode(
                    -1..-1
                )
            }
            else -> {
                return WhileNode(
                    range = node.range,
                    condition = condition,
                    body = body
                )
            }
        }
    }

}