


import java.io.Serializable;



public class LeafNode extends Node implements Serializable {
	//two different matrices to allow for "hot-swapping"
	private final double[][] a;
	private final double[][] b;
	//the private reference to the matrices that are "hot-swapped" to
	private double[][] src;
	private double[][] dest;
	private SimulationPanel simulation;
	//the value, by which the temperature change is delayed.
	//represents the thermal conductivity constant of the metal,
	//this LeafNode is representing.
	private final double[] tempConst;
	//the boundaries of the matrix, in which this LeafNode is working.
	private final int loX, hiX;
	private final int loY, hiY;
	//a local marker to determine if this is an even or odd step size.
	private int iteration = 0;
	
	public LeafNode(double[][] gridA, double[][] gridB,
			        double[] tempConst,
			        int loX, int hiX, int loY, int hiY) {
		this.hiX = hiX; this.hiY = hiY;
		this.loX = loX; this.loY = loY;
		this.a = gridA;
		this.b = gridB;
		this.tempConst = tempConst;
//		System.out.println("Number of updated fields in this LeafNode: " + ((hiX - loX) + 1) * ((hiY - loY) + 1));
//		System.out.println("New Node:: x: " + loX + " to " + hiX + "\n"
//				         + "           y: " + loY + " to " + hiY + "\n");
	}
	

	public double[][] getGrid() {
		return ((iteration & 1) == 0) ? a : b;
	}

	public void heatCorners(double deltaTemp1, double deltaTemp2) {

		this.a[0][0] = deltaTemp1;
		this.b[0][0] = deltaTemp1;
		this.a[this.a.length-1][this.a[0].length-1] = deltaTemp2;
		this.b[this.b.length-1][this.b[0].length-1] = deltaTemp2;
	}
	
	
	
	@Override
	protected void compute() {
//		System.out.println(Thread.currentThread().getName());
//		try {
//		Thread.sleep(10);
//		} catch (InterruptedException e) {}
		//simulation = SimulationPanel.getSimupanel();
		if ((iteration++ & 1) == 0) {
			src = a;
			dest = b;
		}
		else {
			src = b;
			dest = a;
		}
		
		for (int i = loX; i < hiX; ++i) {
			for (int j = loY; j < hiY; ++j) {
				
				//simulation.repaint();
			
				updateTemp(i, j);
				
			}
		}
	}

	private void updateTemp(int x, int y) throws ArrayIndexOutOfBoundsException {
		if (x > a.length || y > a[0].length)
			throw new ArrayIndexOutOfBoundsException("Temperature cannot be updated: Illegal Index.");
	
		double newTemp;
		
		//upper left
		//	=> 3 neighbors
		if (x == 0 && y == 0) {
			newTemp    = (src[x+1][y] +
				          src[x][y+1] +
				          src[x+1][y+1]
				          ) / 3;	
		}
		
		//upper right
		//	=> 3 neighbors
		else if (x == src.length-1 && y == 0) {
			newTemp    = (src[x-1][y] +
				          src[x][y+1] +
				          src[x-1][y+1]
		              	 ) / 3;
		}
		
		//lower left
		//	=> 3 neighbors
		else if (x == 0 && y == src[0].length-1) {
			newTemp    = (src[x+1][y] +
				          src[x][y-1] +
				          src[x+1][y-1]
				         ) / 3;
		}
		
		//lower right
		//	=> 3 neighbors
		else if (x == src.length-1 && y == src[0].length-1) {
			newTemp    = (src[x-1][y] +
					      src[x][y-1] +
					      src[x-1][y-1]
				          ) / 3;
		}
		
		//upper row
		//	=> 5 neighbors
		else if (y == 0) {
			newTemp    = (src[x+1][y] +
						  src[x-1][y] +
						  src[x][y+1] +
						  src[x+1][y+1] +
						  src[x-1][y+1]
			             ) / 5;
		}
		
		//lower row
		//	=> 5 neighbors
		else if (y == src[0].length-1) {
			newTemp    = (src[x+1][y] +
					      src[x-1][y] +
					      src[x][y-1] +
					      src[x-1][y-1] +
					      src[x+1][y-1]
				         ) / 5;
		}
		
		//left col
		//	=> 5 neighbors
		else if (x == 0) {
			newTemp    = (src[x+1][y] +
						  src[x][y+1] +
						  src[x][y-1] +
						  src[x+1][y-1] +
						  src[x+1][y+1]
			              ) / 5;
		}
		
		//right col
		//	=> 5 neighbors
		else if (x == src.length-1) {
			newTemp    = (src[x-1][y] +
						  src[x][y+1] +
						  src[x][y-1] +
						  src[x-1][y-1] +
						  src[x-1][y+1]
			              ) / 5;			
		}
		
		//update non-edge pixels
		//	=> 8 neighbors
		else {
			newTemp    = (src[x+1][y] +
						  src[x-1][y] +
						  src[x+1][y+1] +
						  src[x+1][y-1] + 
						  src[x][y+1] +
						  src[x][y-1] +
						  src[x-1][y-1] + 
						  src[x-1][y+1]
				          ) / 8;
			
		}
		
		dest[x][y] = src[x][y] - ((src[x][y] - newTemp) * tempConst[0]* tempConst[1]* tempConst[2]);
	}
}