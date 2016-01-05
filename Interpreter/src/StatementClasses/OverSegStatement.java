/**
 * 
 */
package StatementClasses;

import BaseClasses.Function;
import BaseClasses.Statement;
import StatementExecuteClasses.Executor;

import java.util.ArrayList;

/**
 * @author viki
 *
 */
public class OverSegStatement extends Statement {
	public OverSegStatement() {
	}

	public boolean executeStatement(){
		boolean result =true;
		System.out.println("execute-OverSegStatement");
		if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==3){//结束if语句
			Executor.waitElse=true;
			Executor.Condition = Executor.statementArraylist.get(Executor.statementArraylist.size()-1).trueOrfalse;
		}

//		else if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==9){//结束函数定义
//			Executor.funcDefFlag=false;
//		}
		if ((Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==8)){//不是函数定义中的结束
			Executor.removeInvalidVarFunc();
		}
		else {
			if(!isFuncdefOver()){//不是函数定义结束
				Executor.functionArraylist.get(Executor.functionArraylist.size()-1).getFunctionBody().add(new OverSegStatement());
				return result;
			}
		}
		Executor.statementArraylist.remove(Executor.statementArraylist.size()-1);

		return result;
	}
	public boolean isFuncdefOver(){//是不是函数定义结束
		boolean res=false;
		ArrayList<Statement> flist=Executor.functionArraylist.get(Executor.functionArraylist.size()-1).getFunctionBody();
		int statecount=0;
		int overcount=0;
		for (int i=0;i<flist.size();i++){
			if(flist.get(i).getClass().equals(IfStatement.class)||flist.get(i).getClass().equals(ElseStatement.class)
					||(flist.get(i).getClass().equals(ForStatement.class))||(flist.get(i).getClass().equals(WhileStatement.class))
					||(flist.get(i).getClass().equals(SwitchStatement.class))||(flist.get(i).getClass().equals(FuncCallStatement.class))
					||(flist.get(i).getClass().equals(NewSegStatement.class))||(flist.get(i).getClass().equals(FuncDefStatement.class))){
				statecount++;
			}
			else if (flist.get(i).getClass().equals(OverSegStatement.class)){
				overcount++;
			}
		}
		if (statecount==overcount)
			res=true;
		return res;
	}


}
