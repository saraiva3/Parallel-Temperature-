

public class Result {
	//store the test results
	private double[][] results;
	
	public Result(int mode, int tempConfigs) {
		results = new double[mode][tempConfigs];
	}
	public int getNumberOfModes() {
		return results.length;
	}
	public int getNumberOfTempConfigs() {
		return results[0].length;
	}
	public synchronized double getSpecificResult(int mode, int tempConfig) {
		return results[mode][tempConfig];
	}
	
	public synchronized void updateResult(int mode, int tempConfig, double result) {
		results[mode][tempConfig] = result;
	}
	
	public synchronized double[][] getResult() {
		return this.results;
	}
	
	public synchronized String toString() {
		StringBuffer st = new StringBuffer();
		
		for (int i = 0; i < results.length; i++) {
			st.append("Mode #" + i + ": \n");
			for (int j = 0; j < results[0].length; j++) {
				st.append("Temperature Constant Configuration: " + j + "; Temp. Convergence: " + results[i][j] + "\n");
			}
		}
		return st.toString();
	}
}