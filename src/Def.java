
public  final class Def {
	//Constants of Vehicle
	static int widthCar = 18; //26
	static int heightCar = 50;
	static double RCar = 2.0;	//Radius of the wheel
	static double collisionDist = 50;
	static int defaultL = 21;
	static int defaultR = 21;

	//Constants of Stimulus
	static int diamStimulus = 36;
	
	//Constants of Window
	public static int winLength = 1200;
	public static int winHeight = 800;

	// Constants for Quantum Controller
	public static double n = 2; //no. of inputs
	public static double m = 2; //size of logical alphabet
	private static double F = 0, T = 1;
	private static double[][] F_F = new double[][] {{F, F, F, F}, {F, F, F, F}, {F, F, F, F}, {F, F, F, F}};
	private static double[][] F_NOR = new double[][] {{T, F, F, F}, {F, F, F, F}, {F, F, F, F}, {F, F, F, F}};
	private static double[][] F_BnotimpliesA = new double[][] {{F, F, F, F}, {F, T, F, F}, {F, F, F, F}, {F, F, F, F}};
	private static double[][] F_NOT_A = new double[][] {{T, F, F, F}, {F, T, F, F}, {F, F, F, F}, {F, F, F, F}};
	private static double[][] F_AnotimpliesB = new double[][] {{F, F, F, F}, {F, F, F, F}, {F, F, T, F}, {F, F, F, F}};
	private static double[][] F_NOT_B = new double[][] {{T, F, F, F}, {F, F, F, F}, {F, F, T, F}, {F, F, F, F}};
	private static double[][] F_XOR = new double[][] {{F, F, F, F}, {F, T, F, F}, {F, F, T, F}, {F, F, F, F}};
	private static double[][] F_NAND = new double[][] {{T, F, F, F}, {F, T, F, F}, {F, F, T, F}, {F, F, F, F}};
	private static double[][] F_AND = new double[][] {{F, F, F, F}, {F, F, F, F}, {F, F, F, F}, {F, F, F, T}};
	private static double[][] F_equals = new double[][] {{T, F, F, F}, {F, F, F, F}, {F, F, F, F}, {F, F, F, T}};
	private static double[][] F_B = new double[][] {{F, F, F, F}, {F, T, F, F}, {F, F, F, F}, {F, F, F, T}};
	private static double[][] F_AimpliesB = new double[][] {{T, F, F, F}, {F, T, F, F}, {F, F, F, F}, {F, F, F, T}};
	private static double[][] F_A = new double[][] {{F, F, F, F}, {F, F, F, F}, {F, F, T, F}, {F, F, F, T}};
	private static double[][] F_BimpliesA = new double[][] {{T, F, F, F}, {F, F, F, F}, {F, F, T, F}, {F, F, F, T}};
	private static double[][] F_OR = new double[][] {{F, F, F, F}, {F, T, F, F}, {F, F, T, F}, {F, F, F, T}};
	private static double[][] F_T = new double[][] {{T, F, F, F}, {F, T, F, F}, {F, F, T, F}, {F, F, F, T}};
	private static double[][] F_H = new double[][]{{0.25,-0.25,-0.25,-0.25}, {-0.25,0.75,-0.25,0.25}, {-0.25,-0.25,0.75,0.25}, {-0.25,0.25,0.25,0.25}};//2by2 H tensor 2by2 H
	private static double rt2 = Math.sqrt(2);
//	private static double[][] F_H_A = new double [][]{{1/Math.sqrt(2),0,1/Math.sqrt(2),0},
//													{0,1/Math.sqrt(2),0,1/Math.sqrt(2)},
//													{1/Math.sqrt(2),0,-1/Math.sqrt(2),0},
//													{0,1/Math.sqrt(2),0,-1/Math.sqrt(2)}};
	
	private static double[][] F_H_A_new = new double[][] { {(rt2-1)/(2*rt2), 0, -1/(2*rt2), 0},
														   {0, (rt2-1)/(2*rt2), 0, -1/(2*rt2)},
														   {-1/(2*rt2), 0, (rt2+1)/(2*rt2), 0},
														   {0, -1/(2*rt2), 0, (rt2+1)/(2*rt2)}};
													
//	private static double[][] F_H_B = new double[][]{{1/Math.sqrt(2),1/Math.sqrt(2),0,0},
//													{1/Math.sqrt(2),-1/Math.sqrt(2),0,0},
//													{0,0,1/Math.sqrt(2),1/Math.sqrt(2)},
//													{0,0,1/Math.sqrt(2),-1/Math.sqrt(2)}};
													
	private static double[][] F_H_B_new = new double[][] { {(rt2-1)/(2*rt2), -1/(2*rt2), 0, 0},
		   												   {-1/(2*rt2), (rt2+1)/(2*rt2), 0, 0},
		   												   {0, 0, (rt2+1)/(2*rt2), -1/(2*rt2)},
		   												   {0, 0, -1/(2*rt2), (rt2+1)/(2*rt2)}};
	private static double[][] F_Cnot = new double[][] {{F, F, F, F}, {F, F, F, F}, {F, F, 0.5, -0.5}, {F, F, -0.5, 0.5}};
	private static double[][] F_Swap = new double[][] {{T, F, F, F}, {F, F,  T, F}, {F, T, F, F}, {F, F, F, T}};
	
	private static double[][] Bell_Basis = new double[][]{{1-1/rt2, 0, 0,  -1/rt2},
														  {0, 1-1/rt2, -1/rt2, 0},
														  {0, -1/rt2, 1+1/rt2, 0},
														  {-1/rt2, 0, 0, 1+1/rt2}};
	
	
	public static double[][][] operators = new double[][][] {F_F, F_NOR, F_BnotimpliesA, F_NOT_A, F_AnotimpliesB, F_NOT_B, F_XOR,
		F_NAND, F_AND, F_equals, F_B, F_AimpliesB, F_A, F_BimpliesA, F_OR, F_T, F_H, F_H_A_new, F_H_B_new, F_Cnot, F_Swap, Bell_Basis};

	public static String[] opNames = {"F", "NOR", "B not implies A", "Not A", "A not implies B", "Not B", "XOR", "NAND", "AND",
			"equals", "B", "A implies B", "A", "B implies A", "OR", "T", "H", "H_A", "H_B", "C not", "Swap", "Bell Basis"};

	public static double constant(int Lop){
		switch(Lop) {
		case 17: return 1;
		case 20: return 0.15;
		case 21: return 0.15;
		case 16: return 0.5;
		case 19: return 100;
		default: return 100;
	}
	}
}
