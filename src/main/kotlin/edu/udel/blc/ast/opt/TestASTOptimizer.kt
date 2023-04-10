package edu.udel.blc.ast.opt

import edu.udel.blc.ast.*
/* IntLiteral values */
val Int1 = IntLiteralNode(-1..-1, -23)  // -23
val Int2 = IntLiteralNode(-1..-1, -5)   // -5
val Int3 = IntLiteralNode(-1..-1, -2)   // -2
val Int4 = IntLiteralNode(-1..-1, 0)    // 0
val Int5 = IntLiteralNode(-1..-1, 2)    // 2
val Int6 = IntLiteralNode(-1..-1, 5)    // 5
val Int7 = IntLiteralNode(-1..-1, 23)   // 23
val Int8 = IntLiteralNode(-1..-1, 10)   // 10
val Int9 = IntLiteralNode(-1..-1, 15)   // 15
val Int10 = IntLiteralNode(-1..-1, -18) // -18
val Int11 = IntLiteralNode(-1..-1, 1)   // 1

val String1 = StringLiteralNode(IntRange.EMPTY, "Hello")
val String2 = StringLiteralNode(IntRange.EMPTY, "World")
val String3 = StringLiteralNode(IntRange.EMPTY, "HelloWorld")

val boolTrue = BooleanLiteralNode(IntRange.EMPTY, true)
val boolFalse = BooleanLiteralNode(IntRange.EMPTY, false)

/* Binary Expressions  */
/* 5 + 5 */
val expr1 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.ADDITION, Int6, Int6)
/* 5 + 0 */
val expr2 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.ADDITION, Int6, Int4)
/* (5 + 5) + (5 + 0) */
val expr3 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.ADDITION, expr1, expr2)
/* -23 + (5 + 0) */
val expr4 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.ADDITION, Int1, expr2)
/* 10 - 15 */
val expr5 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.SUBTRACTION, Int8, Int9)
/* 0 - -2 */
val expr6 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.SUBTRACTION, Int4, Int3)
/* 5 * 0 */
val expr7 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.MULTIPLICATION, Int6, Int4)
/* 5 * 2 */
val expr8 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.MULTIPLICATION, Int6, Int5)
/* 10 % 2 */
val expr9 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.REMAINDER, Int8, Int5)
/* 23 % 2 */
val expr10 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.REMAINDER, Int7, Int5)
/* 23 % 2 == 1 */
val expr11 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.EQUAL_TO, expr10, Int11)
/* (5 * 0)  == (5 * 2) */
val expr12 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.EQUAL_TO, expr7, expr8)
/* 23 % 2 != 1 */
val expr13 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.NOT_EQUAL_TO, expr10, Int11)
/* (5 * 0)  != (5 * 2) */
val expr14 = BinaryExpressionNode(
    IntRange.EMPTY, BinaryOperator.NOT_EQUAL_TO, expr7, expr8)
/* Hello == Hello */
val expr15 = BinaryExpressionNode(
    IntRange.EMPTY,
    BinaryOperator.EQUAL_TO,
    String1,
    String1
)
/* Hello == Hello */
val expr16 = BinaryExpressionNode(
    IntRange.EMPTY,
    BinaryOperator.EQUAL_TO,
    String1,
    String2
)
/* World != HelloWorld */
val expr17 = BinaryExpressionNode(
    IntRange.EMPTY,
    BinaryOperator.NOT_EQUAL_TO,
    String2,
    String3
)

/* World != HelloWorld */
val expr18 = BinaryExpressionNode(
    IntRange.EMPTY,
    BinaryOperator.NOT_EQUAL_TO,
    String3,
    String3
)

fun main() {
    //IntLiteralTest()
    additionTest()
    println("Addition test cases passed")
    subtractionTest()
    println("Subtraction test cases passed")
    multiplicationTest()
    println("Multiplication test cases passed")
    remainderTest()
    println("Remainder test cases passed")
    equalToTest()
    println("EqualTo test cases passed")
    notEqualToTest()
    println("notEqualTo test cases passed")
    complexArithmeticOperationTest()
    println("Combination of Arithmetic Operations test cases passes")



    //equalToTest()
}

private fun additionTest(): Unit{
    var actual = (ExpressionOptimizer().apply(expr1))
    var expected = Int8
    check( actual == expected){ "Addition: expr1 failed." +
            "Expected: $expected, actual: $actual"}

    actual = (ExpressionOptimizer().apply(expr2))
    expected = Int6
    check( actual == expected){ "Addition: expr2 failed." +
            "Expected: $expected, actual: $actual"}

    actual = (ExpressionOptimizer().apply(expr3) )
    expected = Int9
    check( actual == expected){ "Addition: expr3 failed." +
            "Expected: $expected, actual: $actual"}
    actual = (ExpressionOptimizer().apply(expr4) )
    expected = Int10
    check( actual == expected){ "Addition: expr4 failed." +
            "Expected: $expected, actual: $actual"}
}

private fun subtractionTest(): Unit{
    var actual = ExpressionOptimizer().apply(expr5)
    var expected = Int2
    check( actual == expected){ "Subtraction: expr5 failed." +
            "Expected: $expected, actual: $actual" }
    actual = ExpressionOptimizer().apply(expr6)
    expected = Int5
    check( actual == expected){ "Subtraction: expr6 failed." +
            "Expected: $expected, actual: $actual" }
}

private fun multiplicationTest(): Unit{
    var actual = ExpressionOptimizer().apply(expr7)
    var expected = Int4
    check( actual == expected){ "multiplication: expr7 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr8)
    expected = Int8
    check( actual == expected){ "multiplication: expr8 failed." +
            "Expected: $expected, actual: $actual" }
}

private fun remainderTest(): Unit {
    var actual = ExpressionOptimizer().apply(expr9)
    var expected = Int4
    check(actual == expected) {"remainder: expr9 failed." +
                "Expected: $expected, actual: $actual"
    }
    actual = ExpressionOptimizer().apply(expr10)
    expected = Int11
    check(actual == expected) {"remainder: expr10 failed." +
            "Expected: $expected, actual: $actual"
    }
}

private fun equalToTest():Unit{
    var actual = ExpressionOptimizer().apply(expr11)
    var expected = boolTrue
    check( actual == expected){ "equalTo: expr11 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr12)
    expected = boolFalse
    check( actual == expected){ "equalTo: expr12 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr15)
    expected = boolTrue
    check( actual == expected){ "equalTo: expr15 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr16)
    expected = boolFalse
    check( actual == expected){ "equalTo: expr16 failed." +
            "Expected: $expected, actual: $actual" }
}

private fun notEqualToTest():Unit{
    var actual = ExpressionOptimizer().apply(expr13)
    var expected = boolFalse
    check( actual == expected){ "notEqualTo: expr13 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr14)
    expected = boolTrue
    check( actual == expected){ "notEqualTo: expr14 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr17)
    expected = boolTrue
    check( actual == expected){ "equalTo: expr17 failed." +
            "Expected: $expected, actual: $actual" }

    actual = ExpressionOptimizer().apply(expr18)
    expected = boolFalse
    check( actual == expected){ "equalTo: expr18 failed." +
            "Expected: $expected, actual: $actual" }
}

private fun complexArithmeticOperationTest(): Unit {
    val expr19 = BinaryExpressionNode(
        IntRange.EMPTY,
        BinaryOperator.ADDITION,
        BinaryExpressionNode(
            IntRange.EMPTY,
            BinaryOperator.ADDITION,
            IntLiteralNode(
                -1..-1,
                678
            ),
            IntLiteralNode(
                -1..-1,
                8311
            )
        ),
        BinaryExpressionNode(
            IntRange.EMPTY,
            BinaryOperator.ADDITION,
            IntLiteralNode(
                -1..-1,
                9023
            ),
            IntLiteralNode(
                -1..-1,
                57
            )
        )
    )

    var expected = ExpressionOptimizer().apply(expr19)
    var actual = IntLiteralNode(
        -1..-1,
        18069
    )
    check(actual == expected){ "equalTo: expr19 failed." +
            "Expected: $expected, actual: $actual" }

    val expr20 = BinaryExpressionNode(
        IntRange.EMPTY,
        BinaryOperator.MULTIPLICATION,
        BinaryExpressionNode(
            IntRange.EMPTY,
            BinaryOperator.SUBTRACTION,
            IntLiteralNode(
                -1..-1,
                0
            ),
            IntLiteralNode(
                -1..-1,
                892
            )
        ),
        BinaryExpressionNode(
            IntRange.EMPTY,
            BinaryOperator.REMAINDER,
            IntLiteralNode(
                -1..-1,
                73
            ),
            BinaryExpressionNode(
                IntRange.EMPTY,
                BinaryOperator.ADDITION,
                IntLiteralNode(
                    -1..-1,
                    65
                ),
                IntLiteralNode(
                    -1..-1,
                    -34
                )
            )
        )
    )

    expected = ExpressionOptimizer().apply(expr20)
    actual = IntLiteralNode(
        -1..-1,
        -9812
    )
    check(actual == expected){ "equalTo: expr20 failed." +
            "Expected: $expected, actual: $actual" }

    val expr21 = BinaryExpressionNode(
        IntRange.EMPTY,
        BinaryOperator.EQUAL_TO,
        BinaryExpressionNode(
            IntRange.EMPTY,
            BinaryOperator.ADDITION,
            IntLiteralNode(
                -1..-1,
                98
            ),
            IntLiteralNode(
                -1..-1,
                12
            )
        ),
        BinaryExpressionNode(
            IntRange.EMPTY,
            BinaryOperator.SUBTRACTION,
            IntLiteralNode(
                -1..-1,
                84
            ),
            IntLiteralNode(
                -1..-1,
                46
            )
        )
    )

    expected = ExpressionOptimizer().apply(expr21)
    check(boolFalse == expected){ "equalTo: expr21 failed." +
            "Expected: $expected, actual: $boolFalse" }
}

