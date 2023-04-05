package edu.udel.blc.ast


class ReferenceNode(
    override val range: IntRange,
    val name: String,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}