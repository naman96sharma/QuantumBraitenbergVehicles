import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox listR, listL;
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Stimulus> stimuli;
	private JButton b, clear;
	private JLabel  label;
	private double constant;
	private int Lop=Def.defaultL, Rop=Def.defaultR;
	private ArrayList<int[]> path = new ArrayList<>();
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintComponent((Graphics2D) g);
	}
	
	public void paintComponent(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(Stimulus s  : stimuli) {
			g.setColor(Color.yellow);
			g.fillOval((int)s.getPosition().x()-Def.diamStimulus/2, (int)s.getPosition().y()-Def.diamStimulus/2, Def.diamStimulus, Def.diamStimulus);
		}
		for(Vehicle v : vehicles) {
			g.setColor(Color.blue);
			g.fillOval(v.getPosSL().x()-6/2, v.getPosSL().y()-6/2, 6, 6);
			g.setColor(Color.green);
			g.fillOval(v.getPosSR().x()-6/2, v.getPosSR().y()-6/2, 6, 6);
			if(v.collision)
				g.setColor(Color.black);
			else
				g.setColor(Color.pink);
			g.fillOval(v.getPosition().x()-10/2, v.getPosition().y()-10/2, 10, 10);
			
			g.setColor(Color.orange);
			// for(int i=0; i<v.entangled.size(); i++){
			// 	if(v.entangled.get(i).lost()){
			// 		v.entangled.remove(i);
			// 	}
			// 	else {
			// 		g.drawLine(v.getPosition().x(), v.getPosition().y(),v.entangled.get(i).getPosition().x(), v.entangled.get(i).getPosition().y());
			// 	}
			// }
			g.setColor(Color.black);
		}
		
	}
	
	public Panel(ArrayList<Vehicle> cars, ArrayList<Stimulus> stimuli) {
		this.vehicles = cars;
		this.stimuli = stimuli;
		constant = cars.get(0).getConstant();
		listR = new JComboBox(Def.opNames);
		listL = new JComboBox(Def.opNames);
		b = new JButton("OK");
		clear = new JButton("Clear");
		listR.setSelectedIndex(Def.defaultR);
		listL.setSelectedIndex(Def.defaultL);
		add(listL, BorderLayout.PAGE_START);
		add(listR, BorderLayout.PAGE_START);
		add(b,BorderLayout.PAGE_START);
		add(clear, BorderLayout.PAGE_START);
		label = new JLabel("Current setting: x" + (constant/100));
		add(label, BorderLayout.PAGE_START);
		b.addActionListener(this);
		clear.addActionListener(this);
		addMouseListener(this);
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1200,800);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==b){
			Lop = (int)listL.getSelectedIndex();
			Rop = (int)listR.getSelectedIndex();
			constant = Def.constant(Lop);
			label.setText("Current setting: " + constant/100);
		}
		else if(arg0.getSource()==clear){
			vehicles.clear();
			stimuli.clear();
			path.clear();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		if ((arg0.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){ //left click
			stimuli.add(new Stimulus(1.0, x,y));
		}
		
		if ((arg0.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK){//right click
			vehicles.add(new Vehicle(x,y,Math.toRadians(Math.random()*360),Def.widthCar, Def.heightCar, 8.0, stimuli, Lop, Rop));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
