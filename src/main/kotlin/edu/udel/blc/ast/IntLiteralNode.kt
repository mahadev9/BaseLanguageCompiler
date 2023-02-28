package edu.udel.blc.ast


class IntLiteralNode(
    override val range: IntRange,
    val value: Long,
) : Node