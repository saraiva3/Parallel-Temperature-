

import java.io.Serializable;
import java.util.concurrent.RecursiveAction;


//import jsr166y.RecursiveAction;


public class Node extends RecursiveAction implements Serializable {
	
	//a number of child nodes
	private Node a;
	private Node b;
	private Node c;
	private Node d;
	
	public Node() { }
	

	private Node(double[][] gridA, double[][] gridB, 
			           double[] tempConst,
			           int threshold,
					   int loX, int hiX,
					   int loY, int hiY,int flag) {
		//find midpoints to decompose current grid correctly
		int midX = (loX + hiX) / 2;
		int midY = (loY + hiY) / 2;
		
		//compute threshold area
		int ar = ((hiX - loX) + 1) * ((hiY - loY) + 1);
	
		if (ar <= threshold * 3) {
			//if smaller than threshold, decompose problem into
			//4 sectors that will compute the task
			a = new LeafNode(gridA, gridB, tempConst, loX, midX, loY, midY);
			b = new LeafNode(gridA, gridB, tempConst, midX, hiX, loY, midY);
			c = new LeafNode(gridA, gridB, tempConst, loX, midX, midY, hiY);
			d = new LeafNode(gridA, gridB, tempConst, midX, hiX, midY, hiY);

		}
		else {
			//if larger than threshold, decompose problem into
			//4 sectors that in turn will potentially decompose the problem
			a = new Node(gridA, gridB, tempConst, threshold, loX, midX, loY, midY);
			b = new Node(gridA, gridB, tempConst, threshold, midX, hiX, loY, midY);
			c = new Node(gridA, gridB, tempConst, threshold, loX, midX, midY, hiY);
			d = new Node(gridA, gridB, tempConst, threshold, midX, hiX, midY, hiY);
		}
	}
	
	
	public Node(double[][] gridA, double[][] gridB, 
			           double[] tempConst,
                       int threshold,
			           int loX, int hiX,
			           int loY, int hiY) {
		
		//find midpoints to decompose current grid correctly
		int midX = (loX + hiX) / 2;
		int midY = (loY + hiY) / 2;
		
		a = new Node(gridA, gridB, tempConst, threshold, loX, midX, loY, midY,1);
		b = new Node(gridA, gridB, tempConst, threshold, midX, hiX, loY, midY,1);
		c = new Node(gridA, gridB, tempConst, threshold, loX, midX, midY, hiY,1);
		d = new Node(gridA, gridB, tempConst, threshold, midX, hiX, midY, hiY,1);		
	}

	public double[][] getGrid() {
		
		return this.a.getGrid();
	}
	
	
	public Node[] getNodes() {
		Node[] n = {a, b, c, d};
		return n;
	}
	

	public void heatCorners(double deltaTemp1, double deltaTemp2) {

	    
		this.a.heatCorners(deltaTemp1, deltaTemp2);
		this.b.heatCorners(deltaTemp1, deltaTemp2);
		this.c.heatCorners(deltaTemp1, deltaTemp2);
		this.d.heatCorners(deltaTemp1, deltaTemp2);
	}


	@Override
	protected void compute() {
		a.fork();
		b.fork();
		c.fork();
		d.invoke();
		c.join();
		b.join();
		a.join();
	}
}