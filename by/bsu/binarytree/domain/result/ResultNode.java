package by.bsu.binarytree.domain.result;

public class ResultNode {
	
	private StringBuilder code;
	private int h;
	
	public ResultNode(int h, StringBuilder code) {
		super();
		this.code = code;
		this.h = h;
	}
	
	public StringBuilder getCode() {
		return code;
	}
	
	public void deleteCode() {
		code = new StringBuilder("222222");
	}
	
	public int getH() {
		return h;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + h;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ResultNode))
			return false;
		final ResultNode other = (ResultNode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.toString().equals(other.code.toString()))
			return false;
		if (h != other.h)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "code: " + code + " h: " + h;
	}

}
