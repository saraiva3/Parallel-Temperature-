

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class TempGridUI extends JFrame implements ActionListener {
	public TempGrid grid;	
	public int initTempp;
	private JPanel simContainer;
	public SimulationPanel simulation;
//	private JFrame frame;
	private JTextField widthT;
	private JTextField heightT;
	private JTextField c1T;
	private JTextField c2T;
	private JTextField c3T;
	private JTextField iniTempT;
	private JTextField cT;
	private JTextField sT;
	private JTextField thresholdT;
	private JTextField iteractionsT;
	JButton start ;

	JLabel widthLabel,heightlabel,c1,c2,c3,initTemp,c,s,threshold,iteractions,result,result2;
	
	
	public  TempGridUI() {
		//frame = new JFrame();
		this.setBounds(100, 100, 615, 431);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		 widthLabel = new JLabel("Width");
		 widthLabel.setBounds(10, 11, 46, 14);
		 this.getContentPane().add(widthLabel);
		
		 heightlabel = new JLabel("Height");
		 heightlabel.setBounds(10, 36, 46, 14);
		 this.getContentPane().add(heightlabel);
		
		 c1 = new JLabel("C1");
		 c1.setBounds(10, 61, 46, 14);
		 this.getContentPane().add(c1);
		
		 c2 = new JLabel("C2");
		c2.setBounds(10, 86, 46, 14);
		this.getContentPane().add(c2);
		
		 c3 = new JLabel("C3");
		 c3.setBounds(10, 111, 46, 14);
		 this.getContentPane().add(c3);
		
		 initTemp = new JLabel("Temp init");
		 initTemp.setBounds(10, 136, 78, 14);
		 this.getContentPane().add(initTemp);
		
		 c = new JLabel("C");
		c.setBounds(10, 161, 46, 14);
		this.getContentPane().add(c);
		
		 s = new JLabel("S");
		s.setBounds(10, 186, 46, 14);
		this.getContentPane().add(s);
		
		 threshold = new JLabel("Threshold");
		 threshold.setBounds(10, 213, 78, 14);
		 this.getContentPane().add(threshold);
		
		 iteractions = new JLabel("Iteractions");
		 iteractions.setBounds(10, 238, 78, 14);
		 this.getContentPane().add(iteractions);
		 
		 result = new JLabel("Results");
		 result.setBounds(10, 310, 300, 14);
		 this.getContentPane().add(result);
		 
		 
		 
		 result2 = new JLabel("Results");
		 result2.setBounds(10, 330, 320, 14);
		 this.getContentPane().add(result2);
		
		widthT = new JTextField();
		widthT.setBounds(66, 8, 86, 20);
		widthT.setText("100");
		this.getContentPane().add(widthT);
		widthT.setColumns(10);
		
		heightT = new JTextField();
		heightT.setBounds(66, 33, 86, 20);
		heightT.setText("100");
		this.getContentPane().add(heightT);
		heightT.setColumns(10);
		
		c1T = new JTextField();
		c1T.setBounds(66, 58, 86, 20);
		c1T.setText("1");
		this.getContentPane().add(c1T);
		c1T.setColumns(10);
		
		c2T = new JTextField();
		c2T.setBounds(66, 83, 86, 20);
		c2T.setText("1");
		this.getContentPane().add(c2T);
		c2T.setColumns(10);
		
		c3T = new JTextField();
		c3T.setBounds(66, 108, 86, 20);
		c3T.setText("1");
		this.getContentPane().add(c3T);
		c3T.setColumns(10);
		
		iniTempT = new JTextField();
		iniTempT.setBounds(75, 133, 86, 20);
		iniTempT.setText("40");
		this.getContentPane().add(iniTempT);
		iniTempT.setColumns(10);
		
		cT = new JTextField();
		cT.setBounds(66, 158, 86, 20);
		cT.setText("1");
		this.getContentPane().add(cT);
		cT.setColumns(10);
		
		sT = new JTextField();
		sT.setBounds(66, 183, 86, 20);
		sT.setText("10");
		this.getContentPane().add(sT);
		sT.setColumns(10);
		
		thresholdT = new JTextField();
		thresholdT.setBounds(75, 210, 86, 20);
		thresholdT.setText("6");
		this.getContentPane().add(thresholdT);
		thresholdT.setColumns(10);
		
		iteractionsT = new JTextField();
		iteractionsT.setBounds(75, 235, 86, 20);
		iteractionsT.setText("20");
		this.getContentPane().add(iteractionsT);
		iteractionsT.setColumns(10);
		
		JPanel panel = new JPanel(new GridLayout());
		panel.setBounds(305, 11, 284, 359);
		final JTextField  hostLabel;
		hostLabel = new JTextField("Height");
		hostLabel.setBounds(52, 310, 46, 14);
                this.getContentPane().add(hostLabel);
		this.getContentPane().add(heightlabel);
		JButton client;
		client = new JButton("Client");
		client.setBounds(52, 325, 222, 23);
		this.getContentPane().add(client);
                final TempGridUI ui = this;
		client.addActionListener(
				  new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	double tConst1 = Double.parseDouble(c1T.getText());
			    		double tConst2 = Double.parseDouble(c2T.getText());
			    		double tConst3 = Double.parseDouble(c3T.getText());
			    		
			    		final double[] temps = {tConst1, tConst2, tConst3};
			    		final int width = Integer.parseInt(widthT.getText());
			    		final int height = Integer.parseInt(heightT.getText());
			    		final int threshold = Integer.parseInt(thresholdT.getText());
			    		final double initTemp = Double.parseDouble(iniTempT.getText()) ;
			    		initTempp = (int)initTemp;
			    		final double dTemp1 = Double.parseDouble(cT.getText()) ;
			    		final double dTemp2 = Double.parseDouble(sT.getText()) ;	   
			    		final int interactions = Integer.parseInt(iteractionsT.getText());
			    		grid = new TempGrid(width, height, temps, threshold, initTemp, dTemp1, dTemp2);
			    		StringTokenizer hostTokens = new StringTokenizer(hostLabel.getText(), ";");
			    		final String[] hosts = new String[hostTokens.countTokens()];
			    		int index = 0;
			    		while (hostTokens.hasMoreTokens()) {
			    			hosts[index++] = hostTokens.nextToken().trim();
			    		}
			    		
			    					    		
			    		

			    			Thread animationThread = new Thread(new Runnable() {	    			                                       
			    				
                                        int flag =0; 
                                               @Override
                                                public void run() {
                                                     try {
                                                   	new SimulationDistributor(ui,simulation,hosts, 42023, grid, interactions,flag);
			    				} catch (Exception e) {
			    					e.printStackTrace();
			    		 		}
			    				//simulation.repaint();
			    				start.setEnabled(true);
                                                       // }
                                                        System.out.println("ACABO");
                                                }
                                                }
			    		);
			    		animationThread.start();
			    		start.setEnabled(true);
				    }
				  }
				);
		this.getContentPane().add(heightlabel);
		panel.setBorder(BorderFactory.createTitledBorder("Simulation"));
		simContainer = panel;
		simulation = new SimulationPanel(this);
		simContainer.add(simulation);
		simContainer.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		this.getContentPane().add(panel);
		JButton x;
		x = new JButton("Server"); 
		x.setBounds(52,294,222,23);
                x.addActionListener(
		  new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							new SimulationServer(42023);
						} catch (Exception e) {
							e.printStackTrace();
						}
						start.setEnabled(true);
					}
				});
		    }
		  }
		);
		this.getContentPane().add(x);
		
		 start = new JButton("Start Iteractions");
		 start.setBounds(52, 274, 222, 23);
		 this.getContentPane().add(start);
		start.addActionListener(this);
		this.setTitle("Paralele Mesh App");
		this.setResizable(false);
		this.getContentPane().
		setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
	}
	
 	
	 public void actionPerformed(ActionEvent e) {
		 start.setEnabled(false);
			//invoke local concurrent simulation
	    	
	    		double tConst1 = Double.parseDouble(c1T.getText());
	    		double tConst2 = Double.parseDouble(c2T.getText());
	    		double tConst3 = Double.parseDouble(c3T.getText());
	    		
	    		final double[] temps = {tConst1, tConst2, tConst3};
	    		final int width = Integer.parseInt(widthT.getText());
	    		final int height = Integer.parseInt(heightT.getText());
	    		final int threshold = Integer.parseInt(thresholdT.getText());
	    		final double initTemp = Double.parseDouble(iniTempT.getText()) ;
	    		initTempp = (int)initTemp;
	    		final double dTemp1 = Double.parseDouble(cT.getText()) ;
	    		final double dTemp2 = Double.parseDouble(sT.getText()) ;	   
	    		final int interactions = Integer.parseInt(iteractionsT.getText());
	    		this.grid = new TempGrid(width, height, temps, threshold, initTemp, dTemp1, dTemp2);    		       
	    			        grid.execute();
	    			        simulation.repaint();
	    			        	Thread animationThread = new Thread(new Runnable() {
	    				            public void run() {
	    				            	 double [][] array2 =  grid.root.getGrid(); 
	    				            	  for(int i =0; i < interactions-1;i++){

	    			    			         	result.setText("Processing.");
	    			    			         	result2.setText("");
		    				            	    array2 = grid.root.getGrid(); 		    				                    
		    				                    try {
		    				                    	Thread.sleep(500);
		    				                    }catch (Exception ex) {
		    				                    	
		    				                    }

		    		    			         	result.setText("Processing.......");
		    		    			         	result2.setText("");
		    				                    simulation.repaint();		    				                
		    				                	grid = new TempGrid(array2, temps, threshold, initTemp, dTemp1, dTemp2);
		    		    	    				grid.execute();
		    		    	    				array2 = grid.root.getGrid();

		    		    			         	result.setText("Processing............");
		    		    			         	result2.setText("");
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
	    			    					String converged;
	    			    					if(average < 1){
	    			    						converged = "Yes";
	    			    					}
	    			    					else{
	    			    						converged = "No";
	    			    					}

	    				            	  result.setText("Finished: Final average:"+(long)average);
	    				            	  result2.setText("Converged?(If average<1)"+converged);
	    			    			        	  
	    				            }
	    				        });
	    			        	animationThread.start();
	    			        	double [][] array2 =  grid.root.getGrid(); 
	    			      
	    			        	
	    			            
	    			 
	    			        start.setEnabled(true);
	    			    	
	    	
	    	
	    	
	    	
	    }   
		public static void main(String[] args) {
			new TempGridUI();
		}
}
