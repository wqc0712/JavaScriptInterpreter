/**
 * 
 */
package BaseClasses;

/**
 * @author viki
 *
 */
public class Variable {
	String name;
	public Value value;
	private int scope;//作用域
	
	public Variable(String name, int scope) {
		super();
		this.name = name;
		this.scope = scope;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	
}
