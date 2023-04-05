package edu.udel.blc.ast


class IntLiteralNode(
    override val range: IntRange,
    val value: Long,
) : Node {
    override fun equals(other: Any?): Boolean {
    return (other?.let {it is IntLiteralNode && it.range == this.range &&
            it.value == this.value} == true)
    }

    override fun toString(): String {
        return "IntLiteralNode {range = $range, value = $value}"
    }
}