package StatementClasses;

import BaseClasses.Segment;
import BaseClasses.Statement;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class ElseStatement extends Statement{
	public boolean executeStatement(){
		boolean result=false;
		Executor.statementArraylist.add(new Segment(4));
		Executor.currentScope++;
		System.out.println("execute-ElseStatement");
		return result;
	}
}
