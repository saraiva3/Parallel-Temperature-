


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;


public class GroupPanel extends JPanel {
	

	public GroupPanel(JLabel jl, JTextField tf) {
		this.setLayout(new FlowLayout(15));
		this.add(jl);
		this.add(tf);
	}
}
