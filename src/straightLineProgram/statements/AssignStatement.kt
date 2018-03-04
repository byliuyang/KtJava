package straightLineProgram.statements

import straightLineProgram.expressions.Expression

data class AssignStatement(val id: String, val expression: Expression) : Statement()