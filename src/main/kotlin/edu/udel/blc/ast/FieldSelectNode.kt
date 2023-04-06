package edu.udel.blc.ast


class FieldSelectNode(
    override val range: IntRange,
    val expression: Node,
    val name: String,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is FieldSelectNode &&
                it.range == this.range &&
                it.expression == this.expression &&
                it.name == this.name } == true)
    }

    override fun toString(): String {
        return "{FieldSelectNode (range = $range," +
                "expression = ${expression.toString()}," +
                "name = $name)}"
    }
}