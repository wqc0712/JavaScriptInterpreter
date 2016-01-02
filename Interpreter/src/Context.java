import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by tom on 15/12/26.
 * Copyright (c) 2015 tom
 */
public class Context {
    private static Context Instance = new Context();
    public static Context getInstance() {return Instance;}
    private Context() {
        statue = new Stack();
        varinit =false;
        argms = null;
        Expression1 = 0;
        Expression2 = 0;
        Expression3 = 0;
    }

    private Stack<Integer> statue;

    private String identifier;
    private boolean varinit;
    private int Expression1;
    private int Expression2;
    private int Expression3;
    private ArrayList<Integer> argms;
    //private SingleExpression S1;
    /*
        0------------idle
        1------------Block Start
        2------------Var Def Statement
        3------------Return Statement
        4------------If Statement, haven't meet Expression
        5------------If Statement, haven't meet Else
        6------------If Statement, have met Else
        7------------Expression statement
        8------------Argument Statement
        9------------For Statement, haven't meet First Expression
        10-----------For Statement, haven't meet Second Expression
        11-----------For Statement, haven't meet Third Expression
        12-----------For Statement, Reading for statement
        13-----------While Statement, waiting for condition Expression
        14-----------While Statement, Reading for statement
        15-----------Function Declaration, havn't meet Function Body
        16-----------Function Declaration, meeting function body
     */

    public int initial() {
        statue = new Stack<Integer>();
        statue.push(0);
        identifier = "";
        varinit = false;
        //S1 = NULL;
        return 0;
    }

    public int blockstart() {
        if (statue.peek() == 2) {
            return -1;
        } else {
            statue.push(1);
            return 0;
        }
    }

    public int blockend(){
        if (statue.peek() == 2) {
            return -1;
        } else {
            statue.pop();
            return 0;
        }
    }

    public int VardefStart() {
        statue.push(2);
        return 0;
    }

    public int setIdentifier(String s) {
        identifier = s;
        return 0;
    }

    public int initforVar() {
        varinit = true;
        return 0;
    }

    public int NoinitforVar() {
        varinit =false;
        return 0;
    }

    public int EndVarDef() {
        statue.pop();
        System.out.println("Var Define:"+identifier);
        /*
            Create a VarDefObject
            VarName = identifier;
            VarDefObject.executeStatement;
         */
        if (varinit == true) {
            /*
                Create a ValueAssignStatement
                VarName = identifier;
                Value = Expression1;
             */
            varinit = false;
        }
        return 0;
    }

    public int SignalBreak() {
        /*
            Create a BreakObject
         */
        System.out.println("BreakAssignment");
        return 0;
    }

    public int SignalContinue() {
        /*
            Create a ContinueObject
         */
        System.out.println("Continue!");
        return 0;
    }

    public int SignalReturn() {
        statue.push(3);
        System.out.println("Return!");
        return 0;
    }

    public int EndofReturn() {
        /*
            Create a return Object
            set value = S1;
         */
        statue.pop();
        return 0;
    }

    public int Ifstart() {
        statue.push(4);
        return 0;
    }

    public int IfcondSet() {
        if (statue.peek() != 4) {
            System.err.println("Wrong If statement!");
            return -1;
        } else {
            statue.pop();
            statue.push(5);
            /*
                Create a if statement
                Condition = Expression1
             */
            return 0;
        }
    }

    public int ElseSet() {
        if (statue.peek() != 5) {
            System.err.println("Wrong If statement!");
            return -1;
        } else {
            statue.pop();
            statue.push(6);
            /*
                Create a else statement;
             */
            return 0;
        }
    }

    public int EndofIf() {
        if (statue.peek() != 6 && statue.peek() != 5) {
            System.err.println("Wrong If statement!");
            return -1;
        } else {
            statue.pop();
            return 0;
        }
    }

    public int pushExpression(int index) {
        switch (statue.peek()) {
            case 7: statue.add(7); return 0;
            case 3: case 4: {
                Expression1 = index;
                break;
            }
            case 2: {
                if (varinit) {
                    Expression1 = index;
                    break;
                }
            }
            case 8:{
                argms.add(index);
                break;
            }
            case 9:{
                statue.pop();
                statue.push(10);
                Expression1 = index;
                break;
            }
            case 10:{
                statue.pop();
                statue.push(11);
                Expression2 = index;
                break;
            }
            case 11:{
                statue.pop();
                statue.push(12);
                Expression3 = index;
                break;
            }
            case 13:{
                statue.pop();
                statue.push(14);
                Expression1 = index;
            }
            default:

        }
        statue.add(7);
        return 0;
    }
    public int EndExpression(int index) {
        statue.pop();
        int s = statue.peek();
        if (s == 1 || s == 6 || s == 5 || s == 12 || s == 14
            /*&& something else */) {
            /*
                create a new expression
                place = index
             */
        }
        return 0;
    }

    public int VisitArgument(int index) {
        statue.push(8);
        argms = new ArrayList<Integer>();
        return 0;
    }

    public ArrayList<Integer> AskForArgument() {
        return argms;
    }

    public int ForStatementBegin() {
        statue.push(9);
        return 0;
    }

    public int ForStatementEnd() {
        if (statue.peek() != 12) {
            System.err.println("Wrong For Statement!");
            return -1;
        } else {
            /*
                create a for expression
                Expression1
                Expression2
                Expression3
             */
        }
        return 0;
    }

    public int ForStatementBodyEnd() {
        if (statue.peek() != 12) {
            System.err.println("Wrong For Statement!");
            return -1;
        } else {
            /*
                Signal That For statement End
             */
        }
        statue.pop();
        return 0;
    }

    public int WhileStatementBegin() {
        statue.push(13);
        return 0;
    }

    public int WhileStatementEnd() {
        if (statue.peek() != 14) {
            System.err.println("Wrong While Statement!");
            return -1;
        } else {
            /*
                create a While expression
                Expression1
             */
        }
        return 0;
    }

    public int WhileStatementBodyEnd() {
        if (statue.peek() != 14) {
            System.err.println("Wrong While Statement!");
            return -1;
        } else {
            /*
                Signal That For statement End
             */
        }
        statue.pop();
        return 0;
    }

    public int FunctionDeclaration() {
        statue.push(15);
        return 0;
    }

    public ArrayList<Integer> SetFunctionPara() {
        argms = new ArrayList<Integer>();
        return argms;
    }

    public int FunctionBodyBegin() {
        if (statue.peek() == 15) {
            statue.pop();
            statue.push(16);
            return 0;
        } else {
            System.err.println("Wrong Function Expression");
            return -1;
        }
    }

    public int FunctionBodyEnd() {
        statue.pop();
        return 0;
    }
}
