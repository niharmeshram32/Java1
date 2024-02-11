package comp1110.ass2;

import java.util.*;

public class Arboretum {

    /**
     * A hiddenState string array is well-formed if it complies with the following rules:
     *
     * hiddenState[0] - Deck
     *                      A number of 2-character card substrings sorted alphanumerically as defined below
     *                      For example: "a3a8b5b6c2c7d1d3d7d8m1"
     *
     * hiddenState[1] - Player A's hand
     *                      0th character is 'A'
     *                      Followed by 7, 8 or 9 2-character card substrings sorted alphanumerically.
     *                      For example a full hand String might look like: "Ab3b4c1j1m2m5m8"
     *
     * hiddenState[2] - Player B's hand
     *                      0th character is 'B'
     *                      Followed by 7, 8 or 9 2-character _card_ substrings
     *                      For example: "Ba6b7b8c8d2j2j8"
     *
     * Where:
     * "card substring" - A 2-character string that represents a single card.
     *                     0th character is 'a', 'b', 'c', 'd', 'j', or 'm' representing the card species.
     *                     1st character is a sequential digit from '1' to '8', representing the value of the card.
     *                     For example: "d7"
     *
     * "alphanumerical(ly)" - This means that cards are sorted first alphabetically and then numerically.
     *                     For example, "m2" appears before "m5" because 2 < 5
     *                     Whilst "b4" appears before "c1" because alphabetical ordering takes precedence over
     *                     numerical ordering.
     *
     * Exceptions:
     *      - If the deck is empty, hiddenState[0] will be the empty string ""
     *      - If a player's hand is empty, then the corresponding hiddenState[i] will contain only the player's ID.
     *      For example: if player A's hand is empty then hiddenState[1] will be "A" with no other characters.
     *
     * @param hiddenState the hidden state array.
     * @return true if the hiddenState array is well-formed, false if it is not well-formed.
     * TASK 3
     */

    /**
     * Task 3 Authorship details:
     *
     * Author: Nihar Jagdish Meshram
     * UID: u7395484
    */
    public static boolean isHiddenStateWellFormed(String[] hiddenState) {

        // for checking the hiddenState length
        if (hiddenState.length != 3) {
            return false;
        }

        // for checking the hiddenState[0] Deck
        if (hiddenState[0].length() == 0) {
            if (hiddenState[0].equals("") == false) {
                return false;
            }
        } else if (hiddenState[0].length() % 2 != 0) {
            return false;
        } else {
            String cardString = hiddenState[0];

            if ((cardStringChecking(cardString) == false) || (alphanumericalChecking(cardString) == false)) {
                return false;
            }
        }


        // for checking hiddenState[1] player A's hand
        if (hiddenState[1].length() == 0) {
            return false;
        }

        if (hiddenState[1].length() == 1) {
            if (hiddenState[1].equals("A") == false) {
                return false;
            }
        } else {
            if (hiddenState[1].charAt(0) != 'A') {
                return false;
            }

            if ((hiddenState[1].length() - 1) % 2 != 0) {
                return false;
            } else {
                String cardString = hiddenState[1].substring(1, hiddenState[1].length());

                if ((cardStringChecking(cardString) == false) || (alphanumericalChecking(cardString) == false)) {
                    return false;
                }
            }
        }


        // for checking hiddenState[2] player B's hand
        if (hiddenState[2].length() == 0) {
            return false;
        }

        if (hiddenState[2].length() == 1) {
            if (hiddenState[2].equals("B") == false) {
                return false;
            }
        } else {
            if (hiddenState[2].charAt(0) != 'B') {
                return false;
            }

            if ((hiddenState[2].length() - 1) % 2 != 0) {
                return false;
            } else {
                String cardString = hiddenState[2].substring(1, hiddenState[2].length());

                if ((cardStringChecking(cardString) == false) || (alphanumericalChecking(cardString) == false)) {
                    return false;
                }
            }
        }

        return true; //FIXME TASK 3
    }

    // for card string checking
    public static boolean cardStringChecking(String cardString) {

        int num = cardString.length() / 2;

        for (int i = 0; i < num; i++) {

            String curCard = cardString.substring(2 * i, 2 + 2 * i);

            if ("abcdjm".contains(Character.toString(curCard.charAt(0))) == false) {
                return false;
            }

            if (curCard.charAt(1) < '1' || curCard.charAt(1) > '8') {
                return false;
            }
        }

        return true;
    }


    // for alphanumerical checking
    public static boolean alphanumericalChecking(String cardString) {

        int num = cardString.length() / 2;

        for (int i = 0; i < num; i++) {

            String curCard = cardString.substring(2 * i, 2 + 2 * i);
            String preCard = "";

            if (i != 0) {
                preCard = cardString.substring(2 * i - 2, 2 * i);
            }

            if (preCard.equals("") == false) {
                if (preCard.charAt(0) == curCard.charAt(0) && preCard.charAt(1) > curCard.charAt(1)) {
                    return false;
                } else if (preCard.charAt(0) > curCard.charAt(0)) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * A sharedState string array is well-formed if it complies with the following rules:
     *
     * sharedState[0] - a single character ID string, either "A" or "B" representing whose turn it is.
     * sharedState[1] - Player A's arboretum
     *                     0th character is 'A'
     *                     Followed by a number of 8-character _placement_ substrings as defined below that occur in
     *                       the order they were played. Note: these are NOT sorted alphanumerically.
     *                     For example: "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01"
     *
     * sharedState[2] - Player A's discard
     *                    0th character is 'A'
     *                    Followed by a number of 2-character _card_ substrings that occur in the order they were
     *                    played. Note: these are NOT sorted alphanumerically.
     *                    For example: "Aa7c4d6"
     *
     * sharedState[3] - Player B's arboretum
     *                    0th character is 'B'
     *                    Followed by a number of 8-character _placement_ substrings that occur in the order they
     *                    were played. Note: these are NOT sorted alphanumerically.
     *                    For example: "Bj5C00C00j6S01C00j7S02W01j4C00W01m6C00E01m3C00W02j3N01W01"
     *
     * sharedState[4] - Player B's discard
     *                    0th character is 'B'
     *                    Followed by a number of 2-character _card_ substrings that occur in the order they were
     *                    played. Note: these are NOT sorted alphanumerically.
     *                    For example: "Bb2d4c5a1d5"
     *
     * Where: "card substring" and "alphanumerical" ordering are defined above in the javaDoc for
     * isHiddenStateWellFormed and placement substrings are defined as follows:
     *
     * "placement substring" - An 8-character string that represents a card placement in a player's arboretum.
     *                  - 0th and 1st characters are a 2-character card substring
     *                  - 2nd character is 'N' for North, 'S' for South, or 'C' for Centre representing the
     *                    direction of this card relative to the first card played.
     *                  - 3rd and 4th characters are a 2-digit number from "00" to "99" representing the distance
     *                    from the first card played to this card, in the direction of the 2nd character.
     *                  - 5th character is 'E' for East, 'W' for West, or 'C' for Centre representing the
     *                    direction of this card relative to the first card played.
     *                  - 6th and 7th characters are a 2-digit number from "00" to "99" representing the distance
     *                    from the first card played to this card, in the direction of the 3rd character.
     *                  For example: "a1C00C00b3N01C00" says that card "a1" was played first and card "b3" was played
     *                  one square north of the first card (which was "a1").
     *
     * Exceptions:
     * If a player's discard or arboretum are empty, (ie: there are no cards in them), then the corresponding string
     * should contain only the player ID.
     * For example:
     *  - If Player A's arboretum is empty, then sharedState[1] would be "A" with no other characters.
     *  - If Player B's discard is empty, then sharedState[4] would be "B" with no other characters.
     *
     * @param sharedState the shared state array.
     * @return true if the sharedState array is well-formed, false if it is not well-formed.
     * TASK 4
     */

    /**
     * Task 4 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    public static boolean isSharedStateWellFormed(String[] sharedState) {

        // for checking shareState length
        if (sharedState.length != 5) {
            return false;
        }

        // for checking shareState[0] player's ID
        if (sharedState[0] != "A" && sharedState[0] != "B") {
            return false;
        }

        // for checking shareState[1] Player A's arboretum
        if (sharedState[1].length() == 1) {
            if (sharedState[1].charAt(0) != 'A') {
                return false;
            }
        } else {
            if (sharedState[1].charAt(0) != 'A') {
                return false;
            }

            if ((sharedState[1].length() - 1) % 8 != 0) {
                return false;
            } else {
                String cardString = sharedState[1].substring(1, sharedState[1].length());

                if (placementSubstringChecking(cardString) == false) {
                    return false;
                }
            }
        }


        // for checking shareState[2] Player A's discard
        if (sharedState[2].length() == 1) {
            if (sharedState[2].charAt(0) != 'A') {
                return false;
            }
        } else {
            if (sharedState[2].charAt(0) != 'A') {
                return false;
            }

            if ((sharedState[2].length() - 1) % 2 != 0) {
                return false;
            } else {
                String cardString = sharedState[2].substring(1, sharedState[2].length());

                if (cardStringChecking(cardString) == false) {
                    return false;
                }
            }
        }


        // for checking shareState[3] Player B's arboretum
        if (sharedState[3].length() == 1) {
            if (sharedState[3].charAt(0) != 'B') {
                return false;
            }
        } else {
            if (sharedState[3].charAt(0) != 'B') {
                return false;
            }

            if ((sharedState[3].length() - 1) % 8 != 0) {
                return false;
            } else {
                String cardString = sharedState[3].substring(1, sharedState[3].length());

                if (placementSubstringChecking(cardString) == false) {
                    return false;
                }
            }
        }


        // for checking shareState[4] Player B's discard
        if (sharedState[4].length() == 1) {
            if (sharedState[4].charAt(0) != 'B') {
                return false;
            }
        } else {
            if (sharedState[4].charAt(0) != 'B') {
                return false;
            }

            if ((sharedState[4].length() - 1) % 2 != 0) {
                return false;
            } else {
                String cardString = sharedState[4].substring(1, sharedState[4].length());

                if (cardStringChecking(cardString) == false) {
                    return false;
                }
            }
        }

        return true; //FIXME TASK 4
    }

    public static boolean placementSubstringChecking(String cardString) {

        int num = cardString.length() / 8;

        for (int i = 0; i < num; i++) {
            String piece = cardString.substring(8 * i, 8 + 8 * i);

            // 0th character checking
            if ("abcdjm".contains(Character.toString(piece.charAt(0))) == false) {
                return false;
            }

            // 1st character checking
            if (piece.charAt(1) < '1' || piece.charAt(1) > '8') {
                return false;
            }

            // 2nd character checking
            if ("NSC".contains(Character.toString(piece.charAt(2))) == false) {
                return false;
            }

            // 3rd-4th character checking
            if (Integer.parseInt(piece.substring(3, 5)) < 00 || Integer.parseInt(piece.substring(3, 5)) > 99) {
                return false;
            }

            // 5th character checking
            if ("EWC".contains(Character.toString(piece.charAt(5))) == false) {
                return false;
            }

            // 6th-7th character checking
            if (Integer.parseInt(piece.substring(6, 8)) < 00 || Integer.parseInt(piece.substring(6, 8)) > 99) {
                return false;
            }
        }

        return true;
    }

    /**
     * Given a deck string, draw a random card from the deck.
     * You may assume that the deck string is well-formed.
     *
     * @param deck the deck string.
     * @return a random cardString from the deck. If the deck is empty, return the empty string "".
     * TASK 5
     */

    /**
     * Task 5 Authorship details:
     *
     * Author: Yucan Zhu
     * UID: u7266392
     */
    public static String drawFromDeck(String deck) {

        if (deck.equals("")) {
            return "";
        } else {
            int deckCardsNum = deck.length() / 2;

            Random random = new Random();
            int i = random.nextInt(deckCardsNum);

            return deck.substring(2 * i, 2 + 2 * i);
        }

        // FIXME TASK 5
    }

    /**
     * Determine whether this placement is valid for the current player. The "Turn String" determines who is making
     * this placement.
     *
     * A placement is valid if the following conditions are met:
     *
     * - The card is placed adjacent to a card that is already placed, or is placed in the position "C00C00" if this is
     * the first card placed.
     * - The card does not share a location with another card that has been placed by this player.
     * - The card being placed is currently in the hand of this player.
     * - The hand of this player has exactly 9 cards in it.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param placement the placement string of the card to be placed
     * @return false if the placement is valid, false if it is not valid.
     * TASK 7
     */

    /**
     * Task 7 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    public static boolean isPlacementValid(String[][] gameState, String placement) {

        String[] shareState = gameState[0];
        String[] hiddenState = gameState[1];

        // whose turn
        String whoseturn = shareState[0];

        // hand number of player A and B
        int handNumberA = (hiddenState[1].length() - 1) / 2;
        int handNumberB = (hiddenState[2].length() - 1) / 2;

        // arboretum number and discard number of player A and player B
        int arboretumNumberA = (shareState[1].length() - 1) / 8;
        int arboretumNumberB = (shareState[3].length() - 1) / 8;

        String placementCard = placement.substring(0, 2);
        String placementPosition = placement.substring(2, 8);

        // if the player is A
        if (whoseturn.equals("A")) {

            if (arboretumNumberA == 0 && placementPosition.equals("C00C00") == false) {
                return false;
            } else if (arboretumNumberA != 0) {

                // for storing the arboretum card positions
                ArrayList<String> arboretumCardPositions = new ArrayList<String>();

                for (int i = 0; i < arboretumNumberA; i++) {
                    String arboretumCardPosition = shareState[1].substring(3 + 8 * i, 9 + 8 * i);
                    arboretumCardPositions.add(arboretumCardPosition);
                }

                ArrayList<String> adjacentPositions = adjacentPositions(placementPosition);

                if ((arboretumCardPositions.contains(adjacentPositions.get(0)) || arboretumCardPositions.contains(adjacentPositions.get(1)) || arboretumCardPositions.contains(adjacentPositions.get(2)) || arboretumCardPositions.contains(adjacentPositions.get(3))) == false) {
                    return false;
                }

                // for checking whether in a same location
                if (arboretumCardPositions.contains(placementPosition)) {
                    return false;
                }

                // for checking whether in hand
                ArrayList<String> handCards = new ArrayList<String>();

                for (int i = 0; i < handNumberA; i++) {
                    String handCard = hiddenState[1].substring(1 + 2 * i, 3 + 2 * i);
                    handCards.add(handCard);
                }

                if (handCards.contains(placementCard) == false) {
                    return false;
                }

            }

            // for checking the hand cards number whether it is 9
            if (handNumberA != 9) {
                return false;
            }

            // if the player is B
        } else {

            if (arboretumNumberB == 0 && placementPosition.equals("C00C00") == false) {
                return false;
            } else if (arboretumNumberB != 0) {

                // for storing all arboretum card positions
                ArrayList<String> arboretumCardPositions = new ArrayList<String>();

                for (int i = 0; i < arboretumNumberB; i++) {
                    String arboretumCardPosition = shareState[3].substring(3 + 8 * i, 9 + 8 * i);
                    arboretumCardPositions.add(arboretumCardPosition);
                }

                ArrayList<String> adjacentPositions = adjacentPositions(placementPosition);

                if ((arboretumCardPositions.contains(adjacentPositions.get(0)) || arboretumCardPositions.contains(adjacentPositions.get(1)) || arboretumCardPositions.contains(adjacentPositions.get(2)) || arboretumCardPositions.contains(adjacentPositions.get(3))) == false) {
                    return false;
                }

                // for checking whether in a same location
                if (arboretumCardPositions.contains(placementPosition)) {
                    return false;
                }

                // for checking whether in hand
                ArrayList<String> handCards = new ArrayList<String>();

                for (int i = 0; i < handNumberB; i++) {
                    String handCard = hiddenState[2].substring(1 + 2 * i, 3 + 2 * i);
                    handCards.add(handCard);
                }

                if (handCards.contains(placementCard) == false) {
                    return false;
                }

            }

            // for checking the hand cards number whether it is 9
            if (handNumberB != 9) {
                return false;
            }
        }

        return true;
        //FIXME TASK 7
    }

    // for getting adjacent positions.
    public static ArrayList<String> adjacentPositions(String placementPosition) {

        ArrayList<String> adjacentPositions = new ArrayList<String>();

        // for checking the adjacent
        String firstDirSign = placementPosition.substring(0,1);
        String firstDirNum = placementPosition.substring(1,3);
        String secondDirSign = placementPosition.substring(3,4);
        String secondDirNum = placementPosition.substring(4,6);

        String neiborPos1 = "";
        String neiborPos2 = "";
        String neiborPos3 = "";
        String neiborPos4 = "";

        if (firstDirSign.equals("C") == false && firstDirNum.equals("01") == false) {
            String newFirstDirNum1 = String.format("%02d", Integer.parseInt(firstDirNum) + 1);
            String newFirstDirNum2 = String.format("%02d", Integer.parseInt(firstDirNum) - 1);
            neiborPos1 = firstDirSign + newFirstDirNum1 + secondDirSign + secondDirNum;
            neiborPos2 = firstDirSign + newFirstDirNum2 + secondDirSign + secondDirNum;
        } else if (firstDirSign.equals("C") == false && firstDirNum.equals("01")) {
            String newFirstDirNum1 = String.format("%02d", Integer.parseInt(firstDirNum) + 1);
            neiborPos1 = firstDirSign + newFirstDirNum1 + secondDirSign + secondDirNum;
            neiborPos2 = "C00" + secondDirSign + secondDirNum;
        } else if (firstDirSign.equals("C") && firstDirNum.equals("00")) {
            neiborPos1 = "N01" + secondDirSign + secondDirNum;
            neiborPos2 = "S01" + secondDirSign + secondDirNum;
        }

        if (secondDirSign.equals("C") == false && secondDirNum.equals("01") == false) {
            String newSecondDirNum1 = String.format("%02d", Integer.parseInt(secondDirNum) + 1);
            String newSecondDirNum2 = String.format("%02d", Integer.parseInt(secondDirNum) - 1);
            neiborPos3 = firstDirSign + firstDirNum + secondDirSign + newSecondDirNum1;
            neiborPos4 = firstDirSign + firstDirNum + secondDirSign + newSecondDirNum2;
        } else if (secondDirSign.equals("C") == false && secondDirNum.equals("01")) {
            String newSecondDirNum1 = String.format("%02d", Integer.parseInt(secondDirNum) + 1);
            neiborPos3 = firstDirSign + firstDirNum + secondDirSign + newSecondDirNum1;
            neiborPos4 = firstDirSign + firstDirNum + "C00";
        } else if (secondDirSign.equals("C") && secondDirNum.equals("00")) {
            neiborPos3 = firstDirSign + firstDirNum + "W01";
            neiborPos4 = firstDirSign + firstDirNum + "E01";
        }

        adjacentPositions.add(neiborPos1);
        adjacentPositions.add(neiborPos2);
        adjacentPositions.add(neiborPos3);
        adjacentPositions.add(neiborPos4);

        return adjacentPositions;
    }

    /**
     * Determine whether the given gameState is valid.
     * A state is valid if the following conditions are met:
     *
     * - There are exactly 48 cards in the game across the deck and each player's hand, arboretum and discard pile.
     * - There are no duplicates of any cards
     * - Every card in each player's arboretum is adjacent to at least one card played _before_ it.
     * - The number of card's in player B's arboretum is equal to, or one less than the number of cards in player
     * A's arboretum.
     * - Each player may have 0 cards in hand only if all cards are in the deck.
     * - Otherwise, a player has exactly 7 cards in their hand if it is not their turn.
     * - If it is a player's turn, they may have 7, 8, or 9 cards in hard.
     * - The number of cards in a player's discard pile is less than or equal to the number of cards in their arboretum.
     *
     * You may assume that the gameState array is well-formed.
     *
     * @param gameState the game state array
     * @return true if the gameState is valid, false if it is not valid.
     * TASK 8
     */

    /**
     * Task 8 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    public static boolean isStateValid(String[][] gameState) {

        String[] shareState = gameState[0];
        String[] hiddenState = gameState[1];

        // whose turn
        String whoseTurn = shareState[0];

        // arboretum number and discard number of player A
        int arboretumNumberA = (shareState[1].length() - 1) / 8;
        int discardNumberA = (shareState[2].length() - 1) / 2;

        // arboretum number and discard number of player B
        int arboretumNumberB = (shareState[3].length() - 1) / 8;
        int discardNumberB = (shareState[4].length() - 1) / 2;

        // deck number
        int deckNumber = hiddenState[0].length() / 2;

        // hand number of player A and B
        int handNumberA = (hiddenState[1].length() - 1) / 2;
        int handNumberB = (hiddenState[2].length() - 1) / 2;

        // for checking exactly 48 cards
        int totalNumber = deckNumber + handNumberA + handNumberB + arboretumNumberA + arboretumNumberB + discardNumberA + discardNumberB;

        if (totalNumber != 48) {
            return false;
        }

        // for checking no duplicates of any cards
        ArrayList<String> cards = new ArrayList<String>();

        // player A arboretum cards
        for (int i = 0; i < arboretumNumberA; i++) {
            String card = shareState[1].substring(1 + 8 * i, 3 + 8 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // player A discard cards
        for (int i = 0; i < discardNumberA; i++) {
            String card = shareState[2].substring(1 + 2 * i, 3 + 2 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // player B arboretum cards
        for (int i = 0; i < arboretumNumberB; i++) {
            String card = shareState[3].substring(1 + 8 * i, 3 + 8 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // player B discard cards
        for (int i = 0; i < discardNumberB; i++) {
            String card = shareState[4].substring(1 + 2 * i, 3 + 2 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // deck cards
        for (int i = 0; i < deckNumber; i++) {
            String card = hiddenState[0].substring(2 * i, 2 + 2 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // player A hand cards
        for (int i = 0; i < handNumberA; i++) {
            String card = hiddenState[1].substring(1 + 2 * i, 3 + 2 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // player B discard cards
        for (int i = 0; i < handNumberB; i++) {
            String card = hiddenState[2].substring(1 + 2 * i, 3 + 2 * i);

            if (cards.contains(card)) {
                return false;
            } else {
                cards.add(card);
            }
        }

        // check if adjacent in player A arboretum
        ArrayList<String> cardsPosA = new ArrayList<String>();

        for (int i = 0; i < arboretumNumberA; i++) {
            String cardPos = shareState[1].substring(3 + 8 * i, 9 + 8 * i);

            if (cardsPosA.size() == 0) {
                cardsPosA.add(cardPos);
            } else {
                ArrayList<String> adjacentPositions = adjacentPositions(cardPos);

                if ((cardsPosA.contains(adjacentPositions.get(0)) || cardsPosA.contains(adjacentPositions.get(1)) || cardsPosA.contains(adjacentPositions.get(2)) || cardsPosA.contains(adjacentPositions.get(3))) == false) {
                    return false;
                }

                cardsPosA.add(cardPos);
            }
        }

        // check if adjacent in player B arboretum
        ArrayList<String> cardsPosB = new ArrayList<String>();

        for (int i = 0; i < arboretumNumberB; i++) {
            String cardPos = shareState[3].substring(3 + 8 * i, 9 + 8 * i);

            if (cardsPosB.size() == 0) {
                cardsPosB.add(cardPos);
            } else {
                ArrayList<String> adjacentPositions = adjacentPositions(cardPos);

                if ((cardsPosB.contains(adjacentPositions.get(0)) || cardsPosB.contains(adjacentPositions.get(1)) || cardsPosB.contains(adjacentPositions.get(2)) || cardsPosB.contains(adjacentPositions.get(3))) == false) {
                    return false;
                }

                cardsPosB.add(cardPos);
            }
        }

        // checking arboretum cards
        if (arboretumNumberB != arboretumNumberA && arboretumNumberB != arboretumNumberA - 1) {
            return false;
        }

        // checking hand cards
        if (deckNumber == 48) {
            if (handNumberA != 0 || handNumberB != 0) {
                return false;
            }
        } else {
            if (whoseTurn == "A") {
                if (handNumberB != 7 || (handNumberA < 7 && handNumberA > 9)) {
                    return false;
                }
            } else {
                if (handNumberA != 7 || (handNumberB < 7 && handNumberB > 9)) {
                    return false;
                }
            }
        }

        // checking if the discard cards is greater than arboretum cards
        if (discardNumberA > arboretumNumberA) {
            return false;
        }

        if (discardNumberB > arboretumNumberB) {
            return false;
        }

        return true;
        // FIXME TASK 8
    }


    /**
     * Determine whether the given player has the right to score the given species. Note: This does not check whether
     * a viable path exists. You may gain the right to score a species that you do not have a viable scoring path for.
     * See "Gaining the Right to Score" in `README.md`.
     * You may assume that the given gameState array is valid.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array.
     * @param player the player attempting to score.
     * @param species the species that is being scored.
     * @return true if the given player has the right to score this species, false if they do not have the right.
     * TASK 9
     */

    /**
     * Task 9 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    public static boolean canScore(String[][] gameState, char player, char species) {

        String handCardsA = gameState[1][1].substring(1,gameState[1][1].length());
        String handCardsB =  gameState[1][2].substring(1,gameState[1][2].length());

        ArrayList<Integer> cardNumsAndScoresA = CardNumsAndScoresInSpecies(handCardsA, species);
        ArrayList<Integer> cardNumsAndScoresB = CardNumsAndScoresInSpecies(handCardsB, species);

        int scoresA = cardNumsAndScoresA.get(cardNumsAndScoresA.size() - 1);
        cardNumsAndScoresA.remove(cardNumsAndScoresA.size() - 1); // remove the total scores in the end of the list
        ArrayList<Integer> cardNumsA = cardNumsAndScoresA;

        int scoresB = cardNumsAndScoresB.get(cardNumsAndScoresB.size() - 1);
        cardNumsAndScoresB.remove(cardNumsAndScoresB.size() - 1); // remove the total scores in the end of the list
        ArrayList<Integer> cardNumsB = cardNumsAndScoresB;

        // checking the situation that 1 and 8 exist at the same time
        if (cardNumsA.contains(8) && cardNumsB.contains(1)) {
            scoresA -= 8;
        }

        if (cardNumsB.contains(8) && cardNumsA.contains(1)) {
            scoresB -= 8;
        }

        // checking whether the player can score
        if (player == 'A' && scoresA >= scoresB) {
            return true;
        } else if (player == 'B' && scoresB >= scoresA) {
            return true;
        } else {
            return false;
        }

        // FIXME TASK 9
    }

    // get all card numbers and total scores in the species
    public static ArrayList<Integer> CardNumsAndScoresInSpecies(String handCards, char species) {

        ArrayList<Integer> cardNumsAndScores = new ArrayList<Integer>();
        int scores = 0;

        for (int i = 0; i < handCards.length() / 2; i++) {
            if (handCards.charAt(2 * i) == species) {
                int cardNum = Character.getNumericValue(handCards.charAt(2 * i + 1));
                cardNumsAndScores.add(cardNum);
                scores += cardNum;
            }
        }

        cardNumsAndScores.add(scores);

        return cardNumsAndScores;
    }

    /**
     * Find all the valid placements for the given card for the player whose turn it is.
     * A placement is valid if it satisfies the following conditions:
     * 1. The card is horizontally or vertically adjacent to at least one other placed card.
     * 2. The card does not overlap with an already-placed card.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param card the card to play
     * @return a set of valid card placement strings for the current player.
     * TASK 10
     */

    /**
     * Task 10 Authorship details:
     *
     * Author: Yucan Zhu
     * UID: u7266392
     */
    public static Set<String> getAllValidPlacements(String[][] gameState, String card) {

        // whose turn
        String whoseTurn = gameState[0][0];

        if (whoseTurn.equals("A")) {
            String cardStringA = gameState[0][1].substring(1, gameState[0][1].length());
            return getOnePlayerAllValidPlacements(cardStringA, card);
        } else {
            String cardStringB = gameState[0][3].substring(1, gameState[0][3].length());
            return getOnePlayerAllValidPlacements(cardStringB, card);
        }
        //FIXME TASK 10
    }

    // get all valid placement of one player
    public static HashSet<String> getOnePlayerAllValidPlacements(String cardString, String card) {

        int arboretumNumber = cardString.length() / 8;
        HashSet<String> allValidPlacements = new HashSet<String>();

        if (arboretumNumber == 0) {
            allValidPlacements.add(card + "C00C00");
        } else {
            ArrayList<String> arboretumCardsPos = new ArrayList<String>();

            for (int i = 0; i < arboretumNumber; i++) {
                String cardPos = cardString.substring(2 + 8 * i, 8 + 8 * i);
                arboretumCardsPos.add(cardPos);
            }

            for (int i = 0; i < arboretumNumber; i++) {
                String cardPos = cardString.substring(2 + 8 * i, 8 + 8 * i);
                ArrayList<String> adjacentPositions = adjacentPositions(cardPos);

                for (int j = 0; j < adjacentPositions.size(); j++) {
                    if (arboretumCardsPos.contains(adjacentPositions.get(j)) == false) {
                        allValidPlacements.add(card + adjacentPositions.get(j));
                    }
                }
            }
        }

        return allValidPlacements;
    }

    /**
     * Find all viable scoring paths for the given player and the given species if this player has the right to
     * score this species.
     *
     * A "scoring path" is a non-zero number of card substrings in order from starting card to ending card.
     * For example: "a1a3b6c7a8" is a path of length 5 starting at "a1" and ending at "a8".
     *
     * A path is viable if the following conditions are met:
     * 1. The player has the right to score the given species.
     * 2. Each card along the path is orthogonally adjacent to the previous card.
     * 3. Each card has value greater than the previous card.
     * 4. The path begins with the specified species.
     * 5. The path ends with the specified species.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param player the given player
     * @param species the species the path must start and end with.
     * @return a set of all viable scoring paths if this player has the right to score this species, or null if this
     * player does not have the right to score this species. If the player has no viable scoring paths (but has the
     * right to score this species), return the empty Set.
     * TASK 12
     */

    /**
     * Task 12 Authorship details:
     *
     * Author: Nihar Jagdish Meshram
     * UID: u7395484
     */
    public static Set<String> getAllViablePaths(String[][] gameState, char player, char species) {

        if (canScore(gameState, player, species) == false) {
            return null;
        } else {
            if (player == 'A') {
                String cardStringA = gameState[0][1].substring(1, gameState[0][1].length());
                return getOnePlayerAllViablePaths(cardStringA, species);
            } else {
                String cardStringB = gameState[0][3].substring(1, gameState[0][3].length());
                return getOnePlayerAllViablePaths(cardStringB, species);
            }
        }
        // FIXME TASK 12
    }

    // get all viable paths of one player
    public static HashSet<String> getOnePlayerAllViablePaths(String cardString, char species) {

        HashSet<String> allViablePaths = new HashSet<String>();

        ArrayList<String> allCards = new ArrayList<String>();
        ArrayList<String> allPositions = new ArrayList<String>();
        ArrayList<String> speciesCards = new ArrayList<String>();

        // get all cards and their positions
        int num = cardString.length() / 8;

        for (int i = 0; i < num; i++) {
            String piece = cardString.substring(8 * i, 8 * i + 8);
            String card = piece.substring(0, 2);
            String position = piece.substring(2, 8);

            if (card.charAt(0) == species) {
                speciesCards.add(card);
            }

            allCards.add(card);
            allPositions.add(position);
        }

        // get all possible paths in every specie card
        ArrayList<String> allPossiblePaths = new ArrayList<String>();

        for (int i = 0; i < speciesCards.size(); i++) {
            ArrayList<String> allPossiblePathsInOneStartCard = allPossiblePathsInOneStartCard(speciesCards.get(i), allCards, allPositions);
            allPossiblePaths.addAll(allPossiblePathsInOneStartCard);
        }

        allViablePaths = validChecking(allPossiblePaths, species);

        return allViablePaths;
    }

    // get all possible in one specie card
    public static ArrayList<String> allPossiblePathsInOneStartCard(String startCard, ArrayList<String> allCards, ArrayList<String> allPositions) {

        ArrayList<String> allPossiblePathsInOneStartCard = new ArrayList<String>();
        String currentPath = "";

        String currentCard = startCard;
        currentPath += currentCard;

        while (true) {
            ArrayList<String> validCards = validCardsInPath(currentCard, allCards, allPositions);

            // when we can not find an adjacent card which can arrive, just break the finding
            if (validCards.size() == 0) {
                allPossiblePathsInOneStartCard.add(currentPath);
                break;
            }

            // when we just can find one adjacent card which can arrive, this is the only path currently.
            if (validCards.size() == 1) {
                currentCard = validCards.get(0);
                currentPath += currentCard;
            }

            // when we can find more than one adjacent card, we need to discuss every card and use recursion method to find the future path
            if (validCards.size() >= 2) {
                for (int i = 0; i < validCards.size(); i++) {
                    // recursion method
                    ArrayList<String> branch = allPossiblePathsInOneStartCard(validCards.get(i), allCards, allPositions);
                    for (int j = 0; j < branch.size(); j++) {
                        allPossiblePathsInOneStartCard.add(currentPath + branch.get(j));
                    }
                }
                break;
            }
        }

        return allPossiblePathsInOneStartCard;
    }

    // get the valid cards which can be arrived from the current card position
    public static ArrayList<String> validCardsInPath(String currentCard, ArrayList<String> allCards, ArrayList<String> allPositions) {

        ArrayList<String> validCards = new ArrayList<String>();

        int currentCardIndex = allCards.indexOf(currentCard);
        String currentPosition = allPositions.get(currentCardIndex);

        ArrayList<String> adjacentPositions = adjacentPositions(currentPosition);

        for (int i = 0; i < adjacentPositions.size(); i++) {
            if (allPositions.indexOf(adjacentPositions.get(i)) != -1) {
                int adjacentCardIndex = allPositions.indexOf(adjacentPositions.get(i));
                String adjacentCard = allCards.get(adjacentCardIndex);

                if (adjacentCard.charAt(1) > currentCard.charAt(1)) {
                    validCards.add(adjacentCard);
                }
            }
        }

        return validCards;
    }

    // from all possible paths to find the valid path, which can be scored according to the game rules
    public static HashSet<String> validChecking(ArrayList<String> allPossiblePaths, char species) {

        ArrayList<String> allViablePathsList = new ArrayList<String>();
        HashSet<String> allViablePaths = new HashSet<String>();

        for (int i = 0; i < allPossiblePaths.size(); i++) {
            String path = allPossiblePaths.get(i);

            // single card in path can not be considered.
            if (path.length() != 2) {
                allViablePathsList.add(path);
            }
        }

        // get all sub-path which can be scored
        for (int i = 0; i < allViablePathsList.size(); i++) {
            String path = allViablePathsList.get(i);
            ArrayList<Integer> index = new ArrayList<Integer>();

            for (int j = 0; j < path.length(); j++) {
                if (path.charAt(j) == species) {
                    index.add(j);
                }
            }

            for (int j = 0; j < index.size() - 1; j++) {
                for (int m = j + 1; m < index.size(); m++) {
                    String morePath = path.substring(index.get(j), index.get(m) + 2);
                    allViablePaths.add(morePath);
                }
            }
        }

        return allViablePaths;
    }


    /**
     * Find the highest score of the viable paths for the given player and species.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param player the given player
     * @param species the species to score
     * @return the score of the highest scoring viable path for this player and species.
     * If this player does not have the right to score this species, return -1.
     * If this player has the right to score this species but there is no viable scoring path, return 0.
     * TASK 13
     */

    /**
     * Task 13 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    public static int getHighestViablePathScore(String[][] gameState, char player, char species) {

        if (canScore(gameState, player, species) == false) {
            return -1;
        }

        // get all valid paths in this game
        Set<String> allViablePaths = getAllViablePaths(gameState, player, species);

        if (allViablePaths.isEmpty()) {
            return 0;
        }

        int highestScore = Integer.MIN_VALUE;
        ArrayList<String> allViablePathsList = new ArrayList<String>(allViablePaths);

        for (int i = 0; i < allViablePathsList.size(); i++) {
            String path = allViablePathsList.get(i);
            int score = getScore(path);

            // compare and choose the highest
            if (score > highestScore) {
                highestScore = score;
            }
        }

        return highestScore;
        // FIXME TASK 13
    }

    // calculating the path scores
    public static int getScore(String path) {

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

    /**
     * AI Part 1:
     * Decide whether to draw a card from the deck or a discard pile.
     * Note: This method only returns the choice, it does not update the game state.
     * If you wish to draw a card from the deck, return "D".
     * If you wish to draw a card from a discard pile, return the cardstring of the (top) card you wish to draw.
     * You may count the number of cards in a players' hand to determine whether this is their first or final draw
     * for the turn.
     *
     * Note: You may only draw the top card of a players discard pile. ie: The last card that was added to that pile.
     * Note: There must be cards in the deck (or alternatively discard) to draw from the deck (or discard) respectively.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return "D" if you wish to draw from the deck, or the cardstring of the card you wish to draw from a discard
     * pile.
     * TASK 14
     */

    /**
     * Task 14 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    public static String chooseDrawLocation(String[][] gameState) {

        String[] shareState = gameState[0];
        String[] hiddenState = gameState[1];

        String disCardsA = shareState[2];
        String disCardsB = shareState[4];

        String deckCards = hiddenState[0];

        if (deckCards.length() == 0 && disCardsA.length() == 1 && disCardsB.length() != 1) {
            return disCardsB.substring(disCardsB.length() - 2, disCardsB.length());
        } else if (deckCards.length() == 0 && disCardsA.length() != 1 && disCardsB.length() == 1) {
            return disCardsA.substring(disCardsA.length() - 2, disCardsA.length());
        } else if (deckCards.length() == 0 && disCardsA.length() != 1 && disCardsB.length() != 1) {

            ArrayList<String> canBeChosenCards = new ArrayList<String>();

            canBeChosenCards.add(disCardsA.substring(disCardsA.length() - 2, disCardsA.length()));
            canBeChosenCards.add(disCardsB.substring(disCardsB.length() - 2, disCardsB.length()));

            // AIImprovementInDraw is implemented in task 17 for more intelligent draw
            String bestChoice = AIImprovementInDraw(gameState, canBeChosenCards);
            return bestChoice;

        } else if (deckCards.length() != 0 && disCardsA.length() == 1 && disCardsB.length() == 1){
            return "D";
        } else if (deckCards.length() != 0 && disCardsA.length() == 1 && disCardsB.length() != 1) {

            ArrayList<String> canBeChosenCards = new ArrayList<String>();

            canBeChosenCards.add("D");
            canBeChosenCards.add(disCardsB.substring(disCardsB.length() - 2, disCardsB.length()));

            // AIImprovementInDraw is implemented in task 17 for more intelligent draw
            String bestChoice = AIImprovementInDraw(gameState, canBeChosenCards);
            return bestChoice;

        } else if (deckCards.length() != 0 && disCardsA.length() != 1 && disCardsB.length() == 1) {

            ArrayList<String> canBeChosenCards = new ArrayList<String>();

            canBeChosenCards.add("D");
            canBeChosenCards.add(disCardsA.substring(disCardsA.length() - 2, disCardsA.length()));

            // AIImprovementInDraw is implemented in task 17 for more intelligent draw
            String bestChoice = AIImprovementInDraw(gameState, canBeChosenCards);
            return bestChoice;

        } else if (deckCards.length() != 0 && disCardsA.length() != 1 && disCardsB.length() != 1) {

            ArrayList<String> canBeChosenCards = new ArrayList<String>();

            canBeChosenCards.add(disCardsA.substring(disCardsA.length() - 2, disCardsA.length()));
            canBeChosenCards.add(disCardsB.substring(disCardsB.length() - 2, disCardsB.length()));
            canBeChosenCards.add("D");

            // AIImprovementInDraw is implemented in task 17 for more intelligent draw
            String bestChoice = AIImprovementInDraw(gameState, canBeChosenCards);
            return bestChoice;
        }

        return null;
        // FIXME TASK 14
    }


    /**
     * Task 17 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    // Writing for task 17 to choose intelligently the best choice for computer, and has been used in task 14 for smarter drawing cards.
    public static String AIImprovementInDraw(String[][] gameState, ArrayList<String> canBeChosenCards) {

        String bestChoice = "";

        // get all AI hand cards (Player B) and AI arboretum cards
        String AIhandCards = gameState[1][2].substring(1, gameState[1][2].length());
        String AIArboretumCardsAndPositions = gameState[0][3].substring(1, gameState[0][3].length());

        String AIArboretumCards = "";

        for (int i = 0; i < AIArboretumCardsAndPositions.length() / 8; i++) {
            AIArboretumCards += AIArboretumCardsAndPositions.substring(8 * i, 8 * i + 2);
        }

        // remove the deck situation, and prior to consider the cards which are in discard area.
        if (canBeChosenCards.contains("D")) {
            canBeChosenCards.remove("D");
        }

        // if there is only one card which can be chosen, we need to consider the importance of the card.
        // the importance order is '1' > '8' > same specie card > 'D' > normal card
        if (canBeChosenCards.size() == 1) {
            String card = canBeChosenCards.get(0);
            if (AIhandCards.contains(card.substring(0,1)) || AIArboretumCards.contains(card.substring(0,1))) {
                bestChoice = card;
            } else if ("18".contains(card.substring(1,2))) {
                bestChoice = card;
            } else {
                bestChoice = "D";
            }

        // if there are two cards which can be chosen, we need to consider if '1' and '8' existed according to the importance order.
        // when '1' and '8' do not exist, we need to consider which card species we have the most.
        } else if (canBeChosenCards.size() == 2) {
            String card1 = canBeChosenCards.get(0);
            String card2 = canBeChosenCards.get(1);

            if (card1.substring(1,2).equals("1") && card2.substring(1,2).equals("1") == false) {
                bestChoice = card1;
            } else if (card1.substring(1,2).equals("1") == false && card2.substring(1,2).equals("1")) {
                bestChoice = card2;
            } else if (card1.substring(1,2).equals("8") && card2.substring(1,2).equals("8") == false) {
                bestChoice = card1;
            } else if (card1.substring(1,2).equals("8") == false && card2.substring(1,2).equals("8")) {
                bestChoice = card2;
            } else if ((card1.substring(1,2).equals("1") && card2.substring(1,2).equals("1")) || (card1.substring(1,2).equals("8") && card2.substring(1,2).equals("8"))) {
                int countCard1Speice = 0;
                int countCard2Speice = 0;

                for (int i = 0; i < AIhandCards.length() / 2; i++) {
                    if (AIhandCards.substring(2 * i, 2 * i + 1).equals(card1.substring(0, 1))) {
                        countCard1Speice += 1;
                    } else if (AIhandCards.substring(2 * i, 2 * i + 1).equals(card2.substring(0, 1))) {
                        countCard2Speice += 1;
                    }
                }

                for (int i = 0; i < AIArboretumCards.length() / 2; i++) {
                    if (AIArboretumCards.substring(2 * i, 2 * i + 1).equals(card1.substring(0, 1))) {
                        countCard1Speice += 1;
                    } else if (AIArboretumCards.substring(2 * i, 2 * i + 1).equals(card2.substring(0, 1))) {
                        countCard2Speice += 1;
                    }
                }

                if (countCard1Speice > countCard2Speice) {
                    bestChoice = card1;
                } else if (countCard2Speice > countCard1Speice) {
                    bestChoice = card2;
                } else if (countCard1Speice == countCard2Speice) {
                    Random r = new Random();
                    int index = r.nextInt(2);
                    bestChoice = canBeChosenCards.get(index);
                }
            } else {
                int countCard1Speice = 0;
                int countCard2Speice = 0;

                for (int i = 0; i < AIhandCards.length() / 2; i++) {
                    if (AIhandCards.substring(2 * i, 2 * i + 1).equals(card1.substring(0, 1))) {
                        countCard1Speice += 1;
                    } else if (AIhandCards.substring(2 * i, 2 * i + 1).equals(card2.substring(0, 1))) {
                        countCard2Speice += 1;
                    }
                }

                for (int i = 0; i < AIArboretumCards.length() / 2; i++) {
                    if (AIArboretumCards.substring(2 * i, 2 * i + 1).equals(card1.substring(0, 1))) {
                        countCard1Speice += 1;
                    } else if (AIArboretumCards.substring(2 * i, 2 * i + 1).equals(card2.substring(0, 1))) {
                        countCard2Speice += 1;
                    }
                }

                if (countCard1Speice > countCard2Speice) {
                    bestChoice = card1;
                } else if (countCard2Speice > countCard1Speice) {
                    bestChoice = card2;
                } else if (countCard1Speice == countCard2Speice && countCard1Speice != 0){
                    Random r = new Random();
                    int index = r.nextInt(2);
                    bestChoice = canBeChosenCards.get(index);
                } else {
                    bestChoice = "D";
                }
            }
        }

        return bestChoice;
        // FIXME TASK 17
    }


    /**
     * AI Part 2:
     * Generate a moveString array for the player whose turn it is.
     *
     * A moveString array consists of two parts;
     * moveString[0] is the valid card _placement_ string for the card you wish to place.
     * moveString[1] is the cardstring of the card you wish to discard.
     *
     * For example: If I want to play card "a1" in location "N01E02" and discard card "b4" then my moveString[] would
     * be as follows:
     * moveString[0] = "a1N01E02"
     * moveString[1] = "b4"
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return a valid move for this player.
     * TASK 15
     */

    /**
     * Task 15 Authorship details:
     *
     * Author: Yucan Zhu
     * UID: u7266392
     */
    public static String[] generateMove(String[][] gameState) {

        String[] shareState = gameState[0];
        String[] hiddenState = gameState[1];

        String whoseTurn = shareState[0];
        String arboretumCardA = shareState[1];
        String arboretumCardB = shareState[3];

        String handCardsA = hiddenState[1];
        String handCardsB = hiddenState[2];

        if (whoseTurn.equals("A")) {
            return generateOnePlayerMove(arboretumCardA, handCardsA);
        } else if (whoseTurn.equals("B")) {
            return generateOnePlayerMove(arboretumCardB, handCardsB);
        }

        return null;
        // FIXME TASK 15
    }

    // generaate move of one player
    public static String[] generateOnePlayerMove (String arboretumCard, String handCards) {

        String[] moveString = new String[2];

        Random r = new Random();

        int playCardIndex = r.nextInt((handCards.length() - 1) / 2);
        String playCard = handCards.substring(1 + 2 * playCardIndex, 3 + 2 * playCardIndex);

        HashSet<String> allValidPlacement = getOnePlayerAllValidPlacements(arboretumCard.substring(1, arboretumCard.length()), playCard);
        ArrayList<String> allValidPlacementList = new ArrayList<String>(allValidPlacement);

        int playCardPlacementIndex = r.nextInt(allValidPlacementList.size());
        moveString[0] = allValidPlacementList.get(playCardPlacementIndex);

        int discardCardIndex = 0;
        while (true) {
            discardCardIndex = r.nextInt((handCards.length() - 1) / 2);

            if (discardCardIndex != playCardIndex) {
                break;
            }
        }

        moveString[1] = handCards.substring(1 + 2 * discardCardIndex, 3 + 2 * discardCardIndex);

        return moveString;
    }
}
