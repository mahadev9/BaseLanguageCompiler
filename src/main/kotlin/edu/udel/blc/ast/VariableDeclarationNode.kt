package edu.udel.blc.ast


class VariableDeclarationNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
    val initializer: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is VariableDeclarationNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.type == this.type &&
                it.initializer == this.initializer} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}