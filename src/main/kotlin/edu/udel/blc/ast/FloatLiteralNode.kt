package edu.udel.blc.ast

class FloatLiteralNode (
    override val range: IntRange,
    val value: Double,
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is FloatLiteralNode &&
                it.range == this.range &&
                it.value == this.value} == true)
    }

    override fun toString(): String {
        return "{FloatLiteralNode (range = $range," +
                "value = $value,)}"
    }
}