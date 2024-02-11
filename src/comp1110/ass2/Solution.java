package comp1110.ass2;

public class Solution {

    public String getScoringPath() {
        return "j1j2j3j4";
    }

    public static int Scoring(String path) {

        int scores = 0;
        int cardsNum = path.length() / 2;

        // check if there is a path
        if (cardsNum == 0) {
            return scores;
        }

        // basic scores
        scores += cardsNum * 1;

        // additional scores (same species)
        boolean sameSpecies = true;

        String species = path.substring(0,1);
        for (int i = 1; i < cardsNum; i++) {
            if (path.substring(2 * i, 2 * i + 1).equals(species) == false) {
                sameSpecies = false;
            }
        }

        if (cardsNum >= 4 && sameSpecies) {
            scores += cardsNum * 1;
        }

        // additional scores (begin with 1)
        if (path.substring(1,2).equals("1")) {
            scores += 1;
        }

        // additional scores (end with 8)
        if (path.substring((2 * cardsNum) - 1, 2 * cardsNum).equals("8")) {
            scores += 2;
        }

        return scores;
    }

    public String Winner() {
        return "A";
    }

}
