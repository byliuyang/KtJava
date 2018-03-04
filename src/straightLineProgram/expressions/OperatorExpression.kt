package straightLineProgram.expressions

data class OperatorExpression(val expressionLeft: Expression, val expressionRight: Expression, val operator: BinaryOperator): Expression()