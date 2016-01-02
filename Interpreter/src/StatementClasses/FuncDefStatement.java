/**
 * 
 */
package StatementClasses;

import java.util.ArrayList;

import BaseClasses.Function;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class FuncDefStatement extends Statement {
	private String name;
	private ArrayList<String> paramList=new ArrayList<String>();
	public FuncDefStatement(String name,ArrayList<String>paramList) {
		super();
		this.name = name;
		this.paramList=paramList;
	}

	public boolean executeStatement(){
		boolean result =true;
		System.out.println("execute-FuncdefStatement");
		Function function=new Function(name,paramList);
		function.setScope(Executor.currentScope);
		Executor.statementArraylist.add(new Segment(9));
//		Executor.funcDefFlag=true;
		Executor.functionArraylist.add(function);
//		Executor.setReturnvalue(new Value(4.3, 4));
		return result;
	}

}
