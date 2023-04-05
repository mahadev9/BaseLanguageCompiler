package edu.udel.blc.ast


class StructDeclarationNode(
    override val range: IntRange,
    val name: String,
    val fields: List<FieldNode>,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }
}