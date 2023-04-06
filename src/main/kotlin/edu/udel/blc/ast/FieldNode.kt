package edu.udel.blc.ast


class FieldNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is FieldNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.type == this.type} == true)
    }

    override fun toString(): String {
        return "{FieldNode (range = $range," +
                "name = $name," +
                "type = ${type.toString()})}"
    }
}