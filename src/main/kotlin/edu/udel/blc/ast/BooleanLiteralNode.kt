package edu.udel.blc.ast


class BooleanLiteralNode(
    override val range: IntRange,
    val value: Boolean,
) : Node