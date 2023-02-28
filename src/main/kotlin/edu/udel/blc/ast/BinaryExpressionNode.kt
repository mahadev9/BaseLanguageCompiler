package edu.udel.blc.ast


class BinaryExpressionNode(
    override val range: IntRange,
    val operator: BinaryOperator,
    val left: Node,
    val right: Node,
) : Node