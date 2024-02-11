package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static comp1110.ass2.HiddenState.HiddenState;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class D2DU7395484 {

    public static final String[][] MY_VALID_STATES = {
            {"a1a2a3a4a5a6a7a8b1b2b3b4b5b6b7b8c1c2c3c4c5c6c7c8d1d2d3d4d5d6d7d8j1j2j3j4j5j6j7j8m1m2m3m4m5m6m7m8", "A", "B"},
            {"a3a8c2c7d1d3d7d8m1", "Ab3b4c1j1m2m5m8", "Ba6b5b6b7b8c8d2j2j8"},
            {"a4a8c3c4d4d5","Ab3b4b5b6c2c3c4","Bd1d7d8j1j4j6m1m2m4"},
    };


    public static final String[][] MY_NOT_WELLFORMED_HIDDEN = {
            {"a1a2a3a4a5a6a7a8b1b2b1", "B","A"}, // wrong order
            {"a8b9b9c0d3", "Ab3b4c1j1m9m5m8", "Ba6b7b8c8d2j2j8"}, // invalid digit in for player A
            {"a1a3a7a8b2b5b6c2c4c5c7d1d3d4d6d7d8m1", "Ab3b4c1j1m2m5m8", "Ba6b7b8c8d2j2j89"},
            {"", "", "B"}, // missing player ID character
            {"a3a8b5b6c2c7d1d3d7d8m1", "Ab3b4c1j12m5m8", "Ba6b7b8c8d2j2j8"},// missing character in players A
            {"a3a8b5b6c2c71d3d7d8m1", "Ab3b4c1j1m2m5m8", "Ba6b7b8c8d2j2j8"}, // missing character in deck card-substring
            {"a3b4c5","ab2c2d3d4d5d6d7","Ba2b1b2b3b4b5b6"}, //missing player Id
            {"A","B"},

    };



    private String errorPrefix(String[] state) {
        return "HiddenState(" + Arrays.toString(state) + ")";
    }

    private void mytest(String[] in, boolean expected) {
        String errorPrefix = errorPrefix(in);
        boolean out = HiddenState(in);
        assertEquals(expected, out, errorPrefix);
    }

    public void mytrivialFalse() {
        mytest(new String[]{"", "", ""}, false);
        mytest(new String[]{}, false);
    }
    public void mytrivialTrue() {
        mytest(new String[] {"", "A", "B"}, true);
    }

    @Test
    public void testBasicValid() {
        mytrivialFalse();
        for (String[] state : MY_VALID_STATES) {
            mytest(state, true);
        }
    }

    @Test
    public void testNotWellFormed() {
        mytrivialTrue();
        for (String[] state : MY_NOT_WELLFORMED_HIDDEN) {
            mytest(state, false);
        }
    }

}


