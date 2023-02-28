package edu.udel.blc.ast


class FunctionDeclarationNode(
    override val range: IntRange,
    val name: String,
    val parameters: List<ParameterNode>,
    val returnType: Node,
    val body: BlockNode,
) : Node