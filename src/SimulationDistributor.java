


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SimulationDistributor {
    private TempGridUI ui;
	//executes an independent thread for every connection 
	private ExecutorService pool;
	//collects the results of the remote computation
	private Future<double[][]>[] cwResults;
	//a wrapper class for the already reduced f/j tree
	private DistributableNode node;
	//some network handling objects
	private Socket[] connections;
	private ObjectInputStream[] gridReaders;
	private ObjectOutputStream[] nodeWriters;
	//space for a global result
	private double[][] globalResult;
	private TempGrid tg; 
	private int iterations;
	
 class ClientWorker implements Callable<double[][]> {
		//some network handling objects
		private ObjectInputStream in;
		private ObjectOutputStream out;
		//space for a remotely computed result
		private double[][] localResult;
		private DistributableNode myNode;
		

		public ClientWorker(DistributableNode n, ObjectInputStream in, ObjectOutputStream out) {
			this.in = in;
			this.out = out;
			this.myNode = n;
		}
		
	
		public double[][] call() {
			
			try {
				out.writeObject(myNode);
				localResult = ((double[][]) in.readObject());
				return localResult;
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			return localResult;
		}
	}

	public SimulationDistributor(TempGridUI ui,SimulationPanel simul, String[] hosts, int port, TempGrid tg, int iterations, int flag) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		//arrange all DistributabeNodes that go over the network
		
                this.ui = ui;
                this.iterations = iterations/10;
		this.node = new DistributableNode(tg.root, this.iterations, tg.deltaTemp1, tg.deltaTemp2);
		this.tg = tg;
		this.globalResult = new double[tg.root.getGrid().length][tg.root.getGrid()[0].length];
		this.pool = Executors.newFixedThreadPool(hosts.length);
		this.cwResults = new Future[hosts.length];
		connections = new Socket[hosts.length];
		gridReaders = new ObjectInputStream[hosts.length];
		nodeWriters = new ObjectOutputStream[hosts.length];
                
		//for all local iterations...
		for (int j = 0; j < 11; j++) {               
                    
			//for all hosts...
			for (int i = 0; i < hosts.length; i++) {
				//connect to host
				if (connections[i] == null) {
					System.out.println("Connecting to server " + hosts[i] + "...");
					connections[i] = new Socket(hosts[i], port);
					nodeWriters[i] = new ObjectOutputStream(connections[i].getOutputStream());
					gridReaders[i] = new ObjectInputStream(connections[i].getInputStream());
				}
			
				//create worker and submit
				System.out.println("Sending node to " + hosts[i]);
				cwResults[i] = pool.submit(new ClientWorker(node, gridReaders[i], nodeWriters[i]));
                                      //System.out.println(cwResults[i]) ;
			}
			mergeResults(j);
                        	nodeWriters[0].reset();
	
	
                      
                        try{
                        Thread.currentThread().sleep(1000);
                        }
                        catch(Exception e ){
                        
                        }
                       
                        simul.repaint();
		}
		
		
		System.out.println("All local iterations complete. Sending TERM signal.");
		/*for (int i = 0; i < hosts.length; i++) {
			nodeWriters[i].writeObject(null);
			gridReaders[i].close();
			nodeWriters[i].close();
			connections[i].close();
		}
		System.out.println("Streams closed. Connection closed.");
		pool.shutdown();*/
		System.out.println("Service complete.");
	}
	

	private void mergeResults(int count) throws ExecutionException, InterruptedException, IOException {			
		for (int k = 0; k < cwResults.length; k++) {
			double[][] partialResult = cwResults[k].get(); 
			
			for (int i = 0; i < partialResult.length; i++) {
				for (int j = 0; j < partialResult[i].length; j++) {
					globalResult[i][j] = partialResult[i][j];
					
					
				}
			
				
			}
	int h=0;
				int x = 0;

 double[][] tempResult= partialResult;
				for(int z = 0; z < partialResult.length; z++){

				    for(int j = 0; j <=k ; j++){
					h=0;
					int i = z -j;
					tempResult[h][x] = partialResult[i][j];
					h++;
					
				    }
				    x++;
				}
				x=0;
				if(count==4){
				int flag=0;
				    for(int z = partialResult.length-2 ; z >= 0; --z){
					for(int j = 0 ; j<=z ; j++){
					    
					    int i = z-j;
					    
					    if(z==1 ){
						
					      globalResult[partialResult.length - j - 1 ][partialResult.length - i -1] =10;
					    }
					}
					x++;
					
}
				}
				    
		
		}
                
		ui.grid.gridA = globalResult;
		this.tg = new TempGrid(globalResult, tg.tempConst, tg.threshold, tg.initTemp, tg.deltaTemp1, tg.deltaTemp2);
		this.node = new DistributableNode(tg.root, this.iterations, tg.deltaTemp1, tg.deltaTemp2);		
	}
	

	public static void main(String[] args) throws IOException, ClassNotFoundException, ExecutionException, InterruptedException {
		double[] temps = {1, 1, 1,};
		int iterations = 10;
		TempGrid tg = new TempGrid(6, 4, temps, 3, 42, 1, 100);
		String[] hosts = {"localhost"};
		//new SimulationDistributor(hosts, 42023, tg, iterations);
	}
}