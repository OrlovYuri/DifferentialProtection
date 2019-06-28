public class Protection {
	private static double Id0 = 12000;
	private static double It0 = 10000;
	private static double kt = 0.7;
	private static double Iots = 15000;
	private static double i100block = 40;
	
	public static boolean brakeCharact(double Id, double It) {
		return ((Id >= Id0) && (Id >= It*kt + Id0 - kt*It0));
	}
	
	public static boolean difOts(double Id) {
		return (Id >= Iots);
	}
	
	public static boolean block100Hz(double i50, double i100) {
		return (i100/i50 * 100 >= i100block);
	}
}
