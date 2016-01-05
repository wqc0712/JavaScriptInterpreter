/**
 * 
 */
package StatementClasses;

import java.util.ArrayList;

import BaseClasses.*;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class FuncCallStatement extends Statement{
	private String funcname;
	private ArrayList<Integer> varlist=new ArrayList<Integer>();//实参列表

	public FuncCallStatement(String funcname, ArrayList<Integer> varlist) {
		this.setType(8);
		this.funcname = funcname;
		this.varlist = varlist;
	}

	public boolean executeStatement() throws Exception {
		boolean result =true;
		System.out.println("execute-FuncCallStatement"+funcname);
		//获取函数
		Function func=Executor.getFunction(funcname);
		Executor.isFuncCall=true;


		ExpressionStatement es=new ExpressionStatement(0);
		Executor executor=new Executor();
		Executor.statementArraylist.add(new Segment(8));


		//给变量赋值
		if(varlist.size()>func.getParamList().size()){
			System.out.println("变量数量大于函数定义数量");
			return false;
		}

		for(int i=0;i<varlist.size();i++){
			Variable var=new Variable(func.getParamList().get(i),Executor.currentScope+1);
			var.setValue(es.executeExpression(varlist.get(i)));
			Executor.varArraylist.add(var);
		}
		Executor.currentScope++;
		//执行语句
		for (int i=0;i<func.getFunctionBody().size();i++){
			executor.ExecuteStatement(func.getFunctionBody().get(i));
		}
		Executor.currentScope--;
		Executor.isFuncCall=false;
		//
		return result;
	}
}
