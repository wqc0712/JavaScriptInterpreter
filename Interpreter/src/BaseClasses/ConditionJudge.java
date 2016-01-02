/**
 * 
 */
package BaseClasses;

/**
 * @author viki
 *
 */
public class ConditionJudge {
	private Value operator1;
	private Value operator2;
	private String operate;
	
	public boolean getJudgeResult(){
		boolean result=false;
		switch (operate) {
		case "<":
			if(operator1.lessthan(operator2))
				result=true;
			else {
				result=false;
			}
			break;
		case "<=":
			if(operator1.lessthan(operator2)||operator1.equalsto(operator2))
				result=true;
			else {
				result=false;
			}
			break;
		case "==":
			if(operator1.equalsto(operator2))
				result=true;
			else {
				result=false;
			}
			break;
		case ">=":
			if(operator1.biggerthan(operator2)||operator1.equalsto(operator2))
				result=true;
			else {
				result=false;
			}
			break;
		case ">":
			if(operator1.biggerthan(operator2))
				result=true;
			else {
				result=false;
			}
			break;
		case "!":
			if(operator1.getType()==3&&operator1.getBooleanvalue()==false){
				result=true;
			}
		default:
			break;
		}
		return result;
	}
	
	public Value getOperator1() {
		return operator1;
	}
	public void setOperator1(Value operator1) {
		this.operator1 = operator1;
	}
	public Value getOperator2() {
		return operator2;
	}
	public void setOperator2(Value operator2) {
		this.operator2 = operator2;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}

}
