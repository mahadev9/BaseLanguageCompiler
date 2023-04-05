package edu.udel.blc.ast


class ReturnNode(
    override val range: IntRange,
    val expression: Node?,
) : Node {
    override fun equals(other: Any?): Boolean {
    return (other?.let {it is ReturnNode && it.range == this.range &&
            it.expression == this.expression} == true)
    }
}