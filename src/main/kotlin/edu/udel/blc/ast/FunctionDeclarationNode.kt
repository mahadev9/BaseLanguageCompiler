package edu.udel.blc.ast


class FunctionDeclarationNode(
    override val range: IntRange,
    val name: String,
    val parameters: List<ParameterNode>,
    val returnType: Node,
    val body: BlockNode,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is FunctionDeclarationNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.returnType == this.returnType &&
                it.body == this.body } == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}