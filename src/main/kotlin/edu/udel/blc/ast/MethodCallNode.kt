package edu.udel.blc.ast

class MethodCallNode (
    override val range: IntRange,
    val callee: String,
    val arguments: List<Node>,
    val receiver: Node
): Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "{MethodCallNode (range = $range," +
                "callee = $callee," +
                "arguments = ${arguments.toString()}," +
                "receiver = ${receiver.toString()}}"
    }
}