/**
 * 
 */
package StatementClasses;

import java.util.ArrayList;

import BaseClasses.Function;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;
import Interpreter.ExpressionQueue;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class FuncDefStatement extends Statement {
	private String name;
	private ArrayList<Integer> paramlist=new ArrayList<Integer>();
	private ArrayList<String> paramList=new ArrayList<String>();
	public FuncDefStatement(String name,ArrayList<Integer>paramlist) {
		super();
		this.setType(9);
		this.name = name;
		this.paramlist=paramlist;
	}

	public boolean executeStatement(){
		boolean result =true;
		System.out.println("execute-FuncdefStatement");
		ExpressionStatement es=new ExpressionStatement(0);
		//取变量名
		for (int i=0;i<paramlist.size();i++){
			paramList.add(ExpressionQueue.getInstance().Geti(paramlist.get(i)).identifier);
		}
		Function function=new Function(name,paramList);
		function.setScope(Executor.currentScope);
//		Executor.currentScope++;
		Executor.statementArraylist.add(new Segment(9));
//		Executor.funcDefFlag=true;
		Executor.functionArraylist.add(function);
//		Executor.setReturnvalue(new Value(4.3, 4));
		return result;
	}

}
