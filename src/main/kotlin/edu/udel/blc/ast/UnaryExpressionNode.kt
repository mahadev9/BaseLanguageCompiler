package edu.udel.blc.ast


class UnaryExpressionNode(
    override val range: IntRange,
    val operator: UnaryOperator,
    val operand: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}