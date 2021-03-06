package Interpreter;

import java.util.ArrayList;

/**
 * Created by tom on 15/12/28.
 * Copyright (c) 2015 tom
 */



public class Expression {
    /*
        Type Def:
        1----literal, no matter a bool, string or number有
        2----operation has left hand and right hand有
        3----identifier有
        4----This
        5----Expression only have right hand有
        6----Expression only have left hand有
        7----Delete Expression
        8----Void Expression
        9----Typeof Expression
        10---Arguments Expression
        11---ArrayMemberExpression有
        12---MemberDotExpression有
        13---New Expression
     */
    public int Type;
    public int lefthand;
    public int righthand;
    public int operator;

    /*
        operator Def:
        1----   && LogicalAnd
        2----   || LogicalOr
        3----   &  BitAnd
        4----   ^  BitXOr
        5----   |  BitOr
        6----   =  Assignment
        7----   ++ Increment
        8----   -- Decrease
        9----   +  Add
        10---   -  Minus
        11---   !  Not
        12---   == Equal
        13---   != UnEqual
        14----  ~  BitNot
        15----  <  less than
        16----  >  more than
        17----  <= no more than
        18----  >= no less than
        19----  *  multiply
        20----  /  divide
        21----  %  mod
     */

    public double numerical;//数字
    public String identifier;// 变量
    public ArrayList<Integer> Parameter;//参数new
    public int Null;//0--normal,1--Null
    public String StringData; //""--not a string else a string
    public int booleanExp; //0--normal,1--true,-1--false
    public int index;//11-数组表达式的index

    public Expression(int T) {
        Type = T;
    }

}
