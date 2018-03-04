package straightLineProgram

import straightLineProgram.expressionLists.LastExpressionList
import straightLineProgram.expressionLists.PairExpressionList
import straightLineProgram.expressions.*
import straightLineProgram.statements.AssignStatement
import straightLineProgram.statements.CompoundStatement
import straightLineProgram.statements.PrintStatement

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
}