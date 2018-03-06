package statements

data class CompoundStatement(val statementLeft: Statement, val statementRight: Statement): Statement()