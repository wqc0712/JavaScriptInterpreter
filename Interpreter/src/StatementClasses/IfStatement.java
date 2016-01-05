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
	private int index;

	public IfStatement(int index) {
		this.index = index;
		this.setType(3);
	}

	public boolean executeStatement(){
		boolean result =false;
		Executor.statementArraylist.add(new Segment(3));
//		Executor.currentScope++;
//		System.out.println("execute-IfStatement");
		ExpressionStatement es=new ExpressionStatement(index);
		try {
			Value value=es.executeExpression(index);
			if(value.getType()!=3){
				throw new Exception("if括号内必须是布尔类型哈哈哈");
			}
			else{
				Executor.statementArraylist.get(Executor.statementArraylist.size()-1).trueOrfalse=value.getBooleanvalue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result=true;
		return result;
	}

}
