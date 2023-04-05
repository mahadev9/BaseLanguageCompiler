package edu.udel.blc.ast


class UnitLiteralNode(
    override val range: IntRange,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is UnitLiteralNode &&
                it.range == this.range} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}