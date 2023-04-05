package edu.udel.blc.ast


class CompilationUnitNode(
    override val range: IntRange,
    val statements: List<Node>,
) : Node {
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}