package edu.udel.blc.ast


class ExpressionStatementNode(
    override val range: IntRange,
    val expression: Node,
) : Node