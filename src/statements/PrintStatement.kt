package statements

import expressionLists.ExpressionList

data class PrintStatement(val expressionList: ExpressionList): Statement()