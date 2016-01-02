/**
 * 
 */
package BaseClasses;

/**
 * @author viki
 *
 */
public class Value {
		int type;//1-int;2-string;3-boolean;4-double;
	
		int intvalue;
		boolean booleanvalue;
		String stringvalue;
		double doublevalue;

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
		public Value(int intvalue,int type) {
			super();
			this.intvalue = intvalue;
			this.type=type;
		}
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
		
}
