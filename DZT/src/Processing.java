
public class Processing {
	double[] buffer = new double[80];
	double sum;
	int counter;
	
	double ax;
	double ay;
	double kf = Math.sqrt(2)/80;
	double sumx;
	double sumy;

	public Processing() {
		sum = 0;
		counter = 0;
		sumx = 0;
		sumy = 0;
		for (int i = 0; i < 80; i++) {
			buffer[i] = 0;
		}
	}

	public double getRMS(double x) {
		sum = sum + x*x - buffer[counter]*buffer[counter];
		buffer[counter] = x;
		counter++;
		if (counter == 80)
			counter = 0;
		return Math.sqrt(sum/80);
	}

	public void fourier(double x, int harm) {
		sumx = sumx + Math.sin(2*Math.PI*harm*counter/80)*(x - buffer[counter]);
		sumy = sumy + Math.cos(2*Math.PI*harm*counter/80)*(x - buffer[counter]);
		ax = sumx*kf;
		ay = sumy*kf;
		buffer[counter] = x;
		counter++;
		if (counter == 80)
			counter = 0;
	}
	
	public static double getIdif(double Ixvn, double Iyvn, double Ixnn, double Iynn) {
		return Math.sqrt((Ixvn - Ixnn)*(Ixvn - Ixnn) + (Iyvn - Iynn)*(Iyvn - Iynn));
	}
	
	public static double getItorm(double Ixvn, double Iyvn, double Ixnn, double Iynn) {
		return Math.sqrt(Ixnn*Ixnn + Iynn*Iynn);
	}	
}
