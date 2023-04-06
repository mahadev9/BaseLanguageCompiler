package edu.udel.blc.ast


class ParameterNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ParameterNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.type == this.type} == true)
    }

    override fun toString(): String {
        return "{ParameterNode (range = $range," +
                "name = $name," +
                "type = ${type.toString()})}"
    }
}