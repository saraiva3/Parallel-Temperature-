


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;



public class SimulationServer {
	private ExecutorService pool;
	//some network handling objects
	private Socket connection;
	private ServerSocket server;
	private ObjectInputStream nodeReader;
	private ObjectOutputStream gridWriter;
	
	private class ServerWorker implements Runnable {
		//needed to invoke the node concurrently
		private ForkJoinPool pool;
		//some network handling objects
		private ObjectOutputStream out;
		private DistributableNode node;
		
		public ServerWorker(DistributableNode n, ObjectOutputStream out) {
			this.out = out;
			this.node = n;
			pool = new ForkJoinPool();
		}
		
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is executing...");
			for (int i = 0; i < 1; i++) {
//				System.out.println("Iteration: "+ i);
				pool.invoke(node.node);
				node.node.heatCorners(node.deltaTemp1, node.deltaTemp2);
			}
			System.out.println(Thread.currentThread().getName() + ": Sending result back to client...");
			try {
				out.writeObject(node.node.getGrid());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ": Result sent.");
		}
	}
	
	public SimulationServer(int port) throws IOException, ClassNotFoundException {
		pool = Executors.newCachedThreadPool();
		System.out.println("Starting Server...");
		server = new ServerSocket(port);
		connection = server.accept();
		System.out.println("Connection established.");
		nodeReader = new ObjectInputStream(connection.getInputStream());
		gridWriter = new ObjectOutputStream(connection.getOutputStream());
		DistributableNode node;
		
		while ((node = ((DistributableNode) nodeReader.readObject())) != null) {
			System.out.println("Node received.");
			Thread t = new Thread(new ServerWorker(node, gridWriter));
			pool.submit(t);
		}
		
		// => shut everything down
		//first, wait for all threads to be done
		pool.shutdown();
		while (!pool.isTerminated()) {
			//do nothing
		}
		System.out.println("Closing streams and connections.");
		//second, close all streams
		this.nodeReader.close();
		this.gridWriter.close();
		//close all sockets
		connection.close();
		server.close();
		System.out.println("Server closed.");
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		new SimulationServer(42023);
	}
}