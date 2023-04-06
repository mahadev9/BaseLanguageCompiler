package edu.udel.blc.ast


class AssignmentNode(
    override val range: IntRange,
    val lvalue: Node,
    val expression: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is AssignmentNode &&
                it.range == this.range &&
                it.lvalue == this.lvalue &&
                it.expression == this.expression} == true)
    }

    override fun toString(): String {
        return "{AssignmentNode (range = $range," +
                "lvalue = ${lvalue.toString()}," +
                "expression = ${expression.toString()})}"
    }
}