package expressionLists

import expressions.Expression

data class PairExpressionList(val head: Expression, val tail: ExpressionList): ExpressionList()