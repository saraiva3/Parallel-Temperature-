


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class SimulationPanel extends JPanel {
	//the simulation engine for the Jacoby relaxation
	private TempGridUI ui;
	private static SimulationPanel simul;

	public SimulationPanel(TempGridUI ui) {
		super();
		this.ui = ui;
		this.setBackground(Color.WHITE);
		
	}
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//System.out.println("called");
		if (ui.grid != null) {
			for (int i = 0; i < ui.grid.gridA.length; i++) {
				for (int j = 0; j < ui.grid.gridA[i].length; j++) {
				//System.out.println(ui.grid.gridA[i][j] + " -> " + new Double(ui.grid.gridA[i][j]));
					int color = new Double(ui.grid.gridA[i][j]).intValue();
					double a = new Double(ui.grid.gridA[i][j]);
				
					if (color >= 255) color = 255;
					if (color <= 0) color = 1;
					if(a == ui.initTempp){
					//	System.out.println("hahahahah");
						g.setColor(new Color(10, 10, 1));
					}else{
						
						g.setColor(new Color(255, 43, 0));
					}

					
					
					g.drawRect(i,j, 1, 1);
				}
			}
		}
	}
}