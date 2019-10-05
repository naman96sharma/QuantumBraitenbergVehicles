
public class Stimulus {
	double power;
	Position position;
	
	public Stimulus(double power, int x, int y){
		this.power = power;
		position = new Position(x, y);
	}
	
	
	public double getPower(){
		return power;
	}
	
	
	public Position getPosition(){
		return position;
	}
	
	public double getDistance(Position position2){
		double d = Double.MAX_VALUE;
		double curry;
		Position[] temp = new Position[5];
		temp[0] = new Position(position.x(), position.y());
		temp[1] = new Position(position.x(), position.y() - Def.winHeight);
		temp[2] = new Position(position.x() - Def.winHeight, position.y());
		temp[3] = new Position(position.x(), position.y() + Def.winHeight);
		temp[4] = new Position(position.x() + Def.winLength, position.y());
		
		for(int i = 0; i<5; i++){
			curry = position2.getDistance(temp[i]);
			if (curry<d){
				d = curry;
			}
		}
		//d = (double) Math.sqrt(Math.pow(position[0] - position2[0], 2)+ Math.pow(position[1] - position2[1], 2));
		return d;
	}	
}
