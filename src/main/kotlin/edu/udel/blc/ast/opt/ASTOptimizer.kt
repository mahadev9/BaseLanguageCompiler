package edu.udel.blc.ast.opt

import edu.udel.blc.ast.*
import edu.udel.blc.ast.BinaryOperator.*
import edu.udel.blc.util.visitor.ValuedVisitor

class ExpressionOptimizer : ValuedVisitor<Node, Node>() {

    init {
        // Add implementation for ALL AST Nodes types
        register(IntLiteralNode::class.java, ::intLiteral)
        register(BooleanLiteralNode::class.java, ::booleanLiteral)
        register(StringLiteralNode::class.java, ::stringLiteral)
        register(ExpressionStatementNode::class.java, ::expressionStatement)
        register(FieldNode::class.java, ::fieldNode)
        register(FieldSelectNode::class.java, ::fieldSelect)
        register(IndexNode::class.java, ::index)
        register(ParameterNode::class.java, ::parameter)
        register(ReferenceNode::class.java, ::reference)
        register(ReturnNode::class.java, ::_return)
        register(UnaryExpressionNode::class.java, ::unaryExpression)
        register(ArrayLiteralNode::class.java, ::arrayLiteral)

        // Add
        register(BinaryExpressionNode::class.java, ::binaryExpression)

        register(UnitLiteralNode::class.java, ::unitLiteral)

    }

    private fun intLiteral(node: IntLiteralNode): Node{
        // return a copy of the literal node
        return IntLiteralNode(
            range = node.range,
            value = node.value)
    }
    private fun booleanLiteral(node: BooleanLiteralNode): Node{
        return BooleanLiteralNode(
            range = node.range,
            value = node.value
        )
    }
    private fun stringLiteral(node: StringLiteralNode): Node{
        return StringLiteralNode(
            range = node.range,
            value = node.value
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

    private fun multiplication(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return when {
            // if left and right children are both int literals, replace the node with the result of the operation
            left is IntLiteralNode && right is IntLiteralNode -> {
                IntLiteralNode(
                    range = -1..-1, // what should the range of the new node be? How does this impact error reporting?
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

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }

    private fun notEqualTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }

    private fun greaterThan(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }

    private fun greaterThanOrEqualTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }
    private fun lessThan(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }

    private fun lessThanOrEqualTo(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }
    private fun logicalConjunction(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
        )
    }
    private fun logicalDisjunction(node: BinaryExpressionNode): Node{
        val left = apply(node.left)
        val right = apply(node.right)

        return BinaryExpressionNode(
            range = node.range,
            operator = node.operator,
            left = left,
            right = right
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
    private fun index(node:IndexNode): Node{
        val expression = apply(node.expression)
        val index = apply(node.index)
        return IndexNode(
            range = node.range,
            expression = expression,
            index = index
        )
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

    private fun _return (node:ReturnNode): Node{
        val expression = node.expression?.let { apply(node.expression) }
        return ReturnNode(
            range = node.range,
            expression = expression,
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

    private fun arrayLiteral(node:ArrayLiteralNode):Node{
        val elements = node.elements.map { n -> apply(n) }
        return ArrayLiteralNode(
            range = node.range,
            elements = elements
        )
    }


    private fun unitLiteral(node: UnitLiteralNode): Node {
        // return a copy of the literal node
        return UnitLiteralNode(
            range = node.range
        )
    }

}