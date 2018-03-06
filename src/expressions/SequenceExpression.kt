package expressions

import statements.Statement

data class SequenceExpression(val statement: Statement, val expression: Expression): Expression()