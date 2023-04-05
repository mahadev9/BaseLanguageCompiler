package edu.udel.blc.ast


class BlockNode(
    override val range: IntRange,
    val statements: List<Node>,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}