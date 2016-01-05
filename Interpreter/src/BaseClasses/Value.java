/**
 * 
 */
package BaseClasses;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author viki
 *
 */
public class Value {
	int type;//1-int;2-string;3-boolean;4-double;5-array;6-object
	//int不用了
	boolean isNull=false;
	int intvalue;
	boolean booleanvalue;
	String stringvalue;
	double doublevalue;
	ArrayList<Value> arrayvalue;
	Map<String,Value> objectvalue;
	int index;//object,function

	//constructors
	public Value(double doublevalue,int type) {
			super();
			this.doublevalue = doublevalue;
			this.type=type;
		}
	public Value(String stringvalue,int type) {
			super();
			this.stringvalue = stringvalue;
			this.type=type;
		}
	public Value(boolean booleanvalue,int type) {
			super();
			this.booleanvalue = booleanvalue;
			this.type=type;
		}

	public  Value(ArrayList<Value> arrayvalue){super();this.type=5;this.arrayvalue=arrayvalue;}
	public  Value(){super();}
	public Value(Map<String, Value> objectvalue) {
		this.objectvalue = objectvalue;
	}
	//get&set
	public int getType() {
			return type;
		}
	public int getIntvalue() {
			return intvalue;
		}
	public boolean getBooleanvalue() {
			return booleanvalue;
		}
	public String getStringvalue() {
			return stringvalue;
		}
	public double getDoublevalue() {
			return doublevalue;
		}
	public ArrayList<Value> getArrayvalue(){
			return arrayvalue;
		}
	public Map<String, Value> getObjectvalue() {
		return objectvalue;
	}
	public void setNull(boolean aNull) {
		isNull = aNull;
	}
	public boolean isNull() {
		return isNull;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setIntvalue(int intvalue) {
		this.intvalue = intvalue;
	}

	public void setBooleanvalue(boolean booleanvalue) {
		this.booleanvalue = booleanvalue;
	}

	public void setStringvalue(String stringvalue) {
		this.stringvalue = stringvalue;
	}

	public void setDoublevalue(double doublevalue) {
		this.doublevalue = doublevalue;
	}

	public void setArrayvalue(ArrayList<Value> arrayvalue) {
		this.arrayvalue = arrayvalue;
	}

	public void setObjectvalue(Map<String, Value> objectvalue) {
		this.objectvalue = objectvalue;
	}

	//compute
	public boolean lessthan(Value operator2){
		boolean result=false;
		if(this.type==1){
			if(operator2.type==1){
				if(this.intvalue<operator2.getIntvalue()){
					result=true;
				}
			}
			else if (operator2.type==4) {
				if(this.intvalue<operator2.getDoublevalue()){
					result=true;
				}
			}
		}
		else if(this.type==4){
			if(operator2.type==1){
				if(this.doublevalue<operator2.getIntvalue()){
					result=true;
				}
			}
			else if (operator2.type==4) {
				if(this.doublevalue<operator2.getDoublevalue()){
					result=true;
				}
			}
		}
		return result;
	}
	public boolean biggerthan(Value operator2){
		boolean result=false;
		if(this.type==1){
			if(operator2.type==1){
				if(this.intvalue>operator2.getIntvalue()){
					result=true;
				}
			}
			else if (operator2.type==4) {
				if(this.intvalue>operator2.getDoublevalue()){
					result=true;
				}
			}
		}
		else if(this.type==4){
			if(operator2.type==1){
				if(this.doublevalue>operator2.getIntvalue()){
					result=true;
				}
			}
			else if (operator2.type==4) {
				if(this.doublevalue>operator2.getDoublevalue()){
					result=true;
				}
			}
		}
		return result;
	}
	public boolean equalsto(Value operator2){
		boolean result=false;
		if(this.type==operator2.type){
			if(this.type==1){
				if(this.intvalue==operator2.intvalue){
					result=true;
				}
			}
			else if (this.type==2) {
				if (this.stringvalue.equals(operator2.stringvalue)) {
					result=true;
				}
			}
			else if (this.type==3){
				if(this.booleanvalue==operator2.booleanvalue){
					result=true;
				}
			}
			else {
				if(this.doublevalue==operator2.doublevalue){
					result=true;
				}
			}
		}
		else{
			if(this.type==1){
				if (operator2.type==4) {
					if(this.intvalue==operator2.getDoublevalue()){
						result=true;
					}
				}
			}
			else if(this.type==4){
				if(operator2.type==1){
					if(this.doublevalue==operator2.getIntvalue()){
						result=true;
					}
				}
			}
		}
		return result;
	}

	public Value logicAnd(Value valueb) throws Exception {
		Value result=new Value();
		if(this.type!=3||valueb.getType()!=3){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			boolean bv=this.getBooleanvalue()&&valueb.getBooleanvalue();
			result.setBooleanvalue(bv);
			result.setType(3);
		}
		return result;
	}
	public Value logicOr(Value valueb) throws Exception {
		Value result=new Value();
		if(this.type!=3||valueb.getType()!=3){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			boolean bv=this.getBooleanvalue()||valueb.getBooleanvalue();
			result.setBooleanvalue(bv);
			result.setType(3);
		}
		return result;
	}
	public Value bitAnd(Value valueb){
		Value result=new Value();
		return result;
	}
	public Value bitXOr(Value valueb){
		Value result=new Value();
		return result;
	}
	public Value bitOr(Value valueb){
		Value result=new Value();
		return result;
	}

	public Value add(Value valueb)throws Exception{
		Value result=new Value();
		if((this.getType()!=1&&this.getType()!=2&&this.getType()!=4)||(valueb.getType()!=1&&valueb.getType()!=2&&valueb.getType()!=4)){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			if(this.getType()==4){
				if(valueb.getType()==4){
					double dv=this.getDoublevalue()+valueb.getDoublevalue();
					result.setDoublevalue(dv);
					result.setType(4);
				}
				else{
					String sv=this.getDoublevalue()+valueb.getStringvalue();
					result.setStringvalue(sv);
					result.setType(2);
				}
			}
			else{
				if (valueb.getType()==4){
					String sv=this.getStringvalue()+valueb.getDoublevalue();
					result.setStringvalue(sv);
					result.setType(2);
				}
				else{
					String sv=this.getStringvalue()+valueb.getStringvalue();
					result.setStringvalue(sv);
					result.setType(2);
				}
			}
		}
		return result;
	}
	public Value minus(Value valueb) throws Exception {
		Value result=new Value();
		if(this.getType()!=4||valueb.getType()!=4){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			double dv=this.getDoublevalue()-valueb.getDoublevalue();
			result.setDoublevalue(dv);
			result.setType(4);
		}
		return result;
	}
	public Value multiple(Value valueb) throws Exception {
		Value result=new Value();
		if(this.getType()!=4||valueb.getType()!=4){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			double dv=this.getDoublevalue()*valueb.getDoublevalue();
			result.setDoublevalue(dv);
			result.setType(4);
		}
		return result;
	}
	public Value divide(Value valueb) throws Exception {
		Value result=new Value();
		if(this.getType()!=4||valueb.getType()!=4){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			double dv=this.getDoublevalue()/valueb.getDoublevalue();
			result.setDoublevalue(dv);
			result.setType(4);
		}
		return result;
	}
	public Value mod(Value valueb) throws Exception {
		Value result=new Value();
		if(this.getType()!=4||valueb.getType()!=4){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			double dv=this.getDoublevalue()%valueb.getDoublevalue();
			result.setDoublevalue(dv);
			result.setType(4);
		}
		return result;
	}

	public Value addself(boolean valueb) throws Exception {
		Value result=new Value();

		if(this.getType()!=4){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			if(valueb){//a++
				result=this;
			}
			else{//++a
				Value vb=new Value(1,4);
				result=this.add(vb);
				result.setType(4);
			}
		}
		return result;
	}
	public Value minusself(boolean valueb) throws Exception {
		Value result=new Value();

		if(this.getType()!=4){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			if(valueb){//a++
				result=this;
			}
			else{//++a
				Value vb=new Value(1,4);
				result=this.minus(vb);
				result.setType(4);
			}
		}
		return result;
	}
	public Value not() throws Exception {
		Value result=new Value();
		if(this.type!=3){
			throw new Exception("对象类型不支持该运算符.");
		}
		else{
			boolean bv=!this.getBooleanvalue();
			result.setBooleanvalue(bv);
			result.setType(3);
		}
		return result;
	}
}
