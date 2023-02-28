package edu.udel.blc.ast


class CompilationUnitNode(
    override val range: IntRange,
    val statements: List<Node>,
) : Node