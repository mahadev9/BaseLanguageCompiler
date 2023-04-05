package edu.udel.blc.ast


class StringLiteralNode(
    override val range: IntRange,
    val value: String,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}