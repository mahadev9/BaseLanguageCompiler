package edu.udel.blc.ast


class FieldSelectNode(
    override val range: IntRange,
    val expression: Node,
    val name: String,
) : Node