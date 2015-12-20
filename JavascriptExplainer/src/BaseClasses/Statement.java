package BaseClasses;

/**
 * @author viki
 *
 */
public class Statement {
	private int type;//1-变量声明；2-变量赋值；3-if；4-else；5-for；6-while；7-switch；8-函数调用；
	//9-函数定义；10-新建代码段；11-代码段结束；12-case；13-break；

	public boolean executeStatement(){
		boolean result =true;
		System.out.println("statement");
		return result;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
