package straightLineProgram.expressions

data class OperatorExpression(val expressionLeft: Expression, val operator: BinaryOperator, val expressionRight: Expression): Expression()