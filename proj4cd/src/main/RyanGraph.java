package main;
import java.util.HashSet;
import java.util.HashMap;

public class RyanGraph {
    HashMap<Integer, RyanNode> roots;
    HashMap<String, HashSet<Integer>> syns;
    public RyanGraph() {
        roots = new HashMap<>();
        syns = new HashMap<>();
    }
    public class RyanNode {
        private HashSet<String> words;
        private HashSet<RyanNode> pointing;
        public RyanNode(HashSet<String> a, HashSet<RyanNode> b) {
            words = a;
            pointing = b;
        }
        public HashSet<RyanNode> getPointing() {
            return pointing;
        }
        public HashSet<String> getWords() {
            return words;
        }
    }
    public void associate(String word, int idy) {
        if (syns.containsKey(word)) {
            syns.get(word).add(idy);
        } else {
            syns.put(word, new HashSet<>());
            syns.get(word).add(idy);
        }
    }
    public void addRoot(int id, RyanNode a) {
        roots.put(id, a);
    }
    public void pointTo(RyanNode a, RyanNode b) {
        a.pointing.add(b);
    }
}
