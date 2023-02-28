package edu.udel.blc.semantic_analysis.type

class ArrayType(
    val elementType: Type,
) : Type {

    override fun toString(): String = "[$elementType]"

}