package edu.udel.blc.ast


class UnaryExpressionNode(
    override val range: IntRange,
    val operator: UnaryOperator,
    val operand: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is UnaryExpressionNode &&
                it.range == this.range &&
                it.operator == this.operator &&
                it.operand == this.operand} == true)
    }

    override fun toString(): String {
        return "{UnaryExpressionNode (range = $range," +
                "operator = $operator," +
                "operand = ${operand.toString()})}"
    }
}