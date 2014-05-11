package by.bsu.binarytree.domain.result;

public class ResultRecord {
	public Integer nodeNumber;
	public ResultNode c;
	public ResultNode a;
	public ResultNode b;	

	public ResultRecord(int nodeNumber, ResultNode c, ResultNode a, ResultNode b) {
		super();
		this.nodeNumber = nodeNumber;
		this.c = c;
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return nodeNumber + "  c = " + c.getCode() + "  hc = " + c.getH() +
		       "  a = " + a.getCode() + "  ha = " + a.getH() +
		       "  b = " + b.getCode() + "  hb = " + b.getH();
	}

	public boolean isAParent(ResultRecord child) {
		//System.out.println(/*this.a + "\n" +*/child.c + "\n");
		return this.a.equals(child.c);
	}
	
	public boolean isBParent(ResultRecord child) {
		//System.out.println(/*this.b + "\n" +*/child.c + "\n");
		return this.b.equals(child.c);
	}
}
