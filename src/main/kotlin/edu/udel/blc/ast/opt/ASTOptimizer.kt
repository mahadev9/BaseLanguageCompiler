package edu.udel.blc.ast.opt

import edu.udel.blc.ast.*
import edu.udel.blc.ast.BinaryOperator.*
import edu.udel.blc.util.visitor.ValuedVisitor

class ExpressionOptimizer : ValuedVisitor<Node, Node>() {

    init {
        // Add implementation for ALL AST Nodes types
        register(IntLiteralNode::class.java, ::intLiteral)

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

    private fun binaryExpression(node: BinaryExpressionNode): Node {
        return when (node.operator) {
            ADDITION -> addition(node)
            SUBTRACTION -> subtraction(node)
            MULTIPLICATION -> multiplication(node)
            REMAINDER -> remainder(node)
            EQUAL_TO -> TODO()
            NOT_EQUAL_TO -> TODO()
            GREATER_THAN -> TODO()
            GREATER_THAN_OR_EQUAL_TO -> TODO()
            LESS_THAN -> TODO()
            LESS_THAN_OR_EQUAL_TO -> TODO()
            LOGICAL_CONJUNCTION -> TODO()
            LOGICAL_DISJUNCTION -> TODO()
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

    private fun unitLiteral(node: UnitLiteralNode): Node {
        // return a copy of the literal node
        return UnitLiteralNode(
            range = node.range
        )
    }

}