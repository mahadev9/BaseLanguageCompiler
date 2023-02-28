package edu.udel.blc.ast


class ArrayTypeNode(
    override val range: IntRange,
    val elementType: Node,
) : Node