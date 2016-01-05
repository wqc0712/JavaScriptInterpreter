package BaseClasses;

import java.util.ArrayList;

public class Segment {
	public Integer type;//3-if；4-else；5-for；6-while；7-switch；8-函数调用；
	//9-函数定义；10-新建代码段；12-case；13-break；
	public boolean trueOrfalse; //
    public int whileindex = 0;
    public boolean whilerepeat = true;
    public boolean forrepeat = true;
    //	public int forindex1 = 0;
    public int forindex2 = 0;
    public int forindex3 = 0;
    public ArrayList<Statement> whilestatement = new ArrayList<Statement>();
    public ArrayList<Statement> forstatement = new ArrayList<Statement>();
	public Segment(Integer type) {
		super();
		this.type = type;
	}
	
}
