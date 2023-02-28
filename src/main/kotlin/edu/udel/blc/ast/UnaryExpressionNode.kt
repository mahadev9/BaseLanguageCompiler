package edu.udel.blc.ast


class UnaryExpressionNode(
    override val range: IntRange,
    val operator: UnaryOperator,
    val operand: Node,
) : Node