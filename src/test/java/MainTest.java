import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import java.util.Arrays;

public class MainTest {

    @Test
    void testMoveUserUpWithinBounds() {
        Integer[] pos = {1, 1};
        Integer[] result = Main.moveUser("w", pos, 1, 1, 11, 1, 11);
        assertArrayEquals(new Integer[]{1, 2}, result);
    }

    @Test
    void testMoveUserLeftOutOfBounds() {
        Integer[] pos = {1, 1};
        Integer[] result = Main.moveUser("a", pos, 1, 1, 11, 1, 11);
        assertArrayEquals(new Integer[]{1, 1}, result); // No change
    }

    @Test
    void testMoveUserDownWithinBounds() {
        Integer[] pos = {5, 5};
        Integer[] result = Main.moveUser("s", pos, 1, 1, 11, 1, 11);
        assertArrayEquals(new Integer[]{5, 4}, result);
    }

    @Test
    void testMoveUserRightOutOfBounds() {
        Integer[] pos = {10, 5};
        Integer[] result = Main.moveUser("d", pos, 1, 1, 11, 1, 11);
        assertArrayEquals(new Integer[]{10, 5}, result);
    }

    @Test
    void testMoveUserInvalidInput() {
        Integer[] pos = {5, 5};
        Integer[] result = Main.moveUser("x", pos, 1, 1, 11, 1, 11);
        assertArrayEquals(new Integer[]{5, 5}, result);
    }

    @Test
    void testMoveUserWithSpeed2() {
        Integer[] pos = {1, 1};
        Integer[] result = Main.moveUser("w", pos, 2, 1, 11, 1, 11);
        assertArrayEquals(new Integer[]{1, 3}, result);
    }

    @Test
    void testCheckBoundsInside() {
        assertTrue(Main.checkBounds(5, 5, 1, 11, 1, 11));
    }

    @Test
    void testCheckBoundsXMax() {
        assertFalse(Main.checkBounds(11, 5, 1, 11, 1, 11)); 
    }

    @Test
    void testCheckBoundsYMin() {
        assertFalse(Main.checkBounds(5, 0, 1, 11, 1, 11)); 
    }

    @Test
    void testCheckBoundsXMin() {
        assertFalse(Main.checkBounds(0, 5, 1, 11, 1, 11));
    }

}

