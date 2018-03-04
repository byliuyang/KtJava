package straightLineProgram.expressionLists

import straightLineProgram.expressions.Expression

data class PairExpressionList(val head: Expression, val tail: ExpressionList): ExpressionList()