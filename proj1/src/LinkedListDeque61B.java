import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    Node sentinel;
    int size;
    public class Node {
        T value;
        Node before;
        Node after;
        Node(T a, Node b, Node c) {
            value = a;
            before = b;
            after = c;
        }
    }
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.before = sentinel;
        sentinel.after = sentinel;
        size = 0;
    }
    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node a = new Node(x, sentinel, sentinel.after);
        sentinel.after.before = a;
        sentinel.after = a;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node a = new Node(x, sentinel.before, sentinel);
        sentinel.before.after = a;
        sentinel.before = a;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> a = new ArrayList<>();
        Node b = sentinel;
        for (int i = 0; i < size; i++) {
            a.add(b.after.value);
            b = b.after;
        }
        return a;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node a = sentinel.after;
        sentinel.after = a.after;
        sentinel.after.before = sentinel;
        size--;
        return a.value;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node a = sentinel.before;
        sentinel.before = a.before;
        sentinel.before.after = sentinel;
        size--;
        return a.value;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node a = sentinel;
        while (index >= 0) {
            a = a.after;
            index--;
        }
        return a.value;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return passer(sentinel.after, index);
    }
    public T passer(Node a, int i) {
        if (i == 0) {
            return a.value;
        }
        return passer(a.after, i - 1);
    }
}
