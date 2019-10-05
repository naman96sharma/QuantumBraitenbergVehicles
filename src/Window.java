import java.util.ArrayList;

import javax.swing.JFrame;

public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Panel panel;
	
	private static int winLength = Def.winLength;
	private static int winHeight = Def.winHeight;
	private static ArrayList<Vehicle> vehicles;
	private static ArrayList<Stimulus> stimuli;
	
	public Window(ArrayList<Vehicle> vehicles, ArrayList<Stimulus> stimuli) {
		this.setTitle("Braintenberg Vehicles");
		Window.vehicles = vehicles;
		Window.stimuli = stimuli;
		panel = new Panel(vehicles, stimuli);
		this.setSize(winLength, winHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(panel);
		
	}
	
	public void start() {
		this.pack();
		this.setVisible(true);
		while(true) {
			panel.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {e.printStackTrace();}
			for(int  i=0; i<vehicles.size(); i++){
				ArrayList<Vehicle> others = new ArrayList<>(vehicles);
				others.remove(vehicles.get(i));
				vehicles.get(i).knowEverybody(others);
				if(vehicles.get(i).lost()){
					vehicles.remove(i);
					i--;
				}
			}
			for(int i=0; i<vehicles.size(); i++)
				vehicles.get(i).run();
		}	
	}
}