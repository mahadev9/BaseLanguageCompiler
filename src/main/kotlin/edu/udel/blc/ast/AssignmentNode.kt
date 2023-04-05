package edu.udel.blc.ast


class AssignmentNode(
    override val range: IntRange,
    val lvalue: Node,
    val expression: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}