package StatementClasses;

import BaseClasses.Statement;
import BaseClasses.Variable;
import StatementExecuteClasses.Executor;

/**
 * @author viki
 *
 */
//1
public class VarDeclStatement extends Statement {
	
	private String varname;
	
	public VarDeclStatement(String varname) {
		super();
		this.varname = varname;
		super.setType(1);
	}

	public boolean executeStatement(){
		boolean result =true;
//		System.out.println("excecute-varDeclStatement");
		
//		Executor executor=new Executor();
		Variable variable=new Variable(varname,Executor.currentScope);
		Executor.varArraylist.add(variable);
		return result;
	}
	public String getVarname() {
		return varname;
	}
	public void setVarname(String varname) {
		this.varname = varname;
	}

}
