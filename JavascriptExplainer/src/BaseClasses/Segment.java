package BaseClasses;

public class Segment {
	public Integer type;//3-if；4-else；5-for；6-while；7-switch；8-函数调用；
	//9-函数定义；10-新建代码段；12-case；13-break；
	public boolean trueOrfalse; //
	public Segment(Integer type) {
		super();
		this.type = type;
	}
	
}
