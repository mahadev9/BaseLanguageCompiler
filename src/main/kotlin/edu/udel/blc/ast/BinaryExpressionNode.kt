package edu.udel.blc.ast


class BinaryExpressionNode(
    override val range: IntRange,
    val operator: BinaryOperator,
    val left: Node,
    val right: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}