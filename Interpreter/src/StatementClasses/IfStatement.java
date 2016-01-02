/**
 * 
 */
package StatementClasses;

import BaseClasses.ConditionJudge;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class IfStatement extends Statement{
	private int type1;//0-值；1-变量名；2-函数名+++ 必填
	private int type2;//0-值；1-变量名；2-函数名
	private String operate;//!的时候操作数2为空+++必填
	//操作数可能为值、变量、函数调用
	private Value value1;//操作数1+++三选一必填
	private Value value2;//操作数2
	private String variable1;
	private String variable2;
	private FuncCallStatement funcCallStatement1;
	private FuncCallStatement funcCallStatement2;
	public boolean executeStatement(){
		boolean result =false;
		Executor.statementArraylist.add(new Segment(3));
		ConditionJudge judge=new ConditionJudge();
		Executor.currentScope++;
		System.out.println("execute-IfStatement");
		switch (type1) {
		case 0:
			break;
		case 1:
			value1=Executor.getVariable(variable1).getValue();
			break;
		case 2:
			funcCallStatement1.executeStatement();
			value1=Executor.getReturnvalue();
			break;
		default:
			break;
		}
		judge.setOperator1(value1);
		judge.setOperate(operate);
//		Executor.isinIf=true;
		if(!operate.equals("!")){
			switch (type2) {
			case 0:
				break;
			case 1:
				value2=Executor.getVariable(variable2).getValue();
				break;
			case 2:
				funcCallStatement2.executeStatement();
				value2=Executor.getReturnvalue();
				break;
			default:
				break;
			}
			judge.setOperator2(value2);
		}
		if(judge.getJudgeResult()){
			Executor.statementArraylist.get(Executor.statementArraylist.size()-1).trueOrfalse=true;
		}
		result=true;
		return result;
	}
	public int getType1() {
		return type1;
	}
	public void setType1(int type1) {
		this.type1 = type1;
	}
	public int getType2() {
		return type2;
	}
	public void setType2(int type2) {
		this.type2 = type2;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Value getValue1() {
		return value1;
	}
	public void setValue1(Value value1) {
		this.value1 = value1;
	}
	public Value getValue2() {
		return value2;
	}
	public void setValue2(Value value2) {
		this.value2 = value2;
	}
	public String getVariable1() {
		return variable1;
	}
	public void setVariable1(String variable1) {
		this.variable1 = variable1;
	}
	public String getVariable2() {
		return variable2;
	}
	public void setVariable2(String variable2) {
		this.variable2 = variable2;
	}
	public FuncCallStatement getFuncCallStatement1() {
		return funcCallStatement1;
	}
	public void setFuncCallStatement1(FuncCallStatement funcCallStatement1) {
		this.funcCallStatement1 = funcCallStatement1;
	}
	public FuncCallStatement getFuncCallStatement2() {
		return funcCallStatement2;
	}
	public void setFuncCallStatement2(FuncCallStatement funcCallStatement2) {
		this.funcCallStatement2 = funcCallStatement2;
	}

}
