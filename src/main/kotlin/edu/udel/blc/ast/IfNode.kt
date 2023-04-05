package edu.udel.blc.ast


class IfNode(
    override val range: IntRange,
    val condition: Node,
    val thenStatement: Node,
    val elseStatement: Node?,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}