package edu.udel.blc.ast


class ClassDeclarationNode(
    override val range: IntRange,
    val name: String,
    val block: BlockNode,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ClassDeclarationNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.block == this.block } == true)
    }

    override fun toString(): String {
        return "{ClassDeclarationNode (range = $range," +
                "name = $name," +
                "block = ${block.toString()})}"
    }
}