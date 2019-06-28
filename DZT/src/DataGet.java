import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class DataGet {

	int[] x1 = new int[4000];
	int[] x2 = new int[4000];
	int[] x3 = new int[4000];
	int[] x4 = new int[4000];
	int[] x5 = new int[4000];
	int[] x6 = new int[4000];
	double a1, b1, a2, b2, a3, b3, a4, b4, a5, b5, a6, b6;

	BufferedReader br;
	String line;
	String[] mass;


	public DataGet() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cfg", "cfg");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);

		String cfgPath = chooser.getSelectedFile().getAbsolutePath();
		String datPath = cfgPath.substring(0,cfgPath.lastIndexOf('.')).concat(".dat");

		File cfg = new File(cfgPath);
		File dat = new File(datPath);

		try {
			br = new BufferedReader(new FileReader(cfgPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int i = 0;
		try {
			while ((line = br.readLine()) != null) {
				mass = line.split(",");
				switch (i) {
				case 2:	a1 = Double.parseDouble(mass[5]); b1 = Double.parseDouble(mass[6]);	break;
				case 3:	a2 = Double.parseDouble(mass[5]); b2 = Double.parseDouble(mass[6]);	break;
				case 4:	a3 = Double.parseDouble(mass[5]); b3 = Double.parseDouble(mass[6]);	break;
				case 5:	a4 = Double.parseDouble(mass[5]); b4 = Double.parseDouble(mass[6]);	break;
				case 6:	a5 = Double.parseDouble(mass[5]); b5 = Double.parseDouble(mass[6]);	break;
				case 7:	a6 = Double.parseDouble(mass[5]); b6 = Double.parseDouble(mass[6]);	break;
				default: break;
				}	
				i++;
			}
		}
		catch (IOException e) { e.printStackTrace();}

		try {
			br = new BufferedReader(new FileReader(dat));
		} 
		catch (FileNotFoundException e) {	e.printStackTrace(); }

		int j = 0;
		try {
			while ((line = br.readLine()) != null){ 
				mass = line.split(",");
				x1[j] = (int) ((Double.parseDouble(mass[2]) * a1 + b1) * 1000);
				x2[j] = (int) ((Double.parseDouble(mass[3]) * a2 + b2) * 1000);
				x3[j] = (int) ((Double.parseDouble(mass[4]) * a3 + b3) * 1000);
				x4[j] = (int) ((Double.parseDouble(mass[5]) * a4 + b4) * 1000);
				x5[j] = (int) ((Double.parseDouble(mass[6]) * a5 + b5) * 1000);
				x6[j] = (int) ((Double.parseDouble(mass[7]) * a6 + b6) * 1000);
				j++;
			}
		} 
		catch (IOException e) { e.printStackTrace();}
	}
}
