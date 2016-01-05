package StatementClasses;

import BaseClasses.Segment;
import BaseClasses.Statement;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class ElseStatement extends Statement{
    public ElseStatement() {
        this.setType(4);
    }
	public boolean executeStatement(){
		boolean result=false;
		Executor.statementArraylist.add(new Segment(4));
//		Executor.currentScope++;
		System.out.println("execute-ElseStatement");
		Executor.statementArraylist.get(Executor.statementArraylist.size()-1).trueOrfalse=Executor.Condition;
		return result;
	}
}
