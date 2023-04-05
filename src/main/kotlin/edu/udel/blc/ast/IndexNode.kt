package edu.udel.blc.ast


class IndexNode(
    override val range: IntRange,
    val expression: Node,
    val index: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}