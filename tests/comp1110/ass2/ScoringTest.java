package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoringTest {

    @Test
    public void testScoring() {
        Assertions.assertEquals(0, Solution.Scoring("")); // no path
        Assertions.assertEquals(3, Solution.Scoring("a2a3a5")); // same species and less than 4
        Assertions.assertEquals(5, Solution.Scoring("a2b3b4c5a7")); // basic path
        Assertions.assertEquals(8, Solution.Scoring("j2j3j4j5")); // same species
        Assertions.assertEquals(6, Solution.Scoring("m1a3b4c5m7")); // begin with 1
        Assertions.assertEquals(6, Solution.Scoring("a2b4c7a8")); // end with 8
        Assertions.assertEquals(13, Solution.Scoring("a1a3a5a7a8")); // same species, begin with 1 and end with 8
    }

}
