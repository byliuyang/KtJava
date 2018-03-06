package statements

import expressions.Expression

data class AssignStatement(val id: String, val expression: Expression) : Statement()