package main;

import java.util.*;

import edu.princeton.cs.algs4.In;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    NList whist;
    HashMap<Integer, Long> yhist;
    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        In word = new In(wordHistoryFilename);
        In year = new In(yearHistoryFilename);
        whist = new NList();
        while (!word.isEmpty()) {
            String a = word.readLine();
            String[] a1 = a.split("\t");
            if (whist.contains(a1[0])) {
                List<Number> b = new ArrayList<>();
                b.add(Integer.parseInt(a1[1]));
                b.add(Long.parseLong(a1[2]));
                whist.listy.get(a1[0]).add(b);
            } else {
                List<Number> b = new ArrayList<>();
                b.add(Integer.parseInt(a1[1]));
                b.add(Long.parseLong(a1[2]));
                whist.listy.put(a1[0], new HashSet<>());
                whist.listy.get(a1[0]).add(b);
            }
        }
        yhist = new HashMap<>();
        while (!year.isEmpty()) {
            String p = year.readLine();
            String[] p1 = p.split(",");
            yhist.put(Integer.parseInt(p1[0]), Long.parseLong(p1[1]));
        }
    }
    private class NList {
        HashMap<String, HashSet<List<Number>>> listy;
        NList() {
            listy = new HashMap<>();
        }
        private boolean contains(String s) {
            return listy.keySet().contains(s);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(countHistory(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries a = new TimeSeries();
        if (!whist.listy.keySet().contains(word)) {
            return new TimeSeries();
        }
        Set<List<Number>> now = whist.listy.get(word);
        for (List<Number> b: now) {
            a.put((Integer) (b.get(0)), b.get(1).doubleValue());
        }
        return a;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries a = new TimeSeries();
        for (Integer b: yhist.keySet()) {
            a.put(b, (double) (yhist.get(b)));
        }
        return a;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!whist.listy.keySet().contains(word)) {
            return new TimeSeries();
        }
        TimeSeries a = countHistory(word, startYear, endYear);
        TimeSeries b = totalCountHistory();
        for (Integer k: a.keySet()) {
            a.put(k, a.get(k) / b.get(k));
        }
        return a;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries a = new TimeSeries();
        for (String b: words) {
            TimeSeries c = weightHistory(b, startYear, endYear);
            for (Integer d: c.keySet()) {
                if (a.keySet().contains(d)) {
                    a.put(d, a.get(d) + c.get(d));
                } else {
                    a.put(d, c.get(d));
                }
            }
        }
        return a;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
