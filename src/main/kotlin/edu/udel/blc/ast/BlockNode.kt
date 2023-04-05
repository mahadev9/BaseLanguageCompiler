package edu.udel.blc.ast


class BlockNode(
    override val range: IntRange,
    val statements: List<Node>,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is BlockNode &&
                it.range == this.range &&
                it.statements == this.statements} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}