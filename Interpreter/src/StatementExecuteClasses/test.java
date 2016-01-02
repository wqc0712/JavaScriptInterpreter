package StatementExecuteClasses;

import BaseClasses.Value;

public class test {
	public static void main(String[] args){
		Value value1=new Value(true,3);
		Value value2=new Value(33, 1);
		Value value3=new Value(22.2, 4);
		Value value4=new Value(true, 3);
		System.out.println(value1.lessthan(value2));
		System.out.println(value2.biggerthan(value3));
		System.out.println(value1.equalsto(value4));
	}
}
