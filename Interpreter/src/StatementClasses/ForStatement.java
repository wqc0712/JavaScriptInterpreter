/**
 * 
 */
package StatementClasses;

import org.antlr.v4.parse.ANTLRParser.throwsSpec_return;

import BaseClasses.ConditionJudge;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class ForStatement extends Statement {
	private int index_init;//这里先设定为有三条操作表达式
	private int index_judge;
	private int index_operate;
	
	public ForStatement(int index_init,int index_judge,int index_operate){
		this.index_init = index_init;
		this.index_judge = index_judge;
		this.index_operate = index_operate;
	}
	
	public boolean executeStatement(){
		boolean result = false;
		Executor.statementArraylist.add(new Segment(5));
//		System.out.println("execute-ForStatement");
		ExpressionStatement es_init = new ExpressionStatement(index_init);
		ExpressionStatement ex_judge = new ExpressionStatement(index_judge);
		try{
			Value value_init = es_init.executeExpression(index_init);
			Value value_judge = ex_judge.executeExpression(index_judge);
			Executor.statementArraylist.get(Executor.statementArraylist.size()-1).forindex2 = index_judge;
			Executor.statementArraylist.get(Executor.statementArraylist.size()-1).forindex3 = index_operate;
			if(value_init.getType()!= 4){
				throw new Exception("for括号内赋值语句必须是double类型");
			}
			else if(value_judge.getType()!=3){
				throw new Exception("for括号内判断语句必须为boolean类型");
			}
			else{		//三条语句全部符合要求则执行语句
				Executor.statementArraylist.get(Executor.statementArraylist.size()-1).forrepeat = value_init.getBooleanvalue();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		result = true;
		return result;
	}
}
