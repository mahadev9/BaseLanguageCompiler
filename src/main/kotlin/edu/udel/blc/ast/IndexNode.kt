package edu.udel.blc.ast


class IndexNode(
    override val range: IntRange,
    val expression: Node,
    val index: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is IndexNode &&
                it.range == this.range &&
                it.expression == this.expression &&
                it.index == this.index} == true)
    }
    override fun toString(): String {
        return "{IndexNode (range = $range," +
                "expression = ${expression.toString()}," +
                "index = ${index.toString()})}"
    }
}