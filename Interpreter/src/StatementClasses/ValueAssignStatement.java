package StatementClasses;

import java.awt.RenderingHints;

import BaseClasses.Statement;
import BaseClasses.Value;
import BaseClasses.Variable;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
public class ValueAssignStatement extends Statement{
	private String leftvalue;
	private Value rightvalue;
	private String rightvariable;
	private FuncCallStatement funcCallStatement;
	
	public ValueAssignStatement(String leftvalue, Value rightvalue) {
		super();
		this.leftvalue = leftvalue;
		this.rightvalue = rightvalue;
		this.setType(2);
	}

	public ValueAssignStatement(String leftvalue, String rightvariable) {
		super();
		this.leftvalue = leftvalue;
		this.rightvariable = rightvariable;
	}

	public ValueAssignStatement(String leftvalue, FuncCallStatement funcCallStatement) {
		super();
		this.leftvalue = leftvalue;
		this.funcCallStatement = funcCallStatement;
		this.setType(2);
	}

	public boolean executeStatement(){
		System.out.println("excecute-valueAssignStatement");
		boolean result =true;
		
		Variable variable=Executor.getVariable(leftvalue);
		if(variable.getScope()!=-1){
			if(rightvalue!=null){
				variable.setValue(rightvalue);
			}
			else if(rightvariable!=null){
				variable.setValue(Executor.getVariable(rightvariable).getValue());
			}
			else{
				funcCallStatement.executeStatement();
				Value value=Executor.getReturnvalue();
				variable.setValue(value);
			}
		}
		else{
			System.out.println("bucunzaibianliang");
		}
		return result;
	}
}
