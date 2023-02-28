package edu.udel.blc.ast


class CallNode(
    override val range: IntRange,
    val callee: Node,
    val arguments: List<Node>,
) : Node