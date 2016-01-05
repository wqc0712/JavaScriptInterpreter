package StatementClasses;

import BaseClasses.Statement;
import BaseClasses.Value;
import StatementExecuteClasses.Executor;

/**
 * Created by viki on 16/1/4.
 */
public class ReturnStatement extends Statement {
    private int index;

    public ReturnStatement(int index) {
        this.setType(15);
        this.index = index;
    }

    public boolean executeStatement() throws Exception {
        boolean result =true;
        System.out.println("execute-ReturnStatement");
        Value res=new Value();
        res.setNull(true);
        if(index==-1){//空

        }
        else{//非空
            ExpressionStatement es=new ExpressionStatement(index);
            res=es.executeExpression(index);
        }
        for(int i=Executor.statementArraylist.size()-1;i>=0;i--){
            if(Executor.statementArraylist.get(i).type==8){//函数调用
                Executor.setReturnvalue(res);
                Executor.statementArraylist.remove(i);
//                Executor.currentScope--;
            }
            else{//读到其他语句,通通结束
                Executor.statementArraylist.remove(i);
//                Executor.currentScope--;
            }

        }
        return result;
    }
}
