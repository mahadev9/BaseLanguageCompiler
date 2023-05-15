package edu.udel.blc.semantic_analysis.scope


sealed class Scope {

    abstract val containingScope: Scope?

    // Using a LinkedHashMap is important so that the order of parameters and fields is maintained
    val declarations = LinkedHashMap<String, Symbol>()

    // val symbols: List<Symbol>
    //     get() = buildList {
    //         addAll(declarations.entries.map { it.value })
    //         containingScope?.let { scope ->
    //             addAll(scope.symbols)
    //         }
    //     }

    // When containinScope is used, we have scoping issue in bytecode and different behavior in the program
    val symbols: List<Symbol>
        get() {
            return declarations.values.toList() // + containingScope?.symbols.orEmpty()
        }

    open fun declare(symbol: Symbol): Symbol {
        synchronized(declarations) {
            declarations[symbol.name] = symbol
        }
        return symbol
    }

    open fun lookup(name: String): Symbol? {
        synchronized(declarations) {
            return declarations[name] ?: containingScope?.lookup(name)
        }
    }

    val enclosingPath: List<Scope> by lazy {
        buildList {
            var s: Scope? = this@Scope
            while (s != null) {
                add(s)
                s = s.containingScope
            }
        }
    }

}