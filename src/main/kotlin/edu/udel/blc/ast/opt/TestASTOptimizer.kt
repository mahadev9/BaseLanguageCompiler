package edu.udel.blc.ast.opt

import edu.udel.blc.ast.BinaryExpressionNode
import edu.udel.blc.ast.BinaryOperator
import edu.udel.blc.ast.IntLiteralNode

private fun additionTest(): Unit{
    val node = BinaryExpressionNode(
        range = IntRange.EMPTY,
        operator = BinaryOperator.ADDITION,
        left = IntLiteralNode(IntRange.EMPTY, 5),
        right = IntLiteralNode(IntRange.EMPTY, 5)
    )
    val optimized = ExpressionOptimizer().apply(node)
    val expected = IntLiteralNode(-1..-1, 10)

    check( optimized == expected)
}

fun main() {
    additionTest()
}