package edu.udel.blc.ast


class AssignmentNode(
    override val range: IntRange,
    val lvalue: Node,
    val expression: Node,
) : Node