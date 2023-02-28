package edu.udel.blc.ast


class ReferenceNode(
    override val range: IntRange,
    val name: String,
) : Node