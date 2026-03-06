package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    RyanGraph ryan;
    NGramMap rtan;
    public HyponymsHandler(String wordy, String yeary, String synsety, String hyponymy) {
        In synset = new In(synsety);
        In hyponym = new In(hyponymy);
        ryan = new RyanGraph();
        rtan = new NGramMap(wordy, yeary);
        while (!synset.isEmpty()) {
            String p = synset.readLine();
            String[] p1 = p.split(",");
            HashSet<String> p2 = new HashSet<>(Arrays.asList(p1[1].split(" ")));
            ryan.addRoot(Integer.parseInt(p1[0]), ryan.new RyanNode(p2, new HashSet<>()));
            for (String a: p2) {
                ryan.associate(a, Integer.parseInt(p1[0]));
            }
        }
        while (!hyponym.isEmpty()) {
            String p = hyponym.readLine();
            String[] p1 = p.split(",");
            for (int i = 1; i < p1.length; i++) {
                ryan.pointTo(ryan.roots.get(Integer.parseInt(p1[0])), ryan.roots.get(Integer.parseInt(p1[i])));
            }
        }

    }
    public TreeSet<String> getHyponyms(String word) {
        TreeSet<String> a = new TreeSet<>();
        HashSet<Integer> b = ryan.syns.get(word);
        if (b == null) {
            return a;
        }
        for (Integer c: b) {
            a.addAll(hypohelp(ryan.roots.get(c)));
        }
        return a;
    }
    public TreeSet<String> hypohelp(RyanGraph.RyanNode a) {
        Deque<RyanGraph.RyanNode> q = new ArrayDeque<>();
        TreeSet<String> ans = new TreeSet<>(a.getWords());
        Set<RyanGraph.RyanNode> visited = new HashSet<>();

        q.add(a);
        while (!q.isEmpty()) {
            RyanGraph.RyanNode cur = q.removeFirst();
            if (!visited.add(cur)) {
                continue;
            }
            ans.addAll(cur.getWords());
            for (RyanGraph.RyanNode ruan: cur.getPointing()) {
                if (!visited.contains(ruan)) {
                    q.addLast(ruan);
                }
            }
        }
        return new TreeSet<>(ans);
    }
    public TreeSet<String> hypofinal(List<String> a) {
        TreeSet<String> ans = new TreeSet<>(getHyponyms(a.get(0)));
        for (int i = 0; i < a.size(); i++) {
            ans.retainAll(getHyponyms(a.get(i)));
        }
        return ans;
    }
    public TreeSet<String> hypofinal(List<String> a, int k, int start, int end) {
        TreeSet<String> ans = new TreeSet<>(getHyponyms(a.get(0)));
        for (int i = 0; i < a.size(); i++) {
            ans.retainAll(getHyponyms(a.get(i)));
        }
        TreeMap<Double, TreeSet<String>> ansy = new TreeMap<>();
        for (String wordy: ans) {
            TreeMap<Integer, Double> b = rtan.countHistory(wordy, start, end);
            double county = 0.0;
            for (Integer key: b.keySet()) {
                county += b.get(key);
            }
            if (county != 0.0) {
                if (!ansy.containsKey(county)) {
                    ansy.put(county, new TreeSet<>());
                    ansy.get(county).add(wordy);
                } else {
                    ansy.get(county).add(wordy);
                }
            }
        }
        ansy.remove(0.0);
        TreeSet<String> ansreal = new TreeSet<>();
        while (ansreal.size() < k && !ansy.isEmpty()) {
            Map.Entry<Double, TreeSet<String>> curr = ansy.pollLastEntry();
            if (curr != null) {
                while (ansreal.size() < k && !curr.getValue().isEmpty()) {
                    ansreal.add(curr.getValue().pollFirst());
                }
            }
        }
        return ansreal;
    }
    @Override
    public String handle(NgordnetQuery q) {
        if (q.k() == 0) {
            return hypofinal(q.words()).toString();
        }
        return hypofinal(q.words(), q.k(), q.startYear(), q.endYear()).toString();
    }
}
