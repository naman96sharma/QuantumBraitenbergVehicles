import java.util.ArrayList;

public class Position {
	private int x, y;
	private double theta;
	
	Position (int x, int y, double theta){
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	
	Position (int x, int y) {
		this.x = x;
		this.y = y;
		this.theta = 0;
	}
	
	public Position() {
		
	}

	public void changePos(int x, int y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	
	public void changePos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void changeX(int x){
		this.x = x;
	}
	
	public void changeY(int y){
		this.y = y;
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}
	
	public double theta(){
		return theta;
	}


	public double getDistance(Position position2) {
		
		return (double) Math.sqrt(Math.pow(x - position2.x(), 2)+ Math.pow(y - position2.y(), 2));
	}

}
