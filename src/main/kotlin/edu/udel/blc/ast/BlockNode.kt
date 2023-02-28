package edu.udel.blc.ast


class BlockNode(
    override val range: IntRange,
    val statements: List<Node>,
) : Node