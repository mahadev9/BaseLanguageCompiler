package edu.udel.blc.ast


class FieldNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
) : Node