/**
 * 
 */
package StatementClasses;

import BaseClasses.Statement;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class OverSegStatement extends Statement {
	public boolean executeStatement(){
		boolean result =true;
		System.out.println("execute-OverSegStatement");
		if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==3){//结束if语句
			Executor.waitElse=true;
		}
		else if(Executor.isFuncCall){
			Executor.isFuncCall=false;
		}
//		else if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==9){//结束函数定义
//			Executor.funcDefFlag=false;
//		}
		Executor.statementArraylist.remove(Executor.statementArraylist.size()-1);
		Executor.removeInvalidVarFunc();
		Executor.currentScope--;
		return result;
	}

}
