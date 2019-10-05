import java.util.ArrayList;

public class Vehicle{
	private Position position; //x and y
	private Sensor SL;
	private Sensor SR;
	private int width, height;
	private int Lop, Rop; // agressive 10 12, love 3 5
	private double[][] FL, FR;
	private int d; //distance between center of the wheel and center of the car
	private double R; //wheel radius
	private double[] speed;
	private double [] inputSL;
	private double [] inputSR;
	private double [] totalInput;
	private double meanValueL, meanValueR, wL, wR;
	private double cteR=1, cteL = 1;// 100 for angular wheel speeds, 1 for hadamard
	private Position posSL,  posSR;
	public ArrayList<Vehicle>  others, entangled = new ArrayList<>();
	public Boolean collision = false;
	private int clusterID=-1;
	
	public Vehicle(int x, int y, double theta, int width, int height, double R, ArrayList<Stimulus> s, int Lop, int Rop){
		speed = new double[2];
		inputSL = new double[2];
		inputSR = new double[2];
		totalInput = new double[2];
		posSL = new Position();
		posSR = new Position();
		FL = Def.operators[Lop];
		FR = Def.operators[Rop];
		this.Lop = Lop;
		this.Rop = Rop;
		cteL = Def.constant(Lop);
		cteR = Def.constant(Rop);
		
		position = new Position(x, y, theta);
		speed[0] = 0.0;
		speed[1] = 0.0;	
		SL = new Sensor(s);
		SR = new Sensor(s);
		this.width = width;
		this.height = height;
		d = width/2;
		this.R = R;
		
		calculSensorsPos();
	}
	
	public double[] getVelocity(){
		return speed;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public void setClusterID(int id){
		clusterID = id;
	}
	
	public void run(){
		
		inputSL = SL.getInputState(posSL);
		inputSR = SR.getInputState(posSR);
		
		totalInput = kroneckerProduct_xy(inputSL, inputSR);
		
		//Check if collisions
		// ArrayList<Vehicle> neighbors = Sensor.getNeighbors(others, this);
		// for(Vehicle v: neighbors){
		// 	if(!entangled.contains(v)){
		// 		collision =  true;
		// 		FL = add(mult_A_a(FL, (entangled.size() + 1.0)/(entangled.size() + 2.0)), mult_A_a(v.FL,1.0/(entangled.size() + 2.0))); //normalization
		// 		FR = add(mult_A_a(FR, (entangled.size() + 1.0)/(entangled.size() + 2.0)), mult_A_a(v.FR,1.0/(entangled.size() + 2.0)));
		// 		entangled.add(v);
		// 	}
		// }  
		
		
		//Method 1: Fuzzy-like, we compute the mean value of each input state.
		 meanValueL = dotProduct_xy(totalInput, multiply_Ax(FL, totalInput)); //considered as linear velocity
		 meanValueR = dotProduct_xy(totalInput, multiply_Ax(FR, totalInput));
		
		//I think the problem is here, we put "arbitrary" values for wheels 
		wL = cteL*meanValueL*R; // wheel angular speed. See if we have to put a minus here
		wR = cteR*meanValueR*R;//see if we have to put a minus here
		
		if(wL > 10/R || wL <-10/R)
			wL = Math.signum(wL)*10/R;
		if(wR > 10/R || wR <-10/R)
			wR = Math.signum(wR)*10/R;
		
		speed[0] = (R*(wL+wR)*Math.cos(position.theta()+Math.PI/2.0))/2.0;//speed limit of 10
		speed[1] = (R*(wL+wR)*Math.sin(position.theta()+Math.PI/2.0))/2.0; //  not switched sin and cos since cos(x-90) = sin(x) and minus
		
//		if(speed[0] > 10 || speed[0] < -10)
//			speed[0] = Math.signum(speed[0])*10;
//		if(speed[1] > 10 || speed[1] < -10)
//			speed[1] = Math.signum(speed[1])*10;
		
		changePosition();	
		calculSensorsPos();
	}
	
	
	
	private double[][] getFL() {
		// TODO Auto-generated method stub
		return null;
	}

	private void changePosition(){
		double thetaNew = (position.theta() + (R*(wR - wL)/(2*d))%(2*Math.PI))%(2*Math.PI);

	//continuous Canvas

		int xNew = (position.x() + ((int)Math.round(speed[0])))%Def.winLength;
		int yNew = (position.y() + ((int)Math.round(speed[1])))%Def.winHeight;
		
		position.changePos(xNew, yNew, thetaNew);
		
		if(position.x() < 0)
			position.changeX(position.x()+Def.winLength);
		if(position.y() < 0)
			position.changeY(position.y()+Def.winHeight);
		
		//bounceWall
//		if ((position[0]  > Def.winLength) || (position[0] < 0) ){
//			position[0] = -position[0];
//			speed[0] = -speed[0];
//			theta = Math.atan2(speed[0], speed[1]);
//		}
//		if ((position[1] > Def.winHeight) || (position[1] < 0) ){
//			position[1] = -position[1];
//			speed[1] = -speed[1];
//			theta = Math.atan2(speed[0], speed[1]);
//		}
//		
	}
	
	
	
	private double[] multiply_Ax(double [][] matrix, double[] vector ){
	    int rows = matrix.length;
	    int columns = matrix[0].length;

	    double[] result = new double[rows];

	    for (int row = 0; row < rows; row++) {
	        double sum = 0;
	        for (int column = 0; column < columns; column++) {
	            sum += matrix[row][column]
	                    * vector[column];
	        }
	        result[row] = sum;
	    }
	    return result;
	}
	
	public double[][] add(double[][] A, double[][] B){
		int rows = A.length;
		int cols = A[0].length;
		double [][] ans = new double[rows][cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				ans[i][j] = A[i][j]+B[i][j];
		return ans;
	}
	
	public double[][] mult_A_a(double[][]A, double a){
		int rows = A.length;
		int cols = A[0].length;
		double [][] ans = new double[rows][cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				ans[i][j] = A[i][j]*a;
		return ans;
	}
	
	private double dotProduct_xy(double[] x, double[] y){
		int rows = x.length;
		double sum = 0;
		for(int row=0; row < rows; row++){
			sum += x[row]*y[row];
		}
		
		return sum;
	}
	
	private double [] kroneckerProduct_xy(double x[], double [] y){
		int rows_x = x.length;
		int rows_y = y.length;
		double [] result = new double[rows_x*rows_y];
			
		for(int i_x = 0; i_x < rows_x; i_x++){
			for(int i_y = 0; i_y < rows_y; i_y++){
				result[i_y + i_x*rows_y] = x[i_x]*y[i_y];
			}
		}
		
		return result;
		
	}
	
	private void calculSensorsPos(){
		// cx, cy - center of square coordinates
		// x, y - coordinates of a corner point of the square
		// theta is the angle of rotation

		//corner Left
		// translate point to origin
		double tempX = ((double)position.x()-(double)(width/2)) - (double)position.x();
		double tempY = ((double)position.y()+(double)height/2) - (double)position.y();

		// now apply rotation
		
		double rotatedX = tempX*Math.cos(position.theta()) - tempY*Math.sin(position.theta());
		double rotatedY = tempX*Math.sin(position.theta()) + tempY*Math.cos(position.theta());

		// translate back
		int SLxNew =(int) Math.round(rotatedX) + position.x();
		int SLyNew= (int)Math.round(rotatedY) + position.y();
		
		posSL.changePos(SLxNew, SLyNew);
		
		//cornerRight
		 tempX = ((double)position.x()+(double)(width/2)) - (double)position.x();
		 tempY = ((double)position.y()+(double)height/2) - (double)position.y();
		 
		 rotatedX = tempX*Math.cos(position.theta()) - tempY*Math.sin(position.theta());
		 rotatedY = tempX*Math.sin(position.theta()) + tempY*Math.cos(position.theta());
		 
		// translate back
		int SRxNew =(int) Math.round(rotatedX) + position.x();
		int SRyNew= (int)Math.round(rotatedY) + position.y();
		
		posSR.changePos(SRxNew, SRyNew);
		 
	}
	
	public void changeOp(int opL, int opR, double constant) {
		FL = Def.operators[opL];
		FR = Def.operators[opR];
		cteR = Def.constant(opR);
		cteL = Def.constant(opL);
	}

	public Position getPosSL(){
		return posSL;
	}
	
	public Position getPosSR(){
		return posSR;
	}
	
	public double getConstant() {
		return cteL;
	}
	
	public void knowEverybody(ArrayList<Vehicle> others){
		this.others = others;
	}

	public int getClusterID() {
		return clusterID;
	}

	public boolean lost() {
		Boolean lost = false;
		if((Double.isNaN(position.theta())) || (posSL.getDistance(position)>100)){
			lost = true;
		}
		return lost;
	}

}