import java.util.ArrayList;

public class Sensor {
	
	ArrayList<Stimulus> stimulae;	
	int radius = Def.diamStimulus/2;
	static double radius_collision = Def.collisionDist;
	
	public Sensor(ArrayList<Stimulus> stimulae){
		this.stimulae = stimulae;
	}
	
	
	private double getApparentIntensity(Position position){
		double intensity=0;
		double d;
		for (Stimulus s : stimulae){
			d = s.getDistance(position);
			//d = d/1.5 ; //experimental
			if ((d <= radius) || (intensity > 1)){
				intensity = 1.0;
				return intensity;
			}
			intensity =  (intensity + s.getPower()/(Math.pow((d-radius), 2)));			
		}
		//debug
//		System.out.println("LightSensor: intensity of light source "+ intensity);
		//--
		return intensity;
	}
	
	public double[] getInputState(Position position){
		double intensity = this.getApparentIntensity(position);
		double[] vector  = new double[]{Math.sqrt(Math.abs(1-intensity)), Math.sqrt(Math.abs(intensity))};
		return vector;
	}
	
	
	public static ArrayList<Vehicle> getNeighbors(ArrayList<Vehicle> others, Vehicle vehicle){
		ArrayList<Vehicle> neighbors  = new ArrayList<Vehicle>();
		for(Vehicle v : others){
			if( vehicle.getPosition().getDistance(v.getPosition()) < radius_collision){
				neighbors.add(v);
			}
		}
		
		return neighbors;
	}
	
	
	
}
