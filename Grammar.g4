grammar Grammar;

Exprs   :   (VirDefExpr //用于变量定义语句
        |   VirAssExpr  //用于变量赋值语句
        |   IfExpr      //用于If语句
        |   WhileExpr   //用于While语句
        |   ForExpr     //用于For语句
        |   FuncExpr    //用于函数语句
        |   SegBegin    //用于函数段开头
        |   SegEnd      //用于函数段结束
        |   CaseExpr    //用于Case语句
        |   SwitchExpr  //用于Switch语句
        ) (Exprs)?
        ;

VirDefExpr  :   'var';

STRING  :   '"'('\\"'|~'"')*'"' ;
NUMBER  :   [0-9]+;
WS  :   [ \t\r\n]+  -> ship;

fragment
DIGIT   :   [0-9];
fragment
ALPHA   :   [a-zA-Z];
fragment
SYMBOL  :