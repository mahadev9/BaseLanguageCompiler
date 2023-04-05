package edu.udel.blc.ast


class StringLiteralNode(
    override val range: IntRange,
    val value: String,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is StringLiteralNode &&
                it.range == this.range &&
                it.value == this.value} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}