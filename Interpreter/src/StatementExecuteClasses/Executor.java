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
import StatementClasses.*;

/**
 * @author viki
 *
 */
public class Executor {

	public static int currentScope=0;//当前作用域的值
	public static boolean isFuncCall=false;
	public static ArrayList<Variable> varArraylist=new ArrayList<Variable>();//变量的list
//	private static Value returnvalue=new  Value(0,1);
	private static ArrayList<Value> returnvalue=new ArrayList<Value>();//返回值的list
	public static boolean waitElse=false;//if语句执行完，等待else语句执行
	public static ArrayList<Segment> statementArraylist=new ArrayList<Segment>();//存放当前处于的语句段的list
	public static ArrayList<Function> functionArraylist=new ArrayList<Function>();//function的list
	public static boolean Condition=true;//if语句条件结果保存


	//运行语句的方法，给语法分析器调用
	public boolean ExecuteStatement(Statement statement) throws Exception{
		boolean result=false;
		if(statementArraylist.size()!=0){
			if(!(statementArraylist.get(statementArraylist.size()-1).type==9)){
				if(statementArraylist.get(statementArraylist.size()-1).type==6){	//在while语句中
                        if(!statement.getClass().equals(OverSegStatement.class)){	//不是最后一句
                            statementArraylist.get(statementArraylist.size()-1).whilestatement.add(statement);
                        } else {
							result = statement.executeStatement();
							boolean run;
							ExpressionStatement T = new ExpressionStatement(statementArraylist.get(statementArraylist.size() - 1).whileindex);
							try {
								Value valueT = T.executeExpression(statementArraylist.get(statementArraylist.size() - 1).whileindex);
								run = valueT.getBooleanvalue();
								while (run) {
									ArrayList<Statement> S = statementArraylist.get(statementArraylist.size() - 1).whilestatement;
									for (int i = 0; i < S.size(); i++) {
										Statement ST = S.get(i);
										ST.executeStatement();
									}
									valueT = T.executeExpression(statementArraylist.get(statementArraylist.size() - 1).whileindex);
									run = valueT.getBooleanvalue();
								}
							} catch (Exception Err) {
								Err.printStackTrace();
							}
							statement.executeStatement();
						}
						result = true;
                    }
			else if(statementArraylist.get(statementArraylist.size()-1).type==5){	//在for语句中
                if(!statement.getClass().equals(OverSegStatement.class)){	//不是最后一句
                    statementArraylist.get(statementArraylist.size()-1).forstatement.add(statement);
                } else {

					boolean run;
					ExpressionStatement T1 = new ExpressionStatement(statementArraylist.get(statementArraylist.size() - 1).forindex2);
					ExpressionStatement T2 = new ExpressionStatement(statementArraylist.get(statementArraylist.size() - 1).forindex3);
					try {
						Value valueT1 = T1.executeExpression(statementArraylist.get(statementArraylist.size() - 1).forindex2);
						run = valueT1.getBooleanvalue();
						while (run) {
							ArrayList<Statement> S = statementArraylist.get(statementArraylist.size() - 1).forstatement;
							for (int i = 0; i < S.size(); i++) {
								Statement ST = S.get(i);
								ST.executeStatement();
							}
							Value valueT2 = T2.executeExpression(statementArraylist.get(statementArraylist.size() - 1).forindex3);
							valueT1 = T1.executeExpression(statementArraylist.get(statementArraylist.size() - 1).forindex2);
							run = valueT1.getBooleanvalue();
						}
					} catch (Exception Err) {
						Err.printStackTrace();
					}
					statement.executeStatement();
				}
				result = true;
            }
			else if(statementArraylist.get(statementArraylist.size()-1).type==3){//在if语句体中
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
				else{//既不在if又不在else中
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
					else {//非else语句
						result=statement.executeStatement();
					}
					if (waitElse) {
						waitElse=false;
					}
				}
				/*
					这里好像有点问题
				if (waitElse) {
					waitElse=false;
				}
				*/
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
		Variable variable=null;
		for(int i=varArraylist.size()-1;i>=0;i--){
			//if(varArraylist.get(i-1).getScope()<currentScope){//isFuncCall
			//	break;
			//}
			if(varArraylist.get(i).getName().equals(name)){
				variable=varArraylist.get(i);
				break;
			}
		}
		return variable;
	}
	public static Function getFunction(String name){//通过名字查找函数
		Function func=null;
		for(int i=functionArraylist.size()-1;i>=0;i--){
//			if(isFuncCall&&functionArraylist.get(i-1).getScope()!=currentScope){//isFuncCall
//				break;
//			}
			if(functionArraylist.get(i).getName().equals(name)){
				func=functionArraylist.get(i);
				break;
			}
		}
		return func;
	}
	
	public static void removeInvalidVarFunc(){//代码段结束，清除scope过期的变量、方法
		//TODO 此处有逻辑上的问题
		System.out.println("Clean! Current Scope="+currentScope);
		for(int i=varArraylist.size()-1;i>=0;i--){
			if(varArraylist.get(i).getScope()>=currentScope && varArraylist.get(i).getScope() != 0){
				varArraylist.remove(i);
			}
			else{
				break;
			}
		}
		for(int i=functionArraylist.size()-1;i>=0;i--){
			if(functionArraylist.get(i).getScope()>=currentScope && functionArraylist.get(i).getScope() != 0){
				functionArraylist.remove(i);
			}
			else{
				break;//变成continue就可以一直扫描
			}
		}
	}


}
