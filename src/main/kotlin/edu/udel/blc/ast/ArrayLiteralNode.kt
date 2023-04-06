package edu.udel.blc.ast


class ArrayLiteralNode(
    override val range: IntRange,
    val elements: List<Node>,
) : Node {

    init {
        require(elements.isNotEmpty()) { "must contain at least one element" }
    }

    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ArrayLiteralNode && it.range == this.range &&
                it.elements == this.elements} == true)
    }

    override fun toString(): String {
        return "{ArrayLiteralNode (range = $range," +
                "elements = ${elements.toString()})}"
    }

}