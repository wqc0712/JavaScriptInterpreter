package StatementClasses;

import StatementExecuteClasses.Executor;
import BaseClasses.Segment;
import BaseClasses.Statement;
import BaseClasses.Value;

/**
 * @author viki
 *
 */
public class WhileStatement extends Statement {
    private int index;
    
    public WhileStatement(int index) {
        this.index = index;
    }
    
    public boolean executeStatement(){
        boolean result =false;
        Executor.statementArraylist.add(new Segment(6));
//        Executor.currentScope++;
        System.out.println("execute-WhileStatement");
        ExpressionStatement es = new ExpressionStatement(index);
        Executor.statementArraylist.get(Executor.statementArraylist.size()-1).whileindex = index;
        try {
            Value value=es.executeExpression(index);
            if(value.getType()!=3){
                throw new Exception("while括号内必须是布尔类型");
            }
            else{
                Executor.statementArraylist.get(Executor.statementArraylist.size()-1).whilerepeat=value.getBooleanvalue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result=true;
        return result;
    }
}
