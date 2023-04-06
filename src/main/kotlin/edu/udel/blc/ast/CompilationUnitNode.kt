package edu.udel.blc.ast


class CompilationUnitNode(
    override val range: IntRange,
    val statements: List<Node>,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is CompilationUnitNode &&
                it.range == this.range &&
                it.statements == this.statements} == true)
    }

    override fun toString(): String {
        return "{CompilationUnitNode (range = $range," +
                "statements = ${statements.toString()})}"
    }
}