package edu.udel.blc.semantic_analysis.scope

class GlobalScope(
    override val containingScope: Scope
) : Scope() {
    override fun toString(): String = buildString {
        append("Global")
        symbols.joinTo(this, prefix = "{", postfix = "}")
        append(" : ")
        append(containingScope)
    }
}