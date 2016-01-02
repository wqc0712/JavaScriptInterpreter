package StatementClasses;

import BaseClasses.Statement;
/**
 * @author viki
 *
 */
//1
public class ExpressionStatement extends Statement {

    private int index;

    public ExpressionStatement(int index) {
        super();
        this.index = index;
        super.setType(14);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean executeStatement(){
        boolean result =true;
        System.out.println("excecute-ExpressionStatement");

//		Executor executor=new Executor();
//        ExpressionQue

        return result;
    }
}
