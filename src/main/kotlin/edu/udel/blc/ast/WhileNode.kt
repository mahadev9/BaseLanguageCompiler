package edu.udel.blc.ast


class WhileNode(
    override val range: IntRange,
    val condition: Node,
    val body: Node,
) : Node