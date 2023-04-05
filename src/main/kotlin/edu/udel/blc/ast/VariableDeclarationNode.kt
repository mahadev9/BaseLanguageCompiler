package edu.udel.blc.ast


class VariableDeclarationNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
    val initializer: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}