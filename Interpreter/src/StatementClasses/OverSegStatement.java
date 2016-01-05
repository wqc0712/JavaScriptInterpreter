/**
 * 
 */
package StatementClasses;

import BaseClasses.Function;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;
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
        
        if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==6){//结束while语句
            int expressionindex;
            Value judgevalue = new Value(false,3);
            expressionindex = Executor.statementArraylist.get(Executor.statementArraylist.size()-1).whileindex;
            ExpressionStatement es = new ExpressionStatement(0);
            try {
                judgevalue = es.executeExpression(expressionindex);
                if(judgevalue.getBooleanvalue() == true){//继续执行while_segment
                    Segment repeatsegment = new Segment(6);
                    repeatsegment = Executor.statementArraylist.get(Executor.statementArraylist.size()-1);
                    Executor.statementArraylist.add(repeatsegment);
                }
                else{	//停止执行
                    Executor.statementArraylist.get(Executor.statementArraylist.size()-1).whilerepeat = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==5){//结束for语句
            int expressionindex;
            Value judgevalue = new Value(false,3);
            expressionindex = Executor.statementArraylist.get(Executor.statementArraylist.size()-1).forindex2;
            ExpressionStatement es = new ExpressionStatement(0);
            try {
                judgevalue = es.executeExpression(expressionindex);
                if(judgevalue.getBooleanvalue() == true){//继续执行for_segment
                    Segment repeatsegment = new Segment(5);
                    repeatsegment = Executor.statementArraylist.get(Executor.statementArraylist.size()-1);
                    Executor.statementArraylist.add(repeatsegment);
                }
                else{	//停止执行
                    Executor.statementArraylist.get(Executor.statementArraylist.size()-1).forrepeat = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//		else if(Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==9){//结束函数定义
//			Executor.funcDefFlag=false;
//		}

		//TODO 这里的逻辑关系要搞清楚
		if ((Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type==8)){//函数调用中的结束
			Executor.removeInvalidVarFunc();
		}
		else {
			if (Executor.statementArraylist.get(Executor.statementArraylist.size()-1).type!=9) {

			} else
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
		ArrayList<Statement> flist = null;
		if (Executor.functionArraylist.size() > 0) {
			flist = Executor.functionArraylist.get(Executor.functionArraylist.size() - 1).getFunctionBody();
		} else {
			return false;
		}
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
