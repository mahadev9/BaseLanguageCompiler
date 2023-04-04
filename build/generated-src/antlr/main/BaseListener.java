// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BaseParser}.
 */
public interface BaseListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BaseParser#compilatonUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilatonUnit(BaseParser.CompilatonUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseParser#compilatonUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilatonUnit(BaseParser.CompilatonUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(BaseParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(BaseParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code structDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(BaseParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code structDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(BaseParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(BaseParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(BaseParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementAsDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterStatementAsDeclaration(BaseParser.StatementAsDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementAsDeclaration}
	 * labeled alternative in {@link BaseParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitStatementAsDeclaration(BaseParser.StatementAsDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(BaseParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(BaseParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(BaseParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(BaseParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockAsStatement}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockAsStatement(BaseParser.BlockAsStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockAsStatement}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockAsStatement(BaseParser.BlockAsStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStmt(BaseParser.ExpressionStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStmt(BaseParser.ExpressionStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(BaseParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(BaseParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(BaseParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(BaseParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(BaseParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link BaseParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(BaseParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(BaseParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(BaseParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(BaseParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(BaseParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(BaseParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(BaseParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldSelect}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFieldSelect(BaseParser.FieldSelectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldSelect}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFieldSelect(BaseParser.FieldSelectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(BaseParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(BaseParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code referenceAsExpression}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterReferenceAsExpression(BaseParser.ReferenceAsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code referenceAsExpression}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitReferenceAsExpression(BaseParser.ReferenceAsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code index}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIndex(BaseParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by the {@code index}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIndex(BaseParser.IndexContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unitLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnitLiteral(BaseParser.UnitLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unitLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnitLiteral(BaseParser.UnitLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code call}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCall(BaseParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code call}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCall(BaseParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryPrefix}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPrefix(BaseParser.UnaryPrefixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryPrefix}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPrefix(BaseParser.UnaryPrefixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(BaseParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(BaseParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesized}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenthesized(BaseParser.ParenthesizedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesized}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenthesized(BaseParser.ParenthesizedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binary}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinary(BaseParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binary}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinary(BaseParser.BinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(BaseParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link BaseParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(BaseParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code referenceAsType}
	 * labeled alternative in {@link BaseParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void enterReferenceAsType(BaseParser.ReferenceAsTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code referenceAsType}
	 * labeled alternative in {@link BaseParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void exitReferenceAsType(BaseParser.ReferenceAsTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link BaseParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(BaseParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link BaseParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(BaseParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseParser#reference}.
	 * @param ctx the parse tree
	 */
	void enterReference(BaseParser.ReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseParser#reference}.
	 * @param ctx the parse tree
	 */
	void exitReference(BaseParser.ReferenceContext ctx);
}