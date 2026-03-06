import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jh61b.utils.Reflection;
import static com.google.common.truth.Truth.assertWithMessage;
import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void curr() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<Integer>();
        for (int i = 16; i >= 0; i--) {
            a.addFirst(i);
        }
        assertThat(a.toList()).containsExactly(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16).inOrder();
        for (int i = 16; i >= 0; i--) {
            a.removeLast();
        }
        assertThat(a.size()).isEqualTo(0);
        assertThat(a.toList()).isEmpty();
        ArrayDeque61B<Integer> b = new ArrayDeque61B<Integer>();
        for (int i = 0; i < 17; i++) {
            b.addLast(i);
        }
        assertThat(b.toList()).containsExactly(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16).inOrder();
        assertThat(b.size()).isEqualTo(17);
        assertThat(b.get(-1)).isNull();
        assertThat(b.get(20)).isNull();
        assertThat(b.isEmpty()).isFalse();
        for (int i = 16; i >= 0; i--) {
            b.removeFirst();
        }
        assertThat(b.toList()).isEmpty();
        assertThat(b.isEmpty()).isTrue();
        b.removeFirst();
        assertThat(b.size()).isEqualTo(0);

    }
    @Test
    public void nexterTester() {
        ArrayDeque61B<Integer> b = new ArrayDeque61B<Integer>();
        b.addLast(1);
        assertThat(b.toList()).containsExactly(1).inOrder();
        ArrayDeque61B<Integer> a = new ArrayDeque61B<Integer>();
        a.addFirst(1);
        assertThat(a.toList()).containsExactly(1).inOrder();
        a.addFirst(0);
        assertThat(a.toList()).containsExactly(0,1).inOrder();
        a.addLast(2);
        assertThat(a.toList()).containsExactly(0,1,2).inOrder();
        a.removeFirst();
        assertThat(a.toList()).containsExactly(1,2).inOrder();
        a.removeFirst();
        a.removeFirst();
        assertThat(a.toList()).containsExactly().inOrder();
        a.addLast(1);
        a.removeLast();
        assertThat(a.toList()).containsExactly().inOrder();
        a.addFirst(1);
        assertThat(a.toList()).containsExactly(1).inOrder();

    }
}
