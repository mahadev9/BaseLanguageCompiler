package edu.udel.blc.ast


class ArrayLiteralNode(
    override val range: IntRange,
    val elements: List<Node>,
) : Node {

    init {
        require(elements.isNotEmpty()) { "must contain at least one element" }
    }

}