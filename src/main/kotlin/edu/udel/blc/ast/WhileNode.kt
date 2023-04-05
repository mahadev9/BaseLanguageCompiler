package edu.udel.blc.ast


class WhileNode(
    override val range: IntRange,
    val condition: Node,
    val body: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}