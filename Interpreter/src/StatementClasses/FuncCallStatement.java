/**
 * 
 */
package StatementClasses;

import java.util.ArrayList;

import BaseClasses.Function;
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
	private ArrayList<Integer> varlist=new ArrayList<Integer>();//实参列表
	private ArrayList<Variable> variablelist=new ArrayList<Variable>();//实参列表

	public FuncCallStatement(String funcname, ArrayList<Integer> varlist) {
		this.setType(8);
		this.funcname = funcname;
		this.varlist = varlist;
	}

	public boolean executeStatement() throws Exception {
		boolean result =true;
		System.out.println("execute-FuncCallStatement");
		//获取函数
		Function func=Executor.getFunction(funcname);
		Executor.isFuncCall=true;
		Executor.currentScope++;
		ExpressionStatement es=new ExpressionStatement(0);
		Executor executor=new Executor();
		//给变量赋值
		for(int i=0;i<varlist.size();i++){
			Variable var=new Variable(func.getParamList().get(i),Executor.currentScope);
			var.setValue(es.executeExpression(varlist.get(i)));
			Executor.varArraylist.add(var);
		}
		//执行语句
		for (int i=0;i<func.getFunctionBody().size();i++){
			executor.ExecuteStatement(func.getFunctionBody().get(i));
		}
		//

		Executor.setReturnvalue(new Value(4.3, 4));
		return result;
	}
}
