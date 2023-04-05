package edu.udel.blc.ast


class IntLiteralNode(
    override val range: IntRange,
    val value: Long,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}