


import java.util.concurrent.ForkJoinPool;


//import jsr166y.ForkJoinPool;

public class TempGrid {
	//a pool of fork join worker threads to execute the relaxation
	private static ForkJoinPool pool;
	//the root node of the composition tree
	public final Node root;
	//two grids that are being swapped to and from
	public  double[][] gridA;
	public final double[][] gridB;
	//the temperatures by which it is heated up or cooled down
	protected final double deltaTemp1;
	protected final double deltaTemp2;
	//other stuff
	protected final double[] tempConst;
	protected final int threshold;
	protected final double initTemp;
	

	public TempGrid(int x, int y, double[] tempConst, int threshold, double initTemp, double deltaTemp1, double deltaTemp2) {
		this.gridA = new double[x][y];
		this.gridB = new double[x][y];
		this.deltaTemp1 = deltaTemp1;
		this.deltaTemp2 = deltaTemp2;
		this.initTemp = initTemp;
		this.tempConst = tempConst;
		this.threshold = threshold;
		
		for (int i = 0; i < gridA.length; ++i) {
			for (int j = 0; j < gridA[0].length; ++j) {
				gridA[i][j] = initTemp;
				gridB[i][j] = initTemp;
			}
		}
		root = new Node(gridA, gridB, tempConst, threshold, 0, gridA.length, 0, gridA[0].length);
		pool = new ForkJoinPool();
	}
	

	public TempGrid(double[][] grid, double[] tempConst, int threshold, double initTemp, double deltaTemp1, double deltaTemp2) {
		this.gridA = grid;
		this.gridB = grid;
		this.deltaTemp1 = deltaTemp1;
		this.deltaTemp2 = deltaTemp2;
		this.initTemp = initTemp;
		this.tempConst = tempConst;
		this.threshold = threshold;
		
	
		root = new Node(gridA, gridB, tempConst, threshold, 0, gridA.length, 0, gridA[0].length);
		//pool = new ForkJoinPool();
	}

	public void execute( ) {
		root.heatCorners(deltaTemp1, deltaTemp2);
		
		
			
			pool.invoke(this.root);
			root.heatCorners(deltaTemp1, deltaTemp2);
		
	}
	
	
	
	public static void main(String[] args) {		

		double[] temps = {1, 0.75, 0.5};
//		double[] temps = {1, 0.75, 0.5, 0.05};
		
		TempGrid tg = new TempGrid(3, 9, temps, 3, 20, 1, 1);
		tg.execute();
		
		double[][] array = tg.root.getGrid();
		for(int i = 0; i < array.length; ++i) {
			for (int j = 0; j < array[i].length; ++j) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.println();
		}
		
	
		TempGrid tg2 = new TempGrid(array, temps, 3, 30, 1, 1);
		tg2.execute();
		double[][] array2 = tg2.root.getGrid();
		for(int i = 0; i < array2.length; ++i) {
			for (int j = 0; j < array2[i].length; ++j) {
				System.out.print(array2[i][j] + ", ");
			}
			System.out.println();
		}
		TempGrid tg3 = new TempGrid(array2, temps, 3, 30, 1, 1);
		tg3.execute();
		array2 = tg3.root.getGrid();
		for(int i = 0; i < array2.length; ++i) {
			for (int j = 0; j < array2[i].length; ++j) {
				System.out.print(array2[i][j] + ", ");
			}
			System.out.println();
		}
	
		double average = 0;
		for (int i = 0; i < array2.length; i++) {
			for (int j = 0; j < array2[i].length; j++) {
				average += array2[i][j];
			}
		}
		
		average /= (array2.length * array2[0].length);
		average = average - 20;
		System.out.println(average);
		
		
	}
}