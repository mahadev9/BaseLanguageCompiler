package edu.udel.blc.ast


class BooleanLiteralNode(
    override val range: IntRange,
    val value: Boolean,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}