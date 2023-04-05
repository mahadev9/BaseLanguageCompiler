package edu.udel.blc.ast


class BooleanLiteralNode(
    override val range: IntRange,
    val value: Boolean,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is BooleanLiteralNode &&
                it.range == this.range &&
                it.value == this.value} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}