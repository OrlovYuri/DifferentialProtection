import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TripDrawing {
	private XYSeries difTokA = new XYSeries("Диф. ток A",false);
	private XYSeries tormTokA = new XYSeries("Торм. ток A",false);
	private XYSeries difTokB = new XYSeries("Диф. ток B",false);
	private XYSeries tormTokB = new XYSeries("Торм. ток B",false);
	private XYSeries difTokC = new XYSeries("Диф. ток C",false);
	private XYSeries tormTokC = new XYSeries("Торм. ток C",false);
	private XYSeries blockSeries = new XYSeries("Блок. 100 Гц",false);
	private XYSeries tripSeries = new XYSeries("Срабатывание",false);
	private double samples = 20;
	private int samplesBuffer = (int) (80/samples);
	private int sum = 0;

	@SuppressWarnings("deprecation")
	public TripDrawing() {
		JFrame frame = new JFrame();
		JFreeChart chart = createCombinedChart();
		ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
		panel.setPreferredSize(new java.awt.Dimension(500, 270));
		frame.getContentPane().add(new ChartPanel(chart)); 
		frame.setSize(800,800); 
		frame.setContentPane(panel);
		panel.setLayout(null);
		frame.show(); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JFreeChart createCombinedChart() {

		XYSeriesCollection dataA = new XYSeriesCollection();
		XYSeriesCollection dataB = new XYSeriesCollection();
		XYSeriesCollection dataC = new XYSeriesCollection();
		XYSeriesCollection blockData = new XYSeriesCollection(); 
		XYSeriesCollection tripData = new XYSeriesCollection(); 
		dataA.addSeries(difTokA); dataA.addSeries(tormTokA);
		dataB.addSeries(difTokB); dataB.addSeries(tormTokB);
		dataC.addSeries(difTokC); dataC.addSeries(tormTokC);
		blockData.addSeries(blockSeries);
		tripData.addSeries(tripSeries);

		XYLineAndShapeRenderer rendererA = new XYLineAndShapeRenderer();
		rendererA.setSeriesPaint(0, Color.blue); rendererA.setSeriesPaint(1, Color.red);
		rendererA.setSeriesShapesVisible(0, false); rendererA.setSeriesShapesVisible(1, false);

		NumberAxis rangeAxisA = new NumberAxis("Фаза А, А");
		rangeAxisA.setAutoRange(true);
		XYPlot subplotA = new XYPlot(dataA, null, rangeAxisA, rendererA);   

		XYLineAndShapeRenderer rendererB = new XYLineAndShapeRenderer();
		rendererB.setSeriesPaint(0, Color.blue); rendererB.setSeriesPaint(1, Color.red);
		rendererB.setSeriesShapesVisible(0, false); rendererB.setSeriesShapesVisible(1, false);

		NumberAxis rangeAxisB = new NumberAxis("Фаза В, А");
		rangeAxisB.setAutoRange(true);
		XYPlot subplotB = new XYPlot(dataB, null, rangeAxisB, rendererB); 

		XYLineAndShapeRenderer rendererC = new XYLineAndShapeRenderer();
		rendererC.setSeriesPaint(0, Color.blue); rendererC.setSeriesPaint(1, Color.red);
		rendererC.setSeriesShapesVisible(0, false); rendererC.setSeriesShapesVisible(1, false);

		NumberAxis rangeAxisC = new NumberAxis("Фаза С, А");
		rangeAxisC.setAutoRange(true);
		XYPlot subplotC = new XYPlot(dataC, null, rangeAxisC, rendererC); 
		
		XYLineAndShapeRenderer rendererBlock = new XYLineAndShapeRenderer();
		rendererBlock.setSeriesPaint(0, Color.black); rendererBlock.setSeriesShapesVisible(0, false);
		NumberAxis rangeAxisBlock = new NumberAxis("Блокировка");
		XYPlot subplotBlock = new XYPlot(blockData, null, rangeAxisBlock, rendererBlock);

		XYLineAndShapeRenderer rendererTrip = new XYLineAndShapeRenderer();
		rendererTrip.setSeriesPaint(0, Color.black); rendererTrip.setSeriesShapesVisible(0, false);
		NumberAxis rangeAxisTrip = new NumberAxis("Срабатывание");
		XYPlot subplotTrip = new XYPlot(tripData, null, rangeAxisTrip, rendererTrip);

		CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("с"));
		plot.add(subplotA, 5); plot.add(subplotB, 5); plot.add(subplotC, 5);
		plot.add(subplotBlock, 2); plot.add(subplotTrip, 2);
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setDomainCrosshairVisible(true);
		return new JFreeChart("ДЗТ", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	}

	public void setData(double dtA, double ttA, double dtB, double ttB, double dtC, double ttC, boolean block, boolean trip, double time){
		if(sum == samplesBuffer) { 
			sum=0;
			difTokA.add(time, dtA);
			tormTokA.add(time,ttA);
			difTokB.add(time, dtB);
			tormTokB.add(time,ttB);
			difTokC.add(time, dtC);
			tormTokC.add(time,ttC);
			if (block)
				blockSeries.add(time, 1);
			else
				blockSeries.add(time, 0);
			if (trip) 
				tripSeries.add(time, 1);
			else 
				tripSeries.add(time, 0);
		}
		sum++;
	}
}