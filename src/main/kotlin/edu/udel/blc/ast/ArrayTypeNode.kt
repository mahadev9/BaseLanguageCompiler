package edu.udel.blc.ast


class ArrayTypeNode(
    override val range: IntRange,
    val elementType: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ArrayTypeNode && it.range == this.range &&
                it.elementType == this.elementType} == true)
    }

    override fun toString(): String {
        return "{ArrayTypeNode (range = $range," +
                " elementType = ${elementType.toString()})}"
    }
}