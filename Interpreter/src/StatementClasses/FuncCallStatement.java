/**
 * 
 */
package StatementClasses;

import java.text.DecimalFormat;
import java.util.*;

import BaseClasses.*;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class FuncCallStatement extends Statement{
	private String funcname;
	private ArrayList<Integer> varlist=new ArrayList<Integer>();//实参列表
	private ArrayList<String> Sysfunction=new ArrayList<String>();

	public FuncCallStatement(String funcname, ArrayList<Integer> varlist) {
		this.setType(8);
		this.funcname = funcname;
		this.varlist = varlist;
		Sysfunction.add("Array");
		Sysfunction.add("Object");
		Sysfunction.add("write");
	}

	public boolean executeStatement() throws Exception {
		boolean result =true;
//		System.out.println("execute-FuncCallStatement"+funcname);
		ExpressionStatement es=new ExpressionStatement(0);
		Executor executor=new Executor();
		if(Sysfunction.contains(funcname)){//系统函数
			Value res=new Value();
			if (funcname.equals("Array")){
				if (varlist.size()>1){
					System.out.println("Array只支持一个参数");
					result=false;
					throw new Exception("参数数量错误");
				}
				else {
					if (es.executeExpression(varlist.get(0)).getType()!=4){
						throw new Exception("array的参数必须是数字哦亲我会四舍五入");
					}
					else {
						res.setType(5);
						int i=Integer.parseInt(new DecimalFormat("0").format(es.executeExpression(varlist.get(0)).getDoublevalue()));
						res.setArrayvalue(new ArrayList<Value>());
						for (int j=0;j<i;j++){
							Value a=new Value();
							a.setNull(true);
							res.getArrayvalue().add(a);
						}

					}

				}
			}
			else if(funcname.equals("Object")){
				if (varlist.size()>1){
					System.out.println("Object不支持参数");
					result=false;
					throw new Exception("参数数量错误");
				}
				else {
					res.setType(6);
					res.setObjectvalue(new HashMap<String, Value>());
				}

			}
			else if (funcname.equals("write")){
				if (varlist.size()>1){
					System.out.println("write只支持一个参数");
					result=false;
					throw new Exception("参数数量错误");
				}
				else {
					es.executeExpression(varlist.get(0)).print();
				}
			}
			Executor.setReturnvalue(res);
		}
		else{
			//获取函数
			Function func=Executor.getFunction(funcname);
			Executor.isFuncCall=true;

			Executor.statementArraylist.add(new Segment(8));


			//给变量赋值
			if(varlist.size()>func.getParamList().size()){
				System.out.println("参数数量错误");
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
		}

		//
		return result;
	}
}
