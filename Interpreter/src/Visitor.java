

import Interpreter.*;

import java.util.ArrayList;

/**
 * Created by tom on 15/12/23.
 * Copyright (c) 2015 tom
 */


public class Visitor extends ECMAScriptBaseVisitor<Integer> {

    @Override
    public Integer visitProgram(ECMAScriptParser.ProgramContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Integer visitSourceElements(ECMAScriptParser.SourceElementsContext ctx) {
        int i = 0;
        while (true) {
            if (ctx.sourceElement(i) != null) {
                visit(ctx.sourceElement(i));
                i++;
            } else {
                return null;
            }
        }
    }

    @Override
    public Integer visitSourceElement(ECMAScriptParser.SourceElementContext ctx) {
        if (ctx.statement() != null) {
            visit(ctx.statement());
        } else if (ctx.functionDeclaration() != null) {
            visit(ctx.functionDeclaration());
        }
        return null;
    }

    @Override
    public Integer visitStatement(ECMAScriptParser.StatementContext ctx) {

        visit(ctx.getChild(0));

        return null;
    }

    @Override
    public Integer visitBlock(ECMAScriptParser.BlockContext ctx) {
        //TODO Signal Block Begin
        Context.getInstance().blockstart();
        /*NewSegStatement a = new NewSegStatement();
            a.executeStatement();
         */
        if (ctx.statementList() != null) {
            visit(ctx.statementList());
        }
        //TODO Signal Block End
        Context.getInstance().blockend();
        return null;
    }

    @Override
    public Integer visitStatementList(ECMAScriptParser.StatementListContext ctx) {
        int i = 0;
        while (ctx.statement(i) != null) {
            visit(ctx.statement(i));
            i++;
        }
        return null;
    }

    @Override
    public Integer visitVariableStatement(ECMAScriptParser.VariableStatementContext ctx) {

        visit(ctx.variableDeclarationList());
        return null;
    }

    @Override
    public Integer visitVariableDeclarationList(ECMAScriptParser.VariableDeclarationListContext ctx) {
        int i = 0;
        while (ctx.variableDeclaration(i) != null) {
            visit(ctx.variableDeclaration(i));
            i++;
        }
        return null;
    }

    @Override
    public Integer visitVariableDeclaration(ECMAScriptParser.VariableDeclarationContext ctx) {
        Context.getInstance().VardefStart();
        //visit(ctx.Identifier());
        Context.getInstance().setIdentifier(ctx.Identifier().getText());
        if (ctx.initialiser() != null) {
            Context.getInstance().initforVar();
            visit(ctx.initialiser());
        } else {
            Context.getInstance().NoinitforVar();
        }
        Context.getInstance().EndVarDef();
        return null;
    }

    @Override
    public Integer visitInitialiser(ECMAScriptParser.InitialiserContext ctx) {
        //TODO be careful about the Initial Value
        visit(ctx.singleExpression());
        return null;
    }

    @Override
    public Integer visitEmptyStatement(ECMAScriptParser.EmptyStatementContext ctx){
        return null;
    }

    @Override
    public Integer visitIdentifierExpression(ECMAScriptParser.IdentifierExpressionContext ctx){
        int index;
        index = ExpressionQueue.getInstance().nextIndex();
        Context.getInstance().pushExpression(index);
        ExpressionQueue.getInstance().createExpression(3);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.identifier = ctx.Identifier().getText();
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitExpressionStatement(ECMAScriptParser.ExpressionStatementContext ctx){
        //TODO deal with Expressions, need to know how it works
        visit(ctx.expressionSequence());
        return null;
    }

    @Override
    public Integer visitIfStatement(ECMAScriptParser.IfStatementContext ctx) {
        Context.getInstance().Ifstart();
        visit(ctx.expressionSequence());
        Context.getInstance().IfcondSet();
        visit(ctx.statement(0));
        if (ctx.Else() != null) {
            Context.getInstance().ElseSet();
            visit(ctx.statement(1));
        }
        Context.getInstance().EndofIf();
        return null;
    }

    @Override
    public Integer visitDoStatement(ECMAScriptParser.DoStatementContext ctx) {
        //TODO Doesn't care
        return null;
    }

    @Override
    public Integer visitWhileStatement(ECMAScriptParser.WhileStatementContext ctx) {
        Context.getInstance().WhileStatementBegin();
        visit(ctx.expressionSequence());
        Context.getInstance().WhileStatementEnd();
        visit(ctx.statement());
        Context.getInstance().WhileStatementBodyEnd();
        return null;
    }

    @Override
    public Integer visitForStatement(ECMAScriptParser.ForStatementContext ctx) {
        Context.getInstance().ForStatementBegin();
        visit(ctx.expressionSequence(0));
        visit(ctx.expressionSequence(1));
        visit(ctx.expressionSequence(2));
        Context.getInstance().ForStatementEnd();
        visit(ctx.statement());
        Context.getInstance().ForStatementBodyEnd();
        return null;
    }

    @Override
    public Integer visitForVarStatement(ECMAScriptParser.ForVarStatementContext ctx) {
        // TODO unsupported
        //TODO signal a for statement
        visit(ctx.variableDeclarationList());
        //TODO signal first expression
        if (ctx.expressionSequence(0) != null) {
            visit(ctx.expressionSequence(0));
            //TODO signal second expression
        }
        if (ctx.expressionSequence(1) != null) {
            visit(ctx.expressionSequence(1));
            //TODO signal third expression
        }
        visit(ctx.statement());
        return null;
    }

    @Override
    public Integer visitForInStatement(ECMAScriptParser.ForInStatementContext ctx) {

        //TODO unsupported
        return null;
    }

    @Override
    public Integer visitForVarInStatement(ECMAScriptParser.ForVarInStatementContext ctx) {
        //TODO unsupported
        return null;
    }

    @Override
    public Integer visitContinueStatement(ECMAScriptParser.ContinueStatementContext ctx) {
        Context.getInstance().SignalContinue();
        return null;
    }

    @Override
    public Integer visitBreakStatement(ECMAScriptParser.BreakStatementContext ctx) {
        Context.getInstance().SignalBreak();
        return null;
    }

    @Override
    public Integer visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {
        Context.getInstance().SignalReturn();
        if (ctx.expressionSequence() != null) {
            visit(ctx.expressionSequence());
        } else {
            //TODO make return value 0
        }
        Context.getInstance().EndofReturn();
        return null;
    }

    @Override
    public Integer visitWithStatement(ECMAScriptParser.WithStatementContext ctx) {
        //TODO Don't consider With Statement now
        //TODO Don't need
        return null;
    }

    @Override
    public Integer visitSwitchStatement(ECMAScriptParser.SwitchStatementContext ctx) {
        //TODO signal a Switch Statement
        visit(ctx.expressionSequence());
        visit(ctx.caseBlock());
        return null;
    }

    @Override
    public Integer visitCaseBlock(ECMAScriptParser.CaseBlockContext ctx) {
        //TODO maybe Signal a Block Begin
        //only consider case and default are separate
        if (ctx.getChild(1).getClass() == ECMAScriptParser.CaseClausesContext.class) {
            visit(ctx.caseClauses(0));
            if (ctx.defaultClause() != null) {
                visit(ctx.defaultClause());
            }
            if (ctx.caseClauses(1) != null) {
                visit(ctx.caseClauses(1));
            }
        } else {
            if (ctx.defaultClause() != null) {
                visit(ctx.defaultClause());
            }
            if (ctx.caseClauses(1) != null) {
                visit(ctx.caseClauses(1));
            }
        }
        //TODO Signal a Block End
        return null;
    }

    @Override
    public Integer visitCaseClauses(ECMAScriptParser.CaseClausesContext ctx) {
        int i = 0;
        while (ctx.caseClause(i) != null) {
            visit(ctx.caseClause(i));
            i++;
        }
        return null;
    }

    @Override
    public Integer visitCaseClause(ECMAScriptParser.CaseClauseContext ctx) {
        //TODO Signal a case clause
        visit(ctx.expressionSequence());
        visit(ctx.statementList());
        return null;
    }

    @Override
    public Integer visitDefaultClause(ECMAScriptParser.DefaultClauseContext ctx) {
        //TODO Signal a default clause
        visit(ctx.statementList());
        return null;
    }

    @Override
    public Integer visitLabelledStatement(ECMAScriptParser.LabelledStatementContext ctx) {
        //TODO don't deal with Labelled now
        return null;
    }

    @Override
    public Integer visitThrowStatement(ECMAScriptParser.ThrowStatementContext ctx) {
        //TODO don't deal with Throw Statement now
        return null;
    }

    @Override
    public Integer visitTryStatement(ECMAScriptParser.TryStatementContext ctx) {
        //TODO Don't need
        return null;
    }

    @Override
    public Integer visitCatchProduction(ECMAScriptParser.CatchProductionContext ctx) {
        //TODO Don't need
        return null;
    }

    @Override
    public Integer visitFinallyProduction(ECMAScriptParser.FinallyProductionContext ctx) {
        //TODO Don't need
        return null;
    }

    @Override
    public Integer visitDebuggerStatement(ECMAScriptParser.DebuggerStatementContext ctx) {
        //TODO Don't need
        return null;
    }

    @Override
    public Integer visitFunctionDeclaration(ECMAScriptParser.FunctionDeclarationContext ctx) {
        Context.getInstance().FunctionDeclaration();
        Context.getInstance().setIdentifier(ctx.Identifier().getText());
        if (ctx.formalParameterList() != null) {
            visit(ctx.formalParameterList());//TODO for the Parameter
        }
        Context.getInstance().FunctionBodyBegin();
        visit(ctx.functionBody());
        Context.getInstance().FunctionBodyEnd();
        return null;
    }

    @Override
    public Integer visitFormalParameterList(ECMAScriptParser.FormalParameterListContext ctx) {
        int i = 0;
        ArrayList<Integer> Para = Context.getInstance().SetFunctionPara();
        while (ctx.Identifier(i) != null) {
            int index = ExpressionQueue.getInstance().nextIndex();
            Para.add(index);
            ExpressionQueue.getInstance().createExpression(3);
            Expression E = ExpressionQueue.getInstance().Geti(index);
            E.identifier = ctx.Identifier(i).getText();
            i++;
        }
        return null;
    }

    @Override
    public Integer visitFunctionBody(ECMAScriptParser.FunctionBodyContext ctx) {
        if (ctx.sourceElements()!= null) {
            visit(ctx.sourceElements());
        }
        return null;
    }




    @Override
    public Integer visitArrayLiteral(ECMAScriptParser.ArrayLiteralContext ctx) {
        //TODO Don't know how it works but need to be done
        //Array Work.
        return null;
    }

    @Override
    public Integer visitElementList(ECMAScriptParser.ElementListContext ctx) {
        //TODO
        return null;
    }

    @Override
    public Integer visitElision(ECMAScriptParser.ElisionContext ctx) {
        //TODO
        return null;
    }

    @Override
    public Integer visitObjectLiteral(ECMAScriptParser.ObjectLiteralContext ctx) {
        //TODO
        return null;
    }

    @Override
    public Integer visitPropertyNameAndValueList(ECMAScriptParser.PropertyNameAndValueListContext ctx) {
        //TODO
        return null;
    }

    @Override
    public Integer visitPropertyExpressionAssignment(ECMAScriptParser.PropertyExpressionAssignmentContext ctx) {
        //TODO
        return null;
    }

    @Override
    public Integer visitPropertyGetter(ECMAScriptParser.PropertyGetterContext ctx) {
        return null;
    }

    @Override
    public Integer visitPropertySetter(ECMAScriptParser.PropertySetterContext ctx) {
        return null;
    }

    @Override
    public Integer visitPropertyName(ECMAScriptParser.PropertyNameContext ctx) {
        return null;
    }

    @Override
    public Integer visitPropertySetParameterList(ECMAScriptParser.PropertySetParameterListContext ctx) {
        return null;
    }





    @Override
    public Integer visitArguments(ECMAScriptParser.ArgumentsContext ctx) {
        visit(ctx.argumentList());
        return null;
    }

    @Override
    public Integer visitArgumentList(ECMAScriptParser.ArgumentListContext ctx) {
        int i = 0;
        while (ctx.singleExpression(i) != null) {
            visit(ctx.singleExpression(i));
            i++;
        }
        return null;
    }

    @Override
    public Integer visitExpressionSequence(ECMAScriptParser.ExpressionSequenceContext ctx) {
        int i = 0;
        for (i = 0;;i++) {
            if (ctx.singleExpression(i) != null) {
                visit(ctx.singleExpression(i));
            } else break;
        }

        return null;
    }

    @Override
    public Integer visitTernaryExpression(ECMAScriptParser.TernaryExpressionContext ctx) {
        //?: Don't care
        return null;
    }

    @Override
    public Integer visitLogicalAndExpression(ECMAScriptParser.LogicalAndExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 1;
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression(0));
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitPreIncrementExpression(ECMAScriptParser.PreIncrementExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(5);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 7;
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitObjectLiteralExpression(ECMAScriptParser.ObjectLiteralExpressionContext ctx) {
        return null;
    }

    @Override
    public Integer visitInExpression(ECMAScriptParser.InExpressionContext ctx) {
        // Don't Care
        return null;
    }

    @Override
    public Integer visitLogicalOrExpression(ECMAScriptParser.LogicalOrExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 2;
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression(0));
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitNotExpression(ECMAScriptParser.NotExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(5);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 11;
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitPreDecreaseExpression(ECMAScriptParser.PreDecreaseExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(5);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 8;
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitArgumentsExpression(ECMAScriptParser.ArgumentsExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(10);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression());
        Context.getInstance().VisitArgument(index);
        visit(ctx.arguments());
        E.Parameter = Context.getInstance().AskForArgument();
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitThisExpression(ECMAScriptParser.ThisExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(4);
        Context.getInstance().pushExpression(index);
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitFunctionExpression(ECMAScriptParser.FunctionExpressionContext ctx) {
        return null;
    }

    @Override
    public Integer visitUnaryMinusExpression(ECMAScriptParser.UnaryMinusExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(5);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 10;
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitPostDecreaseExpression(ECMAScriptParser.PostDecreaseExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(6);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 8;
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitAssignmentExpression(ECMAScriptParser.AssignmentExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 6;
        int left = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        E.lefthand = left;
        int right = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.expressionSequence());//TODO be careful
        E.righthand = right;
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitTypeofExpression(ECMAScriptParser.TypeofExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(9);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitInstanceofExpression(ECMAScriptParser.InstanceofExpressionContext ctx) {
        //don't care
        return null;
    }

    @Override
    public Integer visitUnaryPlusExpression(ECMAScriptParser.UnaryPlusExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(5);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 9;
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitDeleteExpression(ECMAScriptParser.DeleteExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(7);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        if (ctx.getChild(1).getText().equals("==")) {
            E.operator = 12;
        } else {
            if (ctx.getChild(1).getText().equals("!=")) {
                E.operator = 13;
            } else {
                System.err.println("Wrong Operator");

            }
        }
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression(0));
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitBitXOrExpression(ECMAScriptParser.BitXOrExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 4;
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression(0));
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitMultiplicativeExpression(ECMAScriptParser.MultiplicativeExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        String temp = ctx.getChild(1).getText();
        if (temp.equals("*")) {
            E.operator = 19;
        } else {
            if (temp.equals("/")) {
                E.operator = 20;
            } else {
                if (temp.equals("%")) {
                    E.operator = 21;
                }
            }
        }
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression(0));
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitBitShiftExpression(ECMAScriptParser.BitShiftExpressionContext ctx) {
        //Don't care
        return null;
    }

    @Override
    public Integer visitParenthesizedExpression(ECMAScriptParser.ParenthesizedExpressionContext ctx) {
        visit(ctx.expressionSequence());
        return null;
    }

    @Override
    public Integer visitAdditiveExpression(ECMAScriptParser.AdditiveExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        String temp = ctx.getChild(1).getText();
        if (temp.equals("+")) {
            E.operator = 9;
        } else {
            if (temp.equals("-")) {
                E.operator = 10;
            }
        }
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression(0));
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitRelationalExpression(ECMAScriptParser.RelationalExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        String temp = ctx.getChild(1).getText();
        if (temp.equals("<")) {
            E.operator = 15;
        } else {
            if (temp.equals(">")) {
                E.operator = 16;
            } else {
                if (temp.equals("<=")) {
                    E.operator = 17;
                } else {
                    if (temp.equals(">=")) {
                        E.operator = 18;
                    }
                }
            }
        }
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression(0));
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public  Integer visitPostIncrementExpression(ECMAScriptParser.PostIncrementExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(6);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 7;
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitBitNotExpression(ECMAScriptParser.BitNotExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(5);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.operator = 14;
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitNewExpression(ECMAScriptParser.NewExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(13);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().NewExpressionBegin();
        E.righthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        Context.getInstance().NewExpressionEnd();
        return null;
    }

    @Override
    public Integer visitLiteralExpression(ECMAScriptParser.LiteralExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(1);
        Context.getInstance().pushExpression(index);
        visit(ctx.literal());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitArrayLiteralExpression(ECMAScriptParser.ArrayLiteralExpressionContext ctx) {
        return null;
    }

    @Override
    public Integer visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(12);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        E.identifier = ctx.identifierName().getText();
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public  Integer visitMemberIndexExpression(ECMAScriptParser.MemberIndexExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(11);
        Context.getInstance().pushExpression(index);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        E.lefthand = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.singleExpression());
        E.index = ExpressionQueue.getInstance().nextIndex();
        visit(ctx.expressionSequence());
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitBitAndExpression(ECMAScriptParser.BitAndExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 3;
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression(0));
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitBitOrExpression(ECMAScriptParser.BitOrExpressionContext ctx) {
        int index = ExpressionQueue.getInstance().nextIndex();
        ExpressionQueue.getInstance().createExpression(2);
        Expression E = ExpressionQueue.getInstance().Geti(index);
        Context.getInstance().pushExpression(index);
        E.operator = 5;
        int left = ExpressionQueue.getInstance().nextIndex();
        E.lefthand = left;
        visit(ctx.singleExpression(0));
        int right = ExpressionQueue.getInstance().nextIndex();
        E.righthand = right;
        visit(ctx.singleExpression(1));
        Context.getInstance().EndExpression(index);
        return null;
    }

    @Override
    public Integer visitAssignmentOperatorExpression(ECMAScriptParser.AssignmentOperatorExpressionContext ctx) {
        //Don't Care
        return null;
    }

    @Override
    public Integer visitVoidExpression(ECMAScriptParser.VoidExpressionContext ctx) {
        //Don't Care;
        return null;
    }

    @Override
    public Integer visitAssignmentOperator(ECMAScriptParser.AssignmentOperatorContext ctx) {
        //Don't Care;
        return null;
    }

    @Override
    public Integer visitLiteral(ECMAScriptParser.LiteralContext ctx) {
        String temp;
        int index = ExpressionQueue.getInstance().nextIndex()-1;
        Expression E = ExpressionQueue.getInstance().Geti(index);
        if (ctx.BooleanLiteral() != null) {
            temp = ctx.BooleanLiteral().getText();
            if (temp.equals("true")) {
                E.booleanExp = 1;
            } else {
                E.booleanExp = -1;
            }
        } else {
            E.booleanExp = 0;
            if (ctx.StringLiteral() != null) {
                temp = ctx.StringLiteral().getText();
                E.StringData = temp;
                E.StringData.replace("\'","");
                E.StringData.replace("\"","");
            } else {
                E.StringData = null;
                if (ctx.NullLiteral() != null) {
                    E.Null = 1;
                } else {
                    E.Null = 0;
                    visit(ctx.numericLiteral());
                }
            }
        }
        return null;
    }

    @Override
    public Integer visitNumericLiteral(ECMAScriptParser.NumericLiteralContext ctx) {
        if (ctx.DecimalLiteral() != null) {
            double temp = (new Double(ctx.DecimalLiteral().getText()));
            int index = ExpressionQueue.getInstance().nextIndex()-1;
            Expression E = ExpressionQueue.getInstance().Geti(index);
            E.numerical = temp;
        } else {
            System.err.println("Unsupported Numerical Type! ");
        }
        return null;
    }

    @Override
    public Integer visitIdentifierName(ECMAScriptParser.IdentifierNameContext ctx) {
        Context.getInstance().setIdentifier(ctx.getText());
        return null;
    }

    @Override
    public Integer visitReservedWord(ECMAScriptParser.ReservedWordContext ctx) {
        return null;
    }

    @Override
    public Integer visitKeyword(ECMAScriptParser.KeywordContext ctx) {
        return null;
    }

    @Override
    public Integer visitFutureReservedWord(ECMAScriptParser.FutureReservedWordContext ctx) {
        return null;
    }

    @Override
    public Integer visitGetter(ECMAScriptParser.GetterContext ctx) {
        return null;
    }

    @Override
    public Integer visitSetter(ECMAScriptParser.SetterContext ctx) {
        return null;
    }

    @Override
    public Integer visitEos(ECMAScriptParser.EosContext ctx) {
        return null;
    }

    @Override
    public Integer visitEof(ECMAScriptParser.EofContext ctx) {
        return null;
    }
}
