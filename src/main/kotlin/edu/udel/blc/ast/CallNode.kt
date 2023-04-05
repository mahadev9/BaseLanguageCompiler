package edu.udel.blc.ast


class CallNode(
    override val range: IntRange,
    val callee: Node,
    val arguments: List<Node>,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}