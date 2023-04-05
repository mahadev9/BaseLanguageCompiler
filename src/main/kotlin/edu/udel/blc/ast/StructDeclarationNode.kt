package edu.udel.blc.ast


class StructDeclarationNode(
    override val range: IntRange,
    val name: String,
    val fields: List<FieldNode>,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is StructDeclarationNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.fields == this.fields} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}