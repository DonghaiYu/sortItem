package Influence;

public class Tools {
	public static long MIN_TRAVEL_TIME = 180;
	public static long PERIOD = 12;
	public static float USEFUL_RATE = (float) 0.7;
	public static String READ_FILE = "out/influences";
	public static String WRITE_FILE = "out/nk";
	
	public static float sigmoid(double x) {
		return (float) (1 / (1 + Math.exp(-x)));
	}
	
}
