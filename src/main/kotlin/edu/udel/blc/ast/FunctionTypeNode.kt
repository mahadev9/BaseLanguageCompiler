package edu.udel.blc.ast

class FunctionTypeNode(
    override val range: IntRange,
    val from: List<Node>,
    val to: Node
): Node{
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is FunctionTypeNode &&
                it.range == this.range &&
                it.from == this.from &&
                it.to == this.to } == true)
    }

    override fun toString(): String {
        return "{FunctionTypeNode (range = $range,"+
                "from = ${from.toString()}," +
                "to = ${to.toString()})}"

    }

}