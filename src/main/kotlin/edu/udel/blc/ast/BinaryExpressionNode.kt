package edu.udel.blc.ast


class BinaryExpressionNode(
    override val range: IntRange,
    val operator: BinaryOperator,
    val left: Node,
    val right: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is BinaryExpressionNode &&
                it.range == this.range &&
                it.operator == this.operator &&
                it.left == this.left &&
                it.right == this.left } == true)
    }

    override fun toString(): String {
        return "{BinaryExpressionNode (range = $range," +
                "operator = ${operator.toString()}," +
                "left = ${left.toString()}," +
                "right = ${right.toString()})}"
    }
}