package edu.udel.blc.ast


class ExpressionStatementNode(
    override val range: IntRange,
    val expression: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ExpressionStatementNode &&
                it.range == this.range &&
                it.expression == this.expression} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}