package edu.udel.blc.ast


class ReturnNode(
    override val range: IntRange,
    val expression: Node?,
) : Node