package StatementClasses;

import BaseClasses.Statement;
import BaseClasses.Value;
import BaseClasses.Variable;
import Interpreter.Expression;
import Interpreter.ExpressionQueue;
import StatementExecuteClasses.Executor;

import java.text.DecimalFormat;

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
        try {
            executeExpression(index);

        }
        catch (Exception e){
            result=false;
            System.out.println(e);
        }
//		Executor executor=new Executor();
        return result;
    }

    private Value executeExpression(int index) throws Exception{
        Value result=new Value();
        if (ExpressionQueue.getInstance().Geti(index).operator==6){//赋值语句
            result=executeExpression(ExpressionQueue.getInstance().Geti(index).righthand);
            assignStatement(result,index,true);
        }
        else if(ExpressionQueue.getInstance().Geti(index).Type==2){//有左值又有右值
            if(ExpressionQueue.getInstance().Geti(index).operator==1){//逻辑与
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).logicAnd(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==2){//逻辑或
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).logicOr(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==3){//比特与
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).bitAnd(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==4){//比特异或
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).bitXOr(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==5){//比特或
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).bitOr(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==9){//加
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).add(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==10){//减
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).minus(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==12){//==
                boolean flag= executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).equalsto(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                result=new Value(flag,3);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==13){//!=
                boolean flag=!executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).equalsto(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                result=new Value(flag,3);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==15){//<
                boolean flag=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).lessthan(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                result= new Value(flag,3);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==16){//>
                boolean flag=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).biggerthan(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                result= new Value(flag,3);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==17){//<=
                boolean flag1=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).lessthan(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                boolean flag2= executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).equalsto(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                result= new Value(flag1||flag2,3);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==18){//>=
                boolean flag1=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).biggerthan(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                boolean flag2= executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).equalsto(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
                result= new Value(flag1||flag2,3);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==19){//*
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).multiple(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==20){///
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).divide(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==21){//%
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).mod(executeExpression(ExpressionQueue.getInstance().Geti(index).righthand));
            }
            else{
                throw new Exception("unknown operator");
            }
        }
        else if(ExpressionQueue.getInstance().Geti(index).Type==5){//只有右值
            if(ExpressionQueue.getInstance().Geti(index).operator==7){//++a
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).righthand).addself(false);
                assignStatement(result.addself(false),index,false);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==8){//--
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).righthand).minusself(false);
                assignStatement(result.minusself(false),index,false);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==11){//!
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).righthand).not();
            }
        }
        else if(ExpressionQueue.getInstance().Geti(index).Type==6){//只有左值
            if(ExpressionQueue.getInstance().Geti(index).operator==7){//a++
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).addself(true);
                assignStatement(result.addself(false),index,true);
            }
            else if(ExpressionQueue.getInstance().Geti(index).operator==8){//--
                result=executeExpression(ExpressionQueue.getInstance().Geti(index).lefthand).minusself(true);
                assignStatement(result.minusself(false),index,true);
            }
        }
        else if (ExpressionQueue.getInstance().Geti(index).Type==11){//数组
            Variable var=Executor.getVariable(ExpressionQueue.getInstance().Geti(ExpressionQueue.getInstance().Geti(index).lefthand).identifier);

            Value res=executeExpression(ExpressionQueue.getInstance().Geti(index).righthand);
            if(res.getType()!=4) {
                throw new Exception("数组下标必须为数字");
            }
            return var.getValue().getArrayvalue().get(Integer.parseInt(new DecimalFormat("0").format(res.getDoublevalue())));
        }
        else if (ExpressionQueue.getInstance().Geti(index).Type==12){//对象
            Variable var=Executor.getVariable(ExpressionQueue.getInstance().Geti(ExpressionQueue.getInstance().Geti(index).lefthand).identifier);

            Value res=executeExpression(ExpressionQueue.getInstance().Geti(index).righthand);
            if(res.getType()!=2) {
                throw new Exception("对象key值必须为字符串");
            }
            return var.getValue().getObjectvalue().get(res.getStringvalue());
        }
        else{
            if(ExpressionQueue.getInstance().Geti(index).Type==1){//值
                if(ExpressionQueue.getInstance().Geti(index).booleanExp==0){//normal
                    if (ExpressionQueue.getInstance().Geti(index).StringData.equals("")){//非字符串
                        if(ExpressionQueue.getInstance().Geti(index).Null==0){//非空
                            result=new Value(ExpressionQueue.getInstance().Geti(index).numerical,4);
                        }
                        else{//null
                            result.setNull(true);
                        }
                    }
                    else{//字符串
                        result=new Value(ExpressionQueue.getInstance().Geti(index).StringData,2);
                    }
                }
                else if (ExpressionQueue.getInstance().Geti(index).booleanExp==1){//true
                    result=new Value(true,3);
                }
                else{//false
                    result=new Value(false,3);
                }
            }
            else if (ExpressionQueue.getInstance().Geti(index).Type==3){//变量
                result= Executor.getVariable(ExpressionQueue.getInstance().Geti(index).identifier).getValue();
            }
            else{
                throw new Exception("unknown type");
            }
        }
        return result;
    }

    private void assignStatement(Value value,int index,boolean isleft) throws Exception {
        Expression leftExpression=new Expression(0);
        if (isleft){
            leftExpression=ExpressionQueue.getInstance().Geti(ExpressionQueue.getInstance().Geti(index).lefthand);
        }
        else{
            leftExpression=ExpressionQueue.getInstance().Geti(ExpressionQueue.getInstance().Geti(index).righthand);
        }

        if(leftExpression.Type==3){//左值是变量名
            Executor.getVariable(leftExpression.identifier).setValue(value);
        }
        else if (leftExpression.Type==11){//左边是数组
            if(executeExpression(leftExpression.righthand).getType()!=4){
                throw new Exception("数组下标必须为数字");
            }
            int num=Integer.parseInt(new DecimalFormat("0").format(executeExpression(leftExpression.righthand).getDoublevalue()));

            Executor.getVariable(ExpressionQueue.getInstance().Geti(leftExpression.lefthand).identifier).getValue().getArrayvalue().set(num,value);
        }
        else if (leftExpression.Type==12){//左边是对象
            if(executeExpression(leftExpression.righthand).getType()!=2){
                throw new Exception("对象key值必须为字符串");
            }
            String key=executeExpression(leftExpression.righthand).getStringvalue();
            Executor.getVariable(ExpressionQueue.getInstance().Geti(leftExpression.lefthand).identifier).getValue().getObjectvalue().put(key,value);
        }
        else{
            throw new Exception("赋值语句左值必须为变量名,数组或对象");
        }
    }
}
