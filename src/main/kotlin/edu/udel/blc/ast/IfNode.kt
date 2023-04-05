package edu.udel.blc.ast


class IfNode(
    override val range: IntRange,
    val condition: Node,
    val thenStatement: Node,
    val elseStatement: Node?,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is IfNode &&
                it.range == this.range &&
                it.condition == this.condition &&
                it.thenStatement == this.thenStatement &&
                it.elseStatement == this.elseStatement } == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}