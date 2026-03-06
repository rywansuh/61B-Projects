package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;
    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word: words) {
            response += word + ": {";
            for (int i = startYear; i <= endYear; i++) {
                String temp = "" + map.weightHistory(word, i, i);
                temp = temp.substring(1, temp.length() - 1) + ", ";
                if (temp.length() > 2) {
                    response += temp;
                }
            }
            response = response.substring(0, response.length() - 2) + "}" + "\n";
        }
        return response;
    }
}
