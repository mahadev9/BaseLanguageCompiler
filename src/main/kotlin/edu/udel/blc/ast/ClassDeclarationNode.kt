package edu.udel.blc.ast


class ClassDeclarationNode(
    override val range: IntRange,
    val name: String,
    val fields: List<FieldNode>,
    val methods: List<FunctionDeclarationNode>
) : Node {
    override fun equals(other: Any?): Boolean {
        return (other?.let {it is ClassDeclarationNode &&
                it.range == this.range &&
                it.name == this.name &&
                it.fields == this.fields &&
                it.methods == this.methods } == true)
    }

    override fun toString(): String {
        return "{ClassDeclarationNode (range = $range," +
                "name = $name," +
                "block = ${fields.toString()})" +
                "methods = ${methods.toString()}}"
    }
}