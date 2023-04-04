// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BaseParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BaseVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BaseParser#compilatonUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilatonUnit(BaseParser.CompilatonUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(BaseParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code structDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(BaseParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variableDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(BaseParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementAsDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementAsDeclaration(BaseParser.StatementAsDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BaseParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(BaseParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link BaseParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(BaseParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockAsStatement}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockAsStatement(BaseParser.BlockAsStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStmt(BaseParser.ExpressionStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(BaseParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(BaseParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(BaseParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link BaseParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(BaseParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiteral(BaseParser.ArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(BaseParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fieldSelect}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldSelect(BaseParser.FieldSelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(BaseParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenceAsExpression}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceAsExpression(BaseParser.ReferenceAsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code index}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(BaseParser.IndexContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unitLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitLiteral(BaseParser.UnitLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(BaseParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryPrefix}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPrefix(BaseParser.UnaryPrefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(BaseParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesized}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesized(BaseParser.ParenthesizedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binary}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary(BaseParser.BinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(BaseParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenceAsType}
	 * labeled alternative in {@link BaseParser#typeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceAsType(BaseParser.ReferenceAsTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link BaseParser#typeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(BaseParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BaseParser#reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReference(BaseParser.ReferenceContext ctx);
}