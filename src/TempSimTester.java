


public class TempSimTester {
	//some testing object
	private TempGrid tgrid;
	//some test modes
	public final int SIZE_64x128 = 0;
	public final int SIZE_128x256 = 1;
	public final int SIZE_256x512 = 2;
	public final int SIZE_512x1024 = 3;
	public final int SIZE_1024x2048 = 4;
	//some simulation variables
	private int iterations =1;
	private int threshold = 6;
	private long initTemp = 125;
	private long deltaTemp1 = 1;
	private long deltaTemp2 = 1000;
	public TempSimTester(int mode, double[] tempConstants) {
		//build and fill test object
		switch (mode) {
		case SIZE_64x128: 

			//Size of the grid55554, threshold, initial temp, S, C
			tgrid = new TempGrid(64,128, tempConstants, threshold, initTemp, deltaTemp1, deltaTemp2);
			break;
		case SIZE_128x256: 
			tgrid = new TempGrid(128, 256, tempConstants, threshold, initTemp, deltaTemp1, deltaTemp2);
			break;
		case SIZE_256x512:
			tgrid = new TempGrid(256, 512, tempConstants, threshold, initTemp, deltaTemp1, deltaTemp2);
			break;
		case SIZE_512x1024:
			tgrid = new TempGrid(512, 1024, tempConstants, threshold, initTemp, deltaTemp1, deltaTemp2);
			break;
		case SIZE_1024x2048:
			tgrid = new TempGrid(1024, 2048, tempConstants, threshold, initTemp, deltaTemp1, deltaTemp2);
			break;
		}
	}
	
	public double test() {
		tgrid.execute();
		
		double[][] grid = tgrid.gridA;
		double average = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				average += grid[i][j];
			}
		}
		
		average /= (grid.length * grid[0].length);
		average = average - initTemp;
		return average;
	}

	public static void main(String[] args) {
		int[] modes = {0, 1, 2, 3};
		double[] t1 = {0, 0, 0};
		double[] t2 = {0.25, 0.5, 0.75};
		double[] t3 = {1, 0.75, 0.5};
		double[] t4 = {0.5, 1, 0.35};
		double[] t5 = {0.75, 0.35, 1}; 	
		double[][] ts = {t1, t2, t3, t4, t5};
		Result results = new Result(modes.length, ts.length);
		TempSimTester tst;
		
		for (int i = 0; i < modes.length; i++) {
			System.out.println("Testing Mode #" + modes[i] + "...");
			for (int j = 0; j < ts.length; j++) {
				System.out.println("   ... with Temperature Constants: " + ts[j][0] + ", "+ ts[j][1] + ", "+ ts[j][2]);
				tst = new TempSimTester(modes[i], ts[j]);
				results.updateResult(i, j, tst.test());
			}
		}
		System.out.println(results.toString());
	}
}