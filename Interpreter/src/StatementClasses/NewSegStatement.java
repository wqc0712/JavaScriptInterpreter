/**
 * 
 */
package StatementClasses;

import BaseClasses.Segment;
import BaseClasses.Statement;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class NewSegStatement extends Statement{
	public boolean executeStatement(){
		boolean result=false;
		Executor.statementArraylist.add(new Segment(10));
//		Executor.currentScope++;
		result=true;
		return result;
	}

}
