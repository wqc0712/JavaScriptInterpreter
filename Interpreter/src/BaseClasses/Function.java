package BaseClasses;

import java.util.ArrayList;

public class Function {
	private String name;
	private ArrayList<String> paramList=new ArrayList<String>();
	private ArrayList<Statement> functionBody=new ArrayList<Statement>();
	private int scope;
	public Function(String name,ArrayList<String>paramList) {
		super();
		this.name = name;
		this.paramList=paramList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Statement> getFunctionBody() {
		return functionBody;
	}
	public void setFunctionBody(ArrayList<Statement> functionBody) {
		this.functionBody = functionBody;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public ArrayList<String> getParamList() {
		return paramList;
	}
	public void setParamList(ArrayList<String> paramList) {
		this.paramList = paramList;
	}
	
}
