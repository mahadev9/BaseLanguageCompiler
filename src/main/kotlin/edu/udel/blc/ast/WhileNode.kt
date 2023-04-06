package edu.udel.blc.ast


class WhileNode(
    override val range: IntRange,
    val condition: Node,
    val body: Node,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is WhileNode &&
                it.range == this.range &&
                it.condition == this.condition &&
                it.body == this.body} == true)
    }

    override fun toString(): String {
        return "{WhileNode (range = $range," +
                "condition = ${condition.toString()}" +
                "body = ${body.toString()},)}"
    }
}