/**
 * 
 */
package StatementExecuteClasses;

import java.util.ArrayList;

import BaseClasses.Function;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;
import BaseClasses.Variable;
import StatementClasses.ElseStatement;
import StatementClasses.FuncCallStatement;
import StatementClasses.OverSegStatement;
import StatementClasses.ValueAssignStatement;
import StatementClasses.VarDeclStatement;

/**
 * @author viki
 *
 */
public class Executor {
	
	public static int currentScope=0;//当前作用域的值
	public static ArrayList<Variable> varArraylist=new ArrayList<Variable>();//变量的list
//	private static Value returnvalue=new  Value(0,1);
	private static ArrayList<Value> returnvalue=new ArrayList<Value>();//返回值的list
	public static boolean waitElse=false;//if语句执行完，等待else语句执行
	public static ArrayList<Segment> statementArraylist=new ArrayList<Segment>();//存放当前处于的语句段的list
	public static ArrayList<Function> functionArraylist=new ArrayList<Function>();//function的list
	
	
	//运行语句的方法，给语法分析器调用
	public boolean ExecuteStatement(Statement statement) throws Exception{
		boolean result=false;
		if(statementArraylist.size()!=0){
		if(!(statementArraylist.get(statementArraylist.size()-1).type==9)){ //不是函数定义中的语句
			
			if(statementArraylist.get(statementArraylist.size()-1).type==3){//在if语句体中
				if(statementArraylist.get(statementArraylist.size()-1).trueOrfalse||statement.getClass().equals(OverSegStatement.class)){//条件为是或是结束语句体语句，否则不应执行该语句
					result=statement.executeStatement();
				}
				else {
					result=true;
				}
			}
			else if(statementArraylist.get(statementArraylist.size()-1).type==4){//在else语句体中
				if((!statementArraylist.get(statementArraylist.size()-1).trueOrfalse)||statement.getClass().equals(OverSegStatement.class)){//条件为否或是结束语句体语句，否则不执行该语句
					result=statement.executeStatement();
				}
				else {
					result=true;
				}
			}
			else{
				if(statement.getClass().equals(ElseStatement.class)){//else语句
					if (!waitElse) {
						result=false;
						Exception exception=new Exception("当前不应该存在一个else语句");
						throw exception;
					}
					else{
						result=statement.executeStatement();
					}
				}
				else {
					result=statement.executeStatement();
				}
			}
			if (waitElse) {
				waitElse=false;
			}
			
		}
		else{//是函数定义中的语句，直接加入函数体
			if(statement.getClass().equals(OverSegStatement.class)){//定义结束
				result=statement.executeStatement();
			}
			else {
				functionArraylist.get(functionArraylist.size()-1).getFunctionBody().add(statement);
				result=true;
			}
		}
		}
		else{
			result=statement.executeStatement();
			if (waitElse) {
				waitElse=false;
			}
		}
		return result;
	}
	
	public static Value getReturnvalue(){//获得函数调用的返回值
		Value rValue=returnvalue.get(returnvalue.size()-1);
		returnvalue.remove(returnvalue.size()-1);
		return rValue;
	}
	
	public static void setReturnvalue(Value revalue){//加入函数调用的返回值
		returnvalue.add(revalue);
	}
	
	public static Variable getVariable(String name){//通过名字查找变量
		Variable variable=new Variable("", -1);
		for(int i=varArraylist.size();i>0;i--){
			if(varArraylist.get(i-1).getName().equals(name)){
				variable=varArraylist.get(i-1);
				break;
			}
		}
		return variable;
	}
	
	public static void removeInvalidVarFunc(){//代码段结束，清除scope过期的变量、方法
		for(int i=varArraylist.size()-1;i>=0;i--){
			if(varArraylist.get(i).getScope()>=currentScope){
				varArraylist.remove(i);
			}
			else{
				break;
			}
		}
		for(int i=functionArraylist.size()-1;i>=0;i--){
			if(functionArraylist.get(i).getScope()>=currentScope){
				functionArraylist.remove(i);
			}
			else{
				break;//变成continue就可以一直扫描
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Executor executor=new Executor();
		try {
			VarDeclStatement varDeclStatement=new VarDeclStatement("hello");	
			executor.ExecuteStatement(varDeclStatement);
			System.out.println(varArraylist.size());
			System.out.println(varArraylist.get(0).getName());
			
			String leftvalue="hello";
			Value value=new Value("hhh",2);
			ValueAssignStatement valueAssignStatement=new ValueAssignStatement(leftvalue,value);
			executor.ExecuteStatement(valueAssignStatement);
			System.out.println(varArraylist.get(0).getValue().getStringvalue());
			
			FuncCallStatement funcCallStatement=new FuncCallStatement();
			valueAssignStatement=new ValueAssignStatement(leftvalue, funcCallStatement);
			executor.ExecuteStatement(valueAssignStatement);
			System.out.println(varArraylist.get(0).getValue().getDoublevalue());
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
}
