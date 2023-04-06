package edu.udel.blc.ast


class ReturnNode(
    override val range: IntRange,
    val expression: Node?,
) : Node {
    override fun equals(other: Any?): Boolean {
    return (other?.let {it is ReturnNode && it.range == this.range &&
            it.expression == this.expression} == true)
    }

    override fun toString(): String {
        // TODO: expression is nullable; is it safe to call toString()
        return "{ReturnNode (range = $range," +
                "expression = ${expression.toString()})}"
    }
}