package Interpreter;

import java.util.ArrayList;

/**
 * Created by tom on 15/12/28.
 * Copyright (c) 2015 tom
 */
public class ExpressionQueue {
    private ArrayList<Expression> Data;
    private int count;
    private static ExpressionQueue Instance = new ExpressionQueue();
    static public ExpressionQueue getInstance() {
        return Instance;
    }
    private ExpressionQueue() {
        Data = new ArrayList<Expression>();
        count = 0;
    }

    public int initial() {
        Data.clear();
        count = 0;
        return 0;
    }

    public Expression Geti(int i) {
        return Data.get(i);
    }

    public int createExpression(int type) {
        Expression E = new Expression(type);
        Data.add(E);
        count++;
        return count-1;
    }

    public int nextIndex() {
        return count;
    }

    public void print() {
        Expression E;
        int i = 0;
        for (i = 0;i < count;i++) {
            E = Data.get(i);
            System.out.println(E.lefthand+" "+E.righthand+" "+E.Type+" "+E.operator);
        }
    }

}
