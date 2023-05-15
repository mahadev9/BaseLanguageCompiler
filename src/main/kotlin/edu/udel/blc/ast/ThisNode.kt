package edu.udel.blc.ast

class ThisNode (
    override val range: IntRange
): Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ThisNode &&
                it.range == this.range } == true)
    }

    override fun toString(): String {
        return "{ThisNode (range = $range)}"
    }
}
