
package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;


public class HistoryHandler extends NgordnetQueryHandler {

    public static final int YEAR_1400 = 1400;
    public static final int YEAR_1500 = 1500;
    public static final int NUM_1000 = 1000;
    public static final int NUM_500 = 500;
    public static final double NUM_100 = 100.0;
    public static final double NUM_50 = 50.0;
    private NGramMap map;
    public HistoryHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (String word: q.words()) {
            TimeSeries a = map.weightHistory(word, q.startYear(), q.endYear());
            labels.add(word);
            lts.add(a);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
