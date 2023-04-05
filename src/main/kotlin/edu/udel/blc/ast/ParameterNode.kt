package edu.udel.blc.ast


class ParameterNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}