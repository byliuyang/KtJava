import com.sun.nio.sctp.IllegalUnbindException
import expressionLists.LastExpressionList
import expressionLists.PairExpressionList
import expressions.*
import statements.AssignStatement
import statements.CompoundStatement
import statements.PrintStatement
import statements.Statement
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
 *
 * program => a := 5+3; b := (print(a, a-1), 10*a); print(b)
 * maxArgs(program)
 *
 * 2
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

fun update(table: Table?, key: String, value: Int): Table {
    return Table(key, value, table)
}

fun lookup(table: Table?, key: String): Int {
    var head = table
    while (head != null) {
        if (head.id == key)
            return head.value
        head = head.tail
    }
    throw NoSuchElementException()
}

fun interpret(expression: Expression, table: Table?): IntAndTable {
    when (expression) {
        is IdentifierExpression -> return IntAndTable(lookup(table, expression.identifier), table)
        is NumberExpression -> return IntAndTable(expression.number, table)
        is OperatorExpression -> {
            val left = interpret(expression.expressionLeft, table)
            val right = interpret(expression.expressionRight, left.table)

            var result = 0
            when (expression.operator) {
                BinaryOperator.PLUS -> result = left.int + right.int
                BinaryOperator.TIMES -> result = left.int * right.int
                BinaryOperator.MINUS -> result = left.int - right.int
                BinaryOperator.DIVIDE -> result = left.int / right.int
            }
            return IntAndTable(result, table)
        }
        is SequenceExpression -> {
            val newTable = interpret(expression.statement, table)
            return interpret(expression.expression, newTable)
        }
    }
    throw IllegalUnbindException()
}

fun interpret(statement: Statement, table: Table?): Table? {
    when (statement) {
        is AssignStatement -> {
            val tableAndInt = interpret(statement.expression, table)
            return update(tableAndInt.table, statement.id, tableAndInt.int)
        }
        is CompoundStatement -> {
            val newTable = interpret(statement.statementLeft, table)
            return interpret(statement.statementRight, newTable)
        }
        is PrintStatement -> {
            var expressionList = statement.expressionList
            var newTable = table
            while (expressionList !is LastExpressionList) {
                if (expressionList is PairExpressionList) {
                    val tableAndInt = interpret(expressionList.head, newTable)
                    print("${tableAndInt.int} ")
                    newTable = tableAndInt.table
                    expressionList = expressionList.tail
                }
            }
            val tableAndInt = interpret(expressionList.head, newTable)
            print("${tableAndInt.int}\n")
            return tableAndInt.table
        }
    }
    throw IllegalArgumentException()
}

/**
 * Interprets a program in this language
 *
 * For example,
 *
 * program => a := 5+3; b := (print(a, a-1), 10*a); print(b)
 * interpret(program)
 *
 * 8 7
 * 80
 */
fun interpret(statement: Statement) {
    interpret(statement, null)
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

    interpret(program)
}