import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ArrayDeque61B<T> implements Deque61B<T>, Iterable<T> {
    T[] arr;
    int size;
    int nfront;
    int nback;
    public ArrayDeque61B() {
        arr = (T[]) new Object[8];
        size = 0;
        nfront = 7;
        nback = 0;

    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == arr.length) {
            resizerup();
        }
        arr[nfront] = x;
        nfront--;
        if (nfront < 0) {
            nfront = arr.length - 1;
        }
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == arr.length) {
            resizerup();
        }
        arr[nback] = x;
        nback++;
        if (nback > arr.length - 1) {
            nback = 0;
        }
        size++;
    }
    public void resizerup() {
        T[] newarr = (T[]) new Object[arr.length * 2];
        for (int i = 0; i < size; i++) {
            newarr[i] = arr[(nfront + 1 + i) % arr.length];
        }
        arr = newarr;
        nfront = arr.length - 1;
        nback = size;
    }
    public void resizerdown() {
        T[] newarr = (T[]) new Object[arr.length / 2];
        for (int i = 0; i < size; i++) {
            newarr[i] = arr[(nfront + 1 + i) % arr.length];
        }
        arr = newarr;
        nfront = arr.length - 1;
        nback = 0;
    }
    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> a = new ArrayList<>();
        if (size == 0) {
            return a;
        }
        for (int i = 0; i < size; i++) {
            a.add(get(i));
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
        if (isEmpty()) {
            return null;
        }
        nfront = (nfront + 1) % arr.length;
        T temp = arr[nfront];
        arr[nfront] = null;
        size--;
        if (size > 8 && size == arr.length / 2) {
            resizerdown();
        }
        return temp;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nback--;
        if (nback < 0) {
            nback = arr.length - 1;
        }
        T temp = arr[nback];
        arr[nback] = null;
        size--;
        if (size > 8 && size == arr.length / 2) {
            resizerdown();
        }
        return temp;
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
        return arr[(nfront + index + 1) % arr.length];
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    private class ADIterator implements Iterator<T> {
        private int pos;
        private int iterated;
        public ADIterator() {
            pos = (nfront + 1) % arr.length;
            iterated = 0;
        }

        public boolean hasNext() {
            if (size == 0) {
                return false;
            }
            return iterated < size;
        }

        public T next() {
            iterated++;
            T curr = arr[pos];
            pos = (pos + 1) % arr.length;
            return curr;
        }
    }

    public Iterator<T> iterator() {
        return new ADIterator();
    }
    public boolean equals(Object obj) {
        if (!(obj instanceof Deque61B<?> c)) {
            return false;
        }
        if (size != c.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!(get(i).equals(c.get(i)))) {
                return false;
            }
        }
        return true;
    }
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        String ans = "[";
        for (T a: this) {
            ans += a + ", ";
        }
        return ans.substring(0, ans.length() - 2) + "]";
    }
}
