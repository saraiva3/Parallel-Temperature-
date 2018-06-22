

import java.io.Serializable;

public class DistributableNode implements Serializable {
	
	final Node node;
	final int iterations;
	final double deltaTemp1;
	final double deltaTemp2;
	
	public DistributableNode(Node node, int i, double deltaTemp1, double deltaTemp2) {
		this.node = node;
		this.iterations = i;
		this.deltaTemp1 = deltaTemp1;
		this.deltaTemp2 = deltaTemp2;
	}
}