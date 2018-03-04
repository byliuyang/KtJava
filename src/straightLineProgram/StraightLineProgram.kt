package straightLineProgram

import straightLineProgram.expressionLists.LastExpressionList
import straightLineProgram.expressionLists.PairExpressionList
import straightLineProgram.expressions.*
import straightLineProgram.statements.AssignStatement
import straightLineProgram.statements.CompoundStatement
import straightLineProgram.statements.PrintStatement
import straightLineProgram.statements.Statement
import java.lang.IllegalArgumentException

fun maxArgs(expression: Expression): Int {
    when (expression) {
        is OperatorExpression -> {
            return Math.max(maxArgs(expression.expressionLeft), maxArgs(expression.expressionRight))
        }
        is SequenceExpression -> {
            return Math.max(maxArgs(expression.statement), maxArgs(expression.expression))
        }
        else -> return@maxArgs 0
    }
}

/**
 * Tells the maximum num- ber of arguments of any print statement within any subexpression of a given statement.
 *
 * For example,
 * prog => a := 5+3; b := (print(a, a-1), 10*a); print(b)
 * maxargs(prog) is 2
 */
fun maxArgs(statement: Statement): Int {
    when (statement) {
        is AssignStatement -> {
            return maxArgs(statement.expression)
        }
        is CompoundStatement -> {
            return Math.max(maxArgs(statement.statementLeft), maxArgs(statement.statementRight))
        }
        is PrintStatement -> {
            var count = 0
            var maxCount = 0
            var expressionList = statement.expressionList
            while (expressionList !is LastExpressionList) {
                count++
                if (expressionList is PairExpressionList) {
                    maxCount = Math.max(maxCount, maxArgs(expressionList.head))
                    expressionList = expressionList.tail
                } else throw IllegalArgumentException("Unknown expressionList")
            }

            maxCount = Math.max(maxCount, maxArgs(expressionList.head))
            return Math.max(maxCount, count + 1)
        }
        else -> throw IllegalArgumentException("Unknown Statement")
    }
}

fun main(args: Array<String>) {
    // a := 5+3; b := (print(a, a-1), 10*a); print(b)
    val program = CompoundStatement(
            AssignStatement(
                    "a",
                    OperatorExpression(
                            NumberExpression(5),
                            BinaryOperator.PLUS,
                            NumberExpression(3)
                    )
            ),
            CompoundStatement(
                    AssignStatement(
                            "b",
                            SequenceExpression(
                                    PrintStatement(
                                            PairExpressionList(
                                                    IdentifierExpression("a"),
                                                    LastExpressionList(
                                                            OperatorExpression(
                                                                    IdentifierExpression("a"),
                                                                    BinaryOperator.MINUS,
                                                                    NumberExpression(1)
                                                            )
                                                    )
                                            )
                                    ),
                                    OperatorExpression(
                                            NumberExpression(10),
                                            BinaryOperator.TIMES,
                                            IdentifierExpression("a")

                                    )
                            )),
                    PrintStatement(
                            LastExpressionList(
                                    IdentifierExpression("b")
                            )
                    )
            ))

    print(maxArgs(program))
}