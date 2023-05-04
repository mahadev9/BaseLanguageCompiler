package edu.udel.blc.ast


sealed interface Node {
    val range: IntRange

    interface Visitor<R, A> {
        fun arrayLiteral(node: ArrayLiteralNode, arg: A): R
        fun arrayType(node: ArrayTypeNode, arg: A): R
        fun assignment(node: AssignmentNode, arg: A): R
        fun binaryExpression(node: BinaryExpressionNode, arg: A): R
        fun block(node: BlockNode, arg: A): R
        fun booleanLiteral(node: BooleanLiteralNode, arg: A): R
        fun call(node: CallNode, arg: A): R
        fun methodCall(node: MethodCallNode, arg: A): R
        fun classDeclaration(node: ClassDeclarationNode, arg: A):R
        fun compilationUnit(node: CompilationUnitNode, arg: A): R
        fun expressionStatement(node: ExpressionStatementNode, arg: A): R
        fun field(node: FieldNode, arg: A): R
        fun fieldSelect(node: FieldSelectNode, arg: A): R
        fun functionDeclaration(node: FunctionDeclarationNode, arg: A): R
        fun `if`(node: IfNode, arg: A): R
        fun index(node: IndexNode, arg: A): R
        fun intLiteral(node: IntLiteralNode, arg: A): R
        fun floatLiteral(node: FloatLiteralNode, arg: A): R
        fun parameter(node: ParameterNode, arg: A): R
        fun reference(node: ReferenceNode, arg: A): R
        fun `return`(node: ReturnNode, arg: A): R
        fun stringLiteral(node: StringLiteralNode, arg: A): R
        fun structDeclaration(node: StructDeclarationNode, arg: A): R
        fun unaryExpression(node: UnaryExpressionNode, arg: A): R
        fun unitLiteral(node: UnitLiteralNode, arg: A): R
        fun variableDeclaration(node: VariableDeclarationNode, arg: A): R
        fun `while`(node: WhileNode, arg: A): R

    }

    fun <R, A> accept(visitor: Visitor<R, A>, arg: A): R = when (this) {
        is ArrayLiteralNode -> visitor.arrayLiteral(this, arg)
        is ArrayTypeNode -> visitor.arrayType(this, arg)
        is AssignmentNode -> visitor.assignment(this, arg)
        is BinaryExpressionNode -> visitor.binaryExpression(this, arg)
        is BlockNode -> visitor.block(this, arg)
        is BooleanLiteralNode -> visitor.booleanLiteral(this, arg)
        is CallNode -> visitor.call(this, arg)
        is MethodCallNode -> visitor.methodCall(this, arg)
        is ClassDeclarationNode -> visitor.classDeclaration(this, arg)
        is CompilationUnitNode -> visitor.compilationUnit(this, arg)
        is ExpressionStatementNode -> visitor.expressionStatement(this, arg)
        is FieldNode -> visitor.field(this, arg)
        is FieldSelectNode -> visitor.fieldSelect(this, arg)
        is FunctionDeclarationNode -> visitor.functionDeclaration(this, arg)
        is IfNode -> visitor.`if`(this, arg)
        is IndexNode -> visitor.index(this, arg)
        is IntLiteralNode -> visitor.intLiteral(this, arg)
        is FloatLiteralNode -> visitor.floatLiteral(this, arg)
        is ParameterNode -> visitor.parameter(this, arg)
        is ReferenceNode -> visitor.reference(this, arg)
        is ReturnNode -> visitor.`return`(this, arg)
        is StringLiteralNode -> visitor.stringLiteral(this, arg)
        is StructDeclarationNode -> visitor.structDeclaration(this, arg)
        is UnaryExpressionNode -> visitor.unaryExpression(this, arg)
        is UnitLiteralNode -> visitor.unitLiteral(this, arg)
        is VariableDeclarationNode -> visitor.variableDeclaration(this, arg)
        is WhileNode -> visitor.`while`(this, arg)
    }
    override fun equals(other: Any?): Boolean
    override fun toString():String

}