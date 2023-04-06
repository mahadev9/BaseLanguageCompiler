package edu.udel.blc.ast


class CallNode(
    override val range: IntRange,
    val callee: Node,
    val arguments: List<Node>,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is CallNode &&
                it.range == this.range &&
                it.callee == this.callee &&
                it.arguments == this.arguments} == true)
    }

    override fun toString(): String {
        return "{CallNode (range = $range," +
                "callee = ${callee.toString()}," +
                "arguments = ${arguments.toString()})}"
    }
}