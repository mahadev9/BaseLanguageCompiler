package edu.udel.blc.ast


class ArrayTypeNode(
    override val range: IntRange,
    val elementType: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}