/**
 * 
 */
package StatementClasses;

import java.util.ArrayList;

import BaseClasses.Statement;
import BaseClasses.Value;
import BaseClasses.Variable;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class FuncCallStatement extends Statement{
	private String funcname;
	private ArrayList<Variable> variablelist=new ArrayList<Variable>();//实参列表
	public boolean executeStatement(){
		boolean result =true;
		System.out.println("execute-FuncCallStatement");
		Executor.setReturnvalue(new Value(4.3, 4));
		return result;
	}
}
