package edu.udel.blc.ast


class ParameterNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
) : Node