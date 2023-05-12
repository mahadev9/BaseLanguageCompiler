package edu.udel.blc.semantic_analysis

import edu.udel.blc.ast.*
import edu.udel.blc.ast.BinaryOperator.*
import edu.udel.blc.ast.UnaryOperator.LOGICAL_COMPLEMENT
import edu.udel.blc.ast.UnaryOperator.NEGATION
import edu.udel.blc.machine_code.bytecode.find
import edu.udel.blc.semantic_analysis.scope.*
import edu.udel.blc.semantic_analysis.type.*
import edu.udel.blc.util.uranium.Attribute
import edu.udel.blc.util.uranium.Reactor
import edu.udel.blc.util.visitor.ReflectiveAccessorWalker
import edu.udel.blc.util.visitor.WalkVisitType.PRE_VISIT
import java.util.function.Consumer


class ResolveTypes(
    private val reactor: Reactor,
) : Consumer<CompilationUnitNode> {

    val walker = ReflectiveAccessorWalker(Node::class.java, PRE_VISIT).apply {

        register(BlockNode::class.java, PRE_VISIT, ::block)
        register(ReturnNode::class.java, PRE_VISIT, ::returnStmt)
        register(IfNode::class.java, PRE_VISIT, ::ifStmt)

        register(FunctionDeclarationNode::class.java, PRE_VISIT, ::functionDeclaration)
        register(ParameterNode::class.java, PRE_VISIT, ::parameterDeclaration)
        register(VariableDeclarationNode::class.java, PRE_VISIT, ::variableDeclaration)
        register(StructDeclarationNode::class.java, PRE_VISIT, ::structDeclaration)
        register(ClassDeclarationNode::class.java, PRE_VISIT, ::classDeclaration)
        register(FieldNode::class.java, PRE_VISIT, ::fieldDeclaration)
        register(ThisNode::class.java, PRE_VISIT, ::thisDeclaration)

        register(ReferenceNode::class.java, PRE_VISIT, ::reference)
        register(CallNode::class.java, PRE_VISIT, ::call)
        register(MethodCallNode::class.java, PRE_VISIT, ::methodCall)

        register(AssignmentNode::class.java, PRE_VISIT, ::assignment)
        register(IndexNode::class.java, PRE_VISIT, ::index)
        register(BinaryExpressionNode::class.java, PRE_VISIT, ::binaryExpression)
        register(FieldSelectNode::class.java, PRE_VISIT, ::fieldSelect)
        register(UnaryExpressionNode::class.java, PRE_VISIT, ::unaryExpression)

        register(ArrayLiteralNode::class.java, PRE_VISIT, ::arrayLiteral)
        register(BooleanLiteralNode::class.java, PRE_VISIT, ::booleanLiteral)
        register(IntLiteralNode::class.java, PRE_VISIT, ::intLiteral)
        register(FloatLiteralNode::class.java, PRE_VISIT, ::floatLiteral)
        register(StringLiteralNode::class.java, PRE_VISIT, ::stringLiteral)
        register(UnitLiteralNode::class.java, PRE_VISIT, ::unitLiteral)

        register(ArrayTypeNode::class.java, PRE_VISIT, ::arrayType)
    }

    override fun accept(compilationUnit: CompilationUnitNode) {
        walker.accept(compilationUnit)
        reactor.run()
    }

    // Declarations

    private fun variableDeclaration(node: VariableDeclarationNode) {

        reactor.on(
            name = "load variable declaration symbol",
            attribute = Attribute(node, "symbol")
        ) { symbol: VariableSymbol ->
            if (node.type != null) {
                reactor.on(
                    name = "infer variable initializer type",
                    attribute = Attribute(symbol, "type")
                ){inferType: Type ->
                    if(reactor.get<Type?>(node.initializer, "type") == null){
                        reactor.copy(
                            name = "type variable declaration symbol",
                            to = Attribute(node.initializer, "type"),
                            from = Attribute(symbol, "type")
                        )
                    }
                }
                reactor.copy(
                    name = "type variable declaration symbol",
                    to = Attribute(symbol, "type"),
                    from = Attribute(node.type, "type")
                )
            }
            else{
                reactor.copy(
                    name = "infer variable declaration symbol",
                    to = Attribute(symbol, "type"),
                    from = Attribute(node.initializer, "type")
                )
            }
        }
    }

    private fun thisDeclaration(node: ThisNode) {
        reactor.on(
            name = "load this declaration symbol",
            attribute = Attribute(node, "containingClass")
        ) { symbol: ClassSymbol ->
            reactor.copy(
                name = "type this declaration symbol",
                from = Attribute(symbol, "type"),
                to = Attribute(node, "type")
            )
        }
    }

    private fun functionDeclaration(node: FunctionDeclarationNode) {
        if(node.body.find<ReturnNode>().isEmpty()) {
            reactor.supply(
                name = "infer return type of function declaration",
                attribute = Attribute(node.body, "returnType")
            ) {
                UnitType
            }
        }

        reactor.on(
            name = "load function declaration symbol",
            attribute = Attribute(node, "symbol")
        ) { symbol: CallableSymbol ->

            val symbolTypeAttribute = Attribute(symbol, "type")

            val parameterTypeAttributesMap = symbol.parameters
                .associateTo(LinkedHashMap()) { parameterSymbol ->
                    parameterSymbol.name to Attribute(parameterSymbol, "type")
                }

            val returnTypeAttribute = if (node.returnType != null) Attribute(node.returnType, "type") else
                Attribute(node.body, "returnType")

            // println("function: $returnTypeAttribute")

            reactor.rule("type function declaration symbol") {
                exports(symbolTypeAttribute)
                using(parameterTypeAttributesMap.values)
                using(returnTypeAttribute)
                by { r ->
                    r[symbolTypeAttribute] = FunctionType(
                        parameterTypes = parameterTypeAttributesMap
                            .mapValuesTo(LinkedHashMap()) { (_, fieldTypeAttribute) ->
                                r[fieldTypeAttribute]
                            },
                        returnType = r[returnTypeAttribute]
                    )
                }
            }

        }
    }

    private fun parameterDeclaration(node: ParameterNode) {
        reactor.on(
            name = "load parameter declaration symbol",
            attribute = Attribute(node, "symbol")
        ) { symbol: Symbol ->
            reactor.copy(
                name = "type variable declaration symbol",
                to = Attribute(symbol, "type"),
                from = Attribute(node.type, "type")
            )
        }
    }

    private fun structDeclaration(node: StructDeclarationNode) {

        reactor.on(
            name = "load struct declaration symbol",
            attribute = Attribute(node, "symbol"),
        ) { symbol: StructSymbol ->
            reactor.mapN(
                name = "type struct declaration symbol",
                from = symbol.fields.map { Attribute(it, "type") },
                to = Attribute(symbol, "type"),
            ) { fieldTypes: List<Type> ->
                StructType(
                    name = symbol.getQualifiedName("_"),
                    fieldTypes = symbol.fields
                        .map { it.name }
                        .zip(fieldTypes)
                        .toMap(LinkedHashMap())
                )
            }
        }
    }

    private fun fieldDeclaration(node: FieldNode) {
        reactor.on(
            name = "load field declaration symbol",
            attribute = Attribute(node, "symbol")
        ) { symbol: Symbol ->
            reactor.copy(
                name = "type field declaration symbol",
                to = Attribute(symbol, "type"),
                from = Attribute(node.type, "type"),
            )
        }
    }

    // expressions that contain an identifier (i.e., refer to a declaration)

    private fun reference(node: ReferenceNode) {
        reactor.on(
            name = "load reference symbol",
            attribute = Attribute(node, "symbol")
        ) { symbol: Symbol ->
            reactor.copy(
                name = "type reference",
                to = Attribute(node, "type"),
                from = Attribute(symbol, "type")
            )
        }
    }

    // expressions that do not reference a symbol

    private fun assignment(node: AssignmentNode) {
        reactor.copy(
            name = "load variable declaration symbol",
            from = Attribute(node.expression, "type"),
            to = Attribute(node, "type"),
        )
    }

    private fun index(node: IndexNode) {
        reactor.map(
            name = "type array access",
            from = Attribute(node.expression, "type"),
            to = Attribute(node, "type"),
        ) { expressionType: Type ->

            when (expressionType) {
                is ArrayType -> expressionType.elementType
                else -> SemanticError(node, "expression must be Array, not $expressionType")
            }

        }
    }

    private fun binaryExpression(node: BinaryExpressionNode) {
        when (node.operator) {
            ADDITION, SUBTRACTION, MULTIPLICATION, REMAINDER -> reactor.mapN(
                name = "binary math operand types",
                from = listOf(Attribute(node.left, "type"), Attribute(node.right, "type")),
                to = Attribute(node, "type"),
            ) { operandTypes: List<Type> ->
                when {
                    operandTypes.all{ type -> type is IntType } -> IntType
                    operandTypes.all{ type -> type is FloatType } -> FloatType
                    operandTypes.all{ type -> type is IntType || type is FloatType } -> FloatType
                    else -> SemanticError(node, "Binary expression is not supported between these types $operandTypes")
                }
            }
            DIVISION -> reactor.mapN(
                name = "binary math div '/' type",
                from = listOf(Attribute(node.left, "type"), Attribute(node.right, "type")),
                to = Attribute(node, "type"),
            ) { operandTypes: List<Type> ->
                when {
                    operandTypes.all{ type -> type is IntType || type is FloatType } -> FloatType
                    else -> SemanticError(node, "Binary expression is not supported between these types $operandTypes")
                }
            }
            EQUAL_TO, NOT_EQUAL_TO,
            GREATER_THAN, GREATER_THAN_OR_EQUAL_TO, LESS_THAN, LESS_THAN_OR_EQUAL_TO,
            LOGICAL_CONJUNCTION, LOGICAL_DISJUNCTION -> reactor.supply(
                "binary expression: type",
                Attribute(node, "type")
            ) {
                BooleanType
            }
        }
    }

    private fun call(node: CallNode) {
        reactor.map(
            name = "type call",
            from = Attribute(node.callee, "type"),
            to = Attribute(node, "type"),
        ) { calleeType: Type ->

            when (calleeType) {
                is FunctionType -> calleeType.returnType
                is StructType -> calleeType
                is ClassType -> calleeType
                else -> SemanticError(node, "expression is not callable")
            }

        }
    }

    private fun fieldSelect(node: FieldSelectNode) {
        reactor.map(
            name = "type field access",
            from = Attribute(node.expression, "type"),
            to = Attribute(node, "type"),
        ) { expressionType: Type ->

            when (expressionType) {
                is StructType -> {
                    when (val fieldType = expressionType.fieldTypes[node.name]) {
                        null -> SemanticError(node, "unknown field ${node.name} in ${expressionType.name}")
                        else -> fieldType
                    }
                }
                is ClassType -> {
                    when (val fieldType = expressionType.fieldTypes[node.name]) {
                        null -> SemanticError(node, "unknown field ${node.name} in ${expressionType.name}")
                        else -> fieldType
                    }
                }

                else -> SemanticError(node, "expression must be Struct, not $expressionType")
            }

        }
    }

    private fun unaryExpression(node: UnaryExpressionNode) {
        when (node.operator) {
            LOGICAL_COMPLEMENT -> reactor.supply(
                    name = "unary expression: type",
                    attribute = Attribute(node, "type")
                ) {
                    BooleanType
                }
            NEGATION -> reactor.map(
                name = "negation type",
                from = Attribute(node.operand, "type"),
                to = Attribute(node, "type"),
            ) { operandType: Type ->
                when (operandType) {
                    is IntType -> IntType
                    is FloatType -> FloatType
                    else -> SemanticError(node, "type $operandType is not supported")
                }
            }
        }
    }

    private fun arrayLiteral(node: ArrayLiteralNode) {

        val nodeTypeAttribute = Attribute(node, "type")
        val elementTypeAttributes = node.elements.map { Attribute(it, "type") }

        reactor.mapN(
            name = "type array literal",
            from = elementTypeAttributes,
            to = nodeTypeAttribute,
        ) { elementTypes: List<Type> ->
            when {
                elementTypes.isEmpty() -> SemanticError(node, "unable to determine type for array literal")
                else -> {
                    val supertype = elementTypes.reduce { acc, type -> acc.commonSupertype(type) }
                    ArrayType(supertype)
                }
            }
        }
    }

    private fun booleanLiteral(node: BooleanLiteralNode) {
        reactor.supply(name = "boolena: type", Attribute(node, "type")) { BooleanType }
    }

    private fun intLiteral(node: IntLiteralNode) {
        reactor.supply(name = "int: type", Attribute(node, "type")) { IntType }
    }

    private fun floatLiteral(node: FloatLiteralNode) {
        reactor.supply(name = "float: type", Attribute(node, "type")) { FloatType }
    }

    private fun stringLiteral(node: StringLiteralNode) {
        reactor.supply(name = "string: type", Attribute(node, "type")) { StringType }
    }

    private fun unitLiteral(node: UnitLiteralNode) {
        reactor.supply(name = "unit: type", Attribute(node, "type")) { UnitType }
    }

    private fun arrayType(node: ArrayTypeNode) {
        reactor.map(
            name = "type array type",
            from = Attribute(node.elementType, "type"),
            to = Attribute(node, "type"),
        ) { elementType: Type -> ArrayType(elementType) }
    }

    private fun classDeclaration(node: ClassDeclarationNode){
        reactor.on(
            name = "type class declaration symbol",
            attribute = Attribute(node, "symbol")
        ) { symbol: ClassSymbol ->

            val symbolTypeAttribute = Attribute(symbol, "type")
            val fieldTypes = symbol.fields.associateTo(LinkedHashMap()) {
                it.name to Attribute(it, "type")
            }
            val methodTypes = symbol.methods.associateTo(LinkedHashMap()) {
                it.name to Attribute(it, "type")
            }

            reactor.rule("type class declaration symbol") {
                exports(symbolTypeAttribute)
                using(fieldTypes.values)
                using(methodTypes.values)
                by { r ->
                    r[symbolTypeAttribute] = ClassType(
                        name = symbol.getQualifiedName("_"),
                        fieldTypes = fieldTypes.mapValuesTo(LinkedHashMap()) { (_, fieldTypeAttribute) ->
                            r[fieldTypeAttribute]
                        },
                        methodTypes = methodTypes.mapValuesTo(LinkedHashMap()) { (_, methodTypeAttribute) ->
                            r[methodTypeAttribute]
                        },
                        superClass = null
                    )
                }
            }
        }
    }

    private fun methodCall(node: MethodCallNode) {
        reactor.map(
            name = "type method call",
            from = Attribute(node.receiver, "type"),
            to = Attribute(node, "type")
        ) { expressionType: Type ->
            when (expressionType) {
                is ClassType -> {
                    when (val methodType = expressionType.methodTypes[node.callee]) {
                        null -> SemanticError(node, "unknown method ${node.callee} in ${expressionType.name}")
                        is FunctionType -> methodType.returnType
                        else -> SemanticError(
                                node,
                                "${node.callee} is not a callable function in ${expressionType.name}"
                            )
                    }
                }
                else -> SemanticError(node, "expression must be Class, not $expressionType")
            }
        }

        reactor.on(
            name = "resolve symbol for method call",
            attribute = Attribute(node.receiver, "type")
        ) { classType: ClassType ->
            reactor.map(
                name = "resolve symbol for method call",
                from = Attribute(node.receiver, "scope"),
                to = Attribute(node, "symbol")
            ) { referenceScope: Scope ->
                when (val classSymbol = referenceScope.lookup(classType.name)) {
                    is ClassSymbol -> {
                        when (val methodSymbol = classSymbol.resolveMethod(node.callee)) {
                            is MethodSymbol -> methodSymbol
                            else -> SemanticError(
                                node,
                                "unable to resolve method ${node.callee} in ${classType.name}"
                            )
                        }
                    }
                    else -> SemanticError(node, "unable to resolve class ${classType.name}")
                }
            }
        }
    }

     private fun block(node: BlockNode) {
         val childrenReturnTypeAttributes = node.statements
             .filter { isReturnContainer(it) }
             .map { Attribute(it, "returnType") }


         reactor.mapN(
             name = "infer block return type",
             from = childrenReturnTypeAttributes,
             to = Attribute(node, "returnType")
         ) {
             branchReturnTypes: List<Type> ->
             val knownTypes = branchReturnTypes.filterNot { it is UnitType }

             if(knownTypes.isEmpty()) UnitType
             else knownTypes.reduce { acc, type -> acc.commonSupertype(type) }
         }

     }

     private fun ifStmt(node: IfNode) {
         reactor.mapN(
             "resolve return type of if",
             from = listOfNotNull(node.thenStatement, node.elseStatement)
                 .filter { isReturnContainer(it) }
                 .map { Attribute(it, "returnType") },
             to = Attribute(node, "returnType")
         ) {
             branchReturnTypes: List<Type> ->
             val hasUnknown = branchReturnTypes.any { it is UnitType }

             if(hasUnknown) UnitType
             else branchReturnTypes.reduce { acc, type -> acc.commonSupertype(type) }
         }
     }

    private fun returnStmt(node: ReturnNode) {
        // println("return ${node}")
        if (node.expression != null) {
            reactor.map(
                name = "infer return type of return",
                from = Attribute(node.expression, "type"),
                to = Attribute(node, "returnType")
            ) {
                type: Type -> type
            }
        } else {
            reactor.supply(
                "return statement type",
                Attribute(node, "returnType")
            ) {
                UnitType
            }
        }
    }

    private fun isReturnContainer(node: Node): Boolean {
        return (node is BlockNode || node is IfNode || node is ReturnNode)
    }
}
