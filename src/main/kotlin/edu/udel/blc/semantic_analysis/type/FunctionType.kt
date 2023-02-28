package edu.udel.blc.semantic_analysis.type

class FunctionType(
    val parameterTypes: LinkedHashMap<String, Type>,
    val returnType: Type,
) : Type {

    override fun toString() = buildString {
        parameterTypes.entries.joinTo(this, prefix = "(", postfix = ") -> ") { (name, type) -> "$name : $type" }
        append(returnType)
    }

}