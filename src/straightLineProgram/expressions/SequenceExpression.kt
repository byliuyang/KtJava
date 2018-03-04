package straightLineProgram.expressions

import straightLineProgram.statements.Statement

data class SequenceExpression(val statement: Statement, val expression: Expression): Expression()