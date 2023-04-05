package edu.udel.blc.ast


class ReferenceNode(
    override val range: IntRange,
    val name: String,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ReferenceNode &&
                it.range == this.range &&
                it.name == this.name} == true)
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}