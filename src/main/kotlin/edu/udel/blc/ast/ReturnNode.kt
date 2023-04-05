package edu.udel.blc.ast


class ReturnNode(
    override val range: IntRange,
    val expression: Node?,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}