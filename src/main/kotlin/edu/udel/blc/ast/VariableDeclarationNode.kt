package edu.udel.blc.ast


class VariableDeclarationNode(
    override val range: IntRange,
    val name: String,
    val type: Node,
    val initializer: Node,
) : Node