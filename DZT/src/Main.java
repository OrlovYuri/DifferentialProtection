public class Main {
	static double dtA, ttA, dtB, ttB, dtC, ttC, dtA2, dtB2, dtC2;
	static boolean block, trip;
	public static void main(String[] args) {
		//Создание окна:
		TripDrawing td = new TripDrawing();
		//Создание экземпляров класса Processing:
		Processing prIavn = new Processing(); Processing prIann = new Processing();
		Processing prIbvn = new Processing(); Processing prIbnn = new Processing();
		Processing prIcvn = new Processing(); Processing prIcnn = new Processing();
		Processing prIavn2 = new Processing(); Processing prIann2 = new Processing();
		Processing prIbvn2 = new Processing(); Processing prIbnn2 = new Processing();
		Processing prIcvn2 = new Processing(); Processing prIcnn2 = new Processing();
		//Получение данных токов ВН и НН:
		DataGet dg = new DataGet();
		for (int i = 0; i < 2000; i++) {
			//Расчет ортогональных составляющих токов первой гармоники:
			prIavn.fourier(dg.x1[i], 1); prIann.fourier(dg.x4[i], 1); prIbvn.fourier(dg.x2[i], 1);
			prIbnn.fourier(dg.x5[i], 1); prIcvn.fourier(dg.x3[i], 1); prIcnn.fourier(dg.x6[i], 1);
			//Расчет дифференциальных и тормозных токов первой гармоники:
			dtA = Processing.getIdif(prIavn.ax, prIavn.ay, prIann.ax, prIann.ay);
			ttA = Processing.getItorm(prIavn.ax, prIavn.ay, prIann.ax, prIann.ay);
			dtB = Processing.getIdif(prIbvn.ax, prIbvn.ay, prIbnn.ax, prIbnn.ay);
			ttB = Processing.getItorm(prIbvn.ax, prIbvn.ay, prIbnn.ax, prIbnn.ay);
			dtC = Processing.getIdif(prIcvn.ax, prIcvn.ay, prIcnn.ax, prIcnn.ay);
			ttC = Processing.getItorm(prIcvn.ax, prIcvn.ay, prIcnn.ax, prIcnn.ay);
			//Проверка срабатывания ДТО:
			if (Protection.difOts(dtA) || Protection.difOts(dtB) || Protection.difOts(dtC)) {
				trip = true; block = false;
			}
			else {
				//Расчет ортогональных составляющих токов второй гармоники:
				prIavn2.fourier(dg.x1[i], 2); prIann2.fourier(dg.x4[i], 2); prIbvn2.fourier(dg.x2[i], 2);
				prIbnn2.fourier(dg.x5[i], 2); prIcvn2.fourier(dg.x3[i], 2); prIcnn2.fourier(dg.x6[i], 2);
				//Расчет дифференциальных токов второй гармоники:
				dtA2 = Processing.getIdif(prIavn2.ax, prIavn2.ay, prIann2.ax, prIann2.ay);
				dtB2 = Processing.getIdif(prIbvn2.ax, prIbvn2.ay, prIbnn2.ax, prIbnn2.ay);
				dtC2 = Processing.getIdif(prIcvn2.ax, prIcvn2.ay, prIcnn2.ax, prIcnn2.ay);
				//Проверка срабатывания блокировки по второй гармонике:
				if (Protection.block100Hz(dtA, dtA2) || Protection.block100Hz(dtB, dtB2) || Protection.block100Hz(dtC, dtC2)) {
					block = true; trip = false;
				}
				//Проверка срабатывания ДЗТ по тормозной характеристике:
				else {
					block = false;
					trip = (Protection.brakeCharact(dtA, ttA) || Protection.brakeCharact(dtB, ttB) || Protection.brakeCharact(dtC, ttC));
				}		
			}
			//Визуализация работы защиты:
			td.setData(dtA, ttA, dtB, ttB, dtC, ttC, block, trip, 0.00025*i);		
		}
	}
}