import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		
		ArrayList<Stimulus> stimuli = new ArrayList<Stimulus>();
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		vehicles.add(new Vehicle(200,400,Math.toRadians(-90),Def.widthCar, Def.heightCar, 8.0, stimuli, Def.defaultL, Def.defaultR));
		
		stimuli.add(new Stimulus(1.0, 200,40));
				
		Window window = new Window(vehicles, stimuli);
		window.start();
	}
}
