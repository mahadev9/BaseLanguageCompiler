package edu.udel.blc.ast


class FieldSelectNode(
    override val range: IntRange,
    val expression: Node,
    val name: String,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}