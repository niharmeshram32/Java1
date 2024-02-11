package comp1110.ass2.gui;

import comp1110.ass2.Arboretum;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Game extends Application {
    /* board layout */
    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();

    private TextField cardPlacementTextField;
    private TextField discardTextField;

    // initialise the game state in the beginning of the game
    public static String[][] initialGameState() {

        String[] shareState = new String[5];
        String[] hiddenState = new String[3];

        String[][] gameState = new String[2][];
        gameState[0] = shareState;
        gameState[1] = hiddenState;

        shareState[0] = "A"; // whose turn
        shareState[1] = "A"; // Player A's arboretum
        shareState[2] = "A"; // Player A's discard
        shareState[3] = "B"; // Player B's arboretum
        shareState[4] = "B"; // Player B's discard

        // Deck before having hand cards
        hiddenState[0] = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 9; j++) {
                hiddenState[0] = hiddenState[0] + "abcdjm".substring(i, i+1) + Integer.toString(j);
            }
        }

        // Player A' hand and Player B's hand
        int len = hiddenState[0].length() / 2;

        Random r = new Random();

        hiddenState[1] = "A";
        hiddenState[2] = "B";

        ArrayList<Integer> allIndex = new ArrayList<Integer>();
        ArrayList<String> allHandCards = new ArrayList<String>();

        for (int i = 0; i < 14; i++) {
            int index = r.nextInt(len);
            while (allIndex.contains(index)) {
                index = r.nextInt(len);
            }
            allHandCards.add(hiddenState[0].substring(2 * index, 2 * index + 2));
            allIndex.add(index);
        }

        List<Integer> allIndexForPlayerA = allIndex.subList(0, 7);
        List<Integer> allIndexForPlayerB = allIndex.subList(7, 14);

        allIndexForPlayerA.sort(Comparator.naturalOrder());
        allIndexForPlayerB.sort(Comparator.naturalOrder());

        for (int i = 0; i < allIndexForPlayerA.size(); i++) {
            String handcard = hiddenState[0].substring(2 * allIndexForPlayerA.get(i), 2 * allIndexForPlayerA.get(i) + 2);
            hiddenState[1] += handcard;
        }

        for (int i = 0; i < allIndexForPlayerB.size(); i++) {
            String handcard = hiddenState[0].substring(2 * allIndexForPlayerB.get(i), 2 * allIndexForPlayerB.get(i) + 2);
            hiddenState[2] += handcard;
        }

        for (int i = 0; i < allHandCards.size(); i++) {
            hiddenState[0] = hiddenState[0].replace(allHandCards.get(i), "");
        }

        return gameState;
    }


    // show all the game board
    public void display(String[][] gameState) {

        root.getChildren().clear();
        root.getChildren().add(controls);

        String[] shareState = gameState[0];
        String[] hiddenState = gameState[1];

        String deckCards = hiddenState[0];

        String ArboretumCardsA = shareState[1].substring(1, shareState[1].length());
        String discardCardsA = shareState[2].substring(1, shareState[2].length());
        String handCardsA = hiddenState[1].substring(1, hiddenState[1].length());

        String ArboretumCardsB = shareState[3].substring(1, shareState[3].length());
        String discardCardsB = shareState[4].substring(1, shareState[4].length());
        String handCardsB = hiddenState[2].substring(1, hiddenState[2].length());

        // arboretum number
        int arboretumNumA = ArboretumCardsA.length() / 8;
        int arboretumNumB = ArboretumCardsB.length() / 8;

        // discard cards number
        int discardNumA = discardCardsA.length() / 2;
        int discardNumB = discardCardsB.length() / 2;

        // hand number
        int handNumA = handCardsA.length() / 2;
        int handNumB = handCardsB.length() / 2;

        // deck cards number
        int deckNum = deckCards.length() / 2;

        // for showing the game
        double cardWidth = 30;
        double cardHeight = 40;

        // players Text
        var playerAText = new Text("Player A");
        playerAText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        playerAText.setFill(Color.BLACK);
        playerAText.setLayoutX(260);
        playerAText.setLayoutY(30);
        root.getChildren().add(playerAText);

        var playerBText = new Text("Player B");
        playerBText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        playerBText.setFill(Color.BLACK);
        playerBText.setLayoutX(860);
        playerBText.setLayoutY(30);
        root.getChildren().add(playerBText);

        // deck show
        var deckText = new Text("Deck");
        deckText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
        deckText.setFill(Color.BLACK);
        deckText.setLayoutX(580);
        deckText.setLayoutY(15);
        root.getChildren().add(deckText);

        if (deckNum >= 1) {
            var deckArea = new Rectangle(cardWidth, cardHeight);
            deckArea.setLayoutX(585);
            deckArea.setLayoutY(20);
            root.getChildren().add(deckArea);
        }

        // discard show for player A
        var discardAText = new Text("Discard A");
        discardAText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
        discardAText.setFill(Color.BLACK);
        discardAText.setLayoutX(15);
        discardAText.setLayoutY(15);
        root.getChildren().add(discardAText);

        if (discardNumA == 1) {
            var discardAreaACard1 = new Rectangle(cardWidth, cardHeight);
            discardAreaACard1.setLayoutX(30);
            discardAreaACard1.setLayoutY(20);
            discardAreaACard1.setOpacity(0.3);
            root.getChildren().add(discardAreaACard1);

            String discardAText1 = discardCardsA.substring(2 * (discardNumA - 1), 2 * discardNumA);

            var discardCardAText1 = new Text(discardAText1);
            discardCardAText1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            discardCardAText1.setFill(Color.BLACK);
            discardCardAText1.setLayoutX(36);
            discardCardAText1.setLayoutY(35);
            root.getChildren().add(discardCardAText1);
        }

        if (discardNumA >= 2) {
            var discardAreaACard1 = new Rectangle(cardWidth, 20);
            discardAreaACard1.setLayoutX(30);
            discardAreaACard1.setLayoutY(20);
            discardAreaACard1.setOpacity(0.3);
            root.getChildren().add(discardAreaACard1);

            var discardAreaACard2 = new Rectangle(cardWidth, cardHeight);
            discardAreaACard2.setLayoutX(30);
            discardAreaACard2.setLayoutY(40);
            discardAreaACard2.setOpacity(0.5);
            root.getChildren().add(discardAreaACard2);

            String discardAText1 = discardCardsA.substring(2 * (discardNumA - 2), 2 * (discardNumA - 1));
            String discardAText2 = discardCardsA.substring(2 * (discardNumA - 1), 2 * discardNumA);

            var discardCardAText1 = new Text(discardAText1);
            discardCardAText1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            discardCardAText1.setFill(Color.BLACK);
            discardCardAText1.setLayoutX(36);
            discardCardAText1.setLayoutY(35);
            root.getChildren().add(discardCardAText1);

            var discardCardAText2 = new Text(discardAText2);
            discardCardAText2.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            discardCardAText2.setFill(Color.BLACK);
            discardCardAText2.setLayoutX(36);
            discardCardAText2.setLayoutY(55);
            root.getChildren().add(discardCardAText2);
        }

        // discard show for player B
        var discardBText = new Text("Discard B");
        discardBText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
        discardBText.setFill(Color.BLACK);
        discardBText.setLayoutX(1125);
        discardBText.setLayoutY(15);
        root.getChildren().add(discardBText);

        if (discardNumB == 1) {
            var discardAreaBCard1 = new Rectangle(cardWidth, cardHeight);
            discardAreaBCard1.setLayoutX(1140);
            discardAreaBCard1.setLayoutY(20);
            discardAreaBCard1.setOpacity(0.3);
            root.getChildren().add(discardAreaBCard1);

            String discardBText1 = discardCardsB.substring(2 * (discardNumB - 1), 2 * discardNumB);

            var discardCardBText1 = new Text(discardBText1);
            discardCardBText1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            discardCardBText1.setFill(Color.BLACK);
            discardCardBText1.setLayoutX(1146);
            discardCardBText1.setLayoutY(35);
            root.getChildren().add(discardCardBText1);
        }

        if (discardNumB >= 2) {
            var discardAreaBCard1 = new Rectangle(cardWidth, 20);
            discardAreaBCard1.setLayoutX(1140);
            discardAreaBCard1.setLayoutY(20);
            discardAreaBCard1.setOpacity(0.3);
            root.getChildren().add(discardAreaBCard1);

            var discardAreaBCard2 = new Rectangle(cardWidth, cardHeight);
            discardAreaBCard2.setLayoutX(1140);
            discardAreaBCard2.setLayoutY(40);
            discardAreaBCard2.setOpacity(0.5);
            root.getChildren().add(discardAreaBCard2);

            String discardBText1 = discardCardsB.substring(2 * (discardNumB - 2), 2 * (discardNumB - 1));
            String discardBText2 = discardCardsB.substring(2 * (discardNumB - 1), 2 * discardNumB);

            var discardCardBText1 = new Text(discardBText1);
            discardCardBText1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            discardCardBText1.setFill(Color.BLACK);
            discardCardBText1.setLayoutX(1146);
            discardCardBText1.setLayoutY(35);
            root.getChildren().add(discardCardBText1);

            var discardCardBText2 = new Text(discardBText2);
            discardCardBText2.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            discardCardBText2.setFill(Color.BLACK);
            discardCardBText2.setLayoutX(1146);
            discardCardBText2.setLayoutY(55);
            root.getChildren().add(discardCardBText2);
        }

        // hand A show
        var handAText = new Text("Hand A");
        handAText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 10));
        handAText.setFill(Color.BLACK);
        handAText.setLayoutX(8);
        handAText.setLayoutY(620);
        root.getChildren().add(handAText);

        for (int i = 0; i < handNumA; i++) {
            var handAreaCardA = new Rectangle(cardWidth, cardHeight);
            handAreaCardA.setLayoutX(50 + (500 / (handNumA - 1) * i));
            handAreaCardA.setLayoutY(600);
            handAreaCardA.setOpacity(0.5);
            root.getChildren().add(handAreaCardA);

            String handCardA = handCardsA.substring(2 * i, 2 * i + 2);

            var handCardAText = new Text(handCardA);
            handCardAText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            handCardAText.setFill(Color.BLACK);
            handCardAText.setLayoutX(50 +(500 / (handNumA - 1) * i) + 6);
            handCardAText.setLayoutY(625);
            root.getChildren().add(handCardAText);
        }

        // hand B show
        var handBText = new Text("Hand B");
        handBText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 10));
        handBText.setFill(Color.BLACK);
        handBText.setLayoutX(608);
        handBText.setLayoutY(620);
        root.getChildren().add(handBText);

        for (int i = 0; i < handNumB; i++) {
            var handAreaCardB = new Rectangle(cardWidth, cardHeight);
            handAreaCardB.setLayoutX(650 + (500 / (handNumB - 1) * i));
            handAreaCardB.setLayoutY(600);
            handAreaCardB.setOpacity(0.5);
            root.getChildren().add(handAreaCardB);

            String handCardB = handCardsB.substring(2 * i, 2 * i + 2);

            var handCardBText = new Text(handCardB);
            handCardBText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
            handCardBText.setFill(Color.BLACK);
            handCardBText.setLayoutX(650 +(500 / (handNumB - 1) * i) + 6);
            handCardBText.setLayoutY(625);
            root.getChildren().add(handCardBText);
        }

        // middle seperation line
        var line = new Line(600, 100, 600, 640);
        root.getChildren().add(line);

        // arboretum show for player A
        for (int i = 0; i < arboretumNumA; i++) {

            String piece = ArboretumCardsA.substring(8 * i, 8 * i + 8);
            String ArboretumCard = piece.substring(0, 2);
            String position = piece.substring(2, 8);

            if (position.equals("C00C00")) {
                var rect = new Rectangle(cardWidth, cardHeight);
                rect.setLayoutX(290);
                rect.setLayoutY(290);
                rect.setOpacity(0.5);
                root.getChildren().add(rect);

                var ArboretumCardText = new Text(ArboretumCard);
                ArboretumCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
                ArboretumCardText.setFill(Color.DARKRED);
                ArboretumCardText.setLayoutX(296);
                ArboretumCardText.setLayoutY(315);
                root.getChildren().add(ArboretumCardText);
            } else {
                String firstDir = position.substring(0,1);
                String firstDis = position.substring(1,3);
                String secondDir = position.substring(3,4);
                String secondDis = position.substring(4,6);

                double shiftDisX = 0.0;
                double shiftDisY = 0.0;

                if (firstDir.equals("N")) {
                    shiftDisY =  - Integer.parseInt(firstDis) * cardHeight;
                } else if (firstDir.equals("S")) {
                    shiftDisY = Integer.parseInt(firstDis) * cardHeight;
                }

                if (secondDir.equals("W")) {
                    shiftDisX =  - Integer.parseInt(secondDis) * cardWidth;
                } else if (secondDir.equals("E")) {
                    shiftDisX = Integer.parseInt(secondDis) * cardWidth;
                }

                var rect = new Rectangle(cardWidth, cardHeight);
                rect.setLayoutX(290 + shiftDisX);
                rect.setLayoutY(290 + shiftDisY);
                rect.setOpacity(0.5);
                root.getChildren().add(rect);

                var ArboretumCardText = new Text(ArboretumCard);
                ArboretumCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
                ArboretumCardText.setFill(Color.BLACK);
                ArboretumCardText.setLayoutX(296 + shiftDisX);
                ArboretumCardText.setLayoutY(315 + shiftDisY);
                root.getChildren().add(ArboretumCardText);
            }
        }

        // arboretum show for player B
        for (int i = 0; i < arboretumNumB; i++) {

            String piece = ArboretumCardsB.substring(8 * i, 8 * i + 8);
            String ArboretumCard = piece.substring(0, 2);
            String position = piece.substring(2, 8);

            if (position.equals("C00C00")) {
                var rect = new Rectangle(cardWidth, cardHeight);
                rect.setLayoutX(910);
                rect.setLayoutY(290);
                rect.setOpacity(0.5);
                root.getChildren().add(rect);

                var ArboretumCardText = new Text(ArboretumCard);
                ArboretumCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
                ArboretumCardText.setFill(Color.DARKRED);
                ArboretumCardText.setLayoutX(916);
                ArboretumCardText.setLayoutY(315);
                root.getChildren().add(ArboretumCardText);
            } else {
                String firstDir = position.substring(0,1);
                String firstDis = position.substring(1,3);
                String secondDir = position.substring(3,4);
                String secondDis = position.substring(4,6);

                double shiftDisX = 0.0;
                double shiftDisY = 0.0;

                if (firstDir.equals("N")) {
                    shiftDisY =  - Integer.parseInt(firstDis) * cardHeight;
                } else if (firstDir.equals("S")) {
                    shiftDisY = Integer.parseInt(firstDis) * cardHeight;
                }

                if (secondDir.equals("W")) {
                    shiftDisX =  - Integer.parseInt(secondDis) * cardWidth;
                } else if (secondDir.equals("E")) {
                    shiftDisX = Integer.parseInt(secondDis) * cardWidth;
                }

                var rect = new Rectangle(cardWidth, cardHeight);
                rect.setLayoutX(910 + shiftDisX);
                rect.setLayoutY(290 + shiftDisY);
                rect.setOpacity(0.5);
                root.getChildren().add(rect);

                var ArboretumCardText = new Text(ArboretumCard);
                ArboretumCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 15));
                ArboretumCardText.setFill(Color.BLACK);
                ArboretumCardText.setLayoutX(916 + shiftDisX);
                ArboretumCardText.setLayoutY(315 + shiftDisY);
                root.getChildren().add(ArboretumCardText);
            }
        }

    }

    /**
     * Task 11 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */

    /**
     * Task 16 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    // normal game (play with computer)
    public void makeControl(String[][] gameState) {

        String whoseTurn = gameState[0][0];

        Label cardPlacement = new Label("Card Placement");
        cardPlacementTextField = new TextField();
        cardPlacementTextField.setPrefWidth(100);

        Label discardCard = new Label("Discard");
        discardTextField = new TextField();
        discardTextField.setPrefWidth(100);

        Button drawFromDeck = new Button("Draw From Deck");
        Button drawFromDCA = new Button("Draw From Discard A");
        Button drawFromDCB = new Button("Draw From Discard B");

        Button place = new Button("Place");
        Button discard = new Button("Discard");

        Button end = new Button("End my turn");

        // draw a card from Deck
        drawFromDeck.setOnAction(e -> {
            if (gameState[1][1].length() >= 9 * 2 + 1) {
                System.out.println("You cannot draw a card!\nPlease place one card and discard one card!\n----------------------");
            } else if (gameState[1][1].length() < 7 * 2 + 1) {
                System.out.println("You need to draw a card!!\nPlease place one card and discard one card!\n----------------------");
            } else {
                String drawCard = Arboretum.drawFromDeck(gameState[1][0]);
                if (drawCard.equals("")) {
                    System.out.println("game over");
                } else {
                    ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[1][0], gameState[1][1], drawCard, "A");

                    gameState[1][0] = afterchanging.get(0);
                    gameState[1][1] = afterchanging.get(1);
                }
                display(gameState);
            }
        });

        // draw a card from Discard Area of player A
        drawFromDCA.setOnAction(e -> {
            if (gameState[1][1].length() >= 9 * 2 + 1) {
                System.out.println("You cannot draw a card!\nPlease place one card and discard one card!\n----------------------");
            } else if (gameState[1][1].length() < 7 * 2 + 1) {
                System.out.println("You need to draw a card!!\nPlease place one card and discard one card!\n----------------------");
            } else {
                if (gameState[0][2].length() == 1) {
                    System.out.println("There is no cards in your discard area!\n----------------------");
                } else {
                    int cardsNum = (gameState[0][2].length() - 1) / 2;
                    String drawCard = gameState[0][2].substring(2 * cardsNum - 1, 2 * cardsNum + 1);

                    ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[0][2], gameState[1][1], drawCard, "A");

                    gameState[0][2] = afterchanging.get(0);
                    gameState[1][1] = afterchanging.get(1);
                }
                display(gameState);
            }

        });

        // draw a card from Discard Area of player B
        drawFromDCB.setOnAction(e -> {
            if (gameState[1][1].length() >= 9 * 2 + 1) {
                System.out.println("You cannot draw a card!\nPlease place one card and discard one card!\n----------------------");
            } else if (gameState[1][1].length() < 7 * 2 + 1) {
                System.out.println("You need to draw a card!!\nPlease place one card and discard one card!\n----------------------");
            } else {
                if (gameState[0][4].length() == 1) {
                    System.out.println("There is no cards in Player B's discard area!\n----------------------");
                } else {
                    int cardsNum = (gameState[0][4].length() - 1) / 2;
                    String drawCard = gameState[0][4].substring(2 * cardsNum - 1, 2 * cardsNum + 1);

                    ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[0][4], gameState[1][1], drawCard, "A");

                    gameState[0][4] = afterchanging.get(0);
                    gameState[1][1] = afterchanging.get(1);
                }
                display(gameState);
            }
        });

        // place a card in arboretum
        place.setOnAction(e -> {
            if (gameState[1][1].length() == 7 * 2 + 1) {
                System.out.println("You have placed a card! Or you forget to draw!\n----------------------");
            } else {
                String inforOfPlacement = cardPlacementTextField.getText();
                if (gameState[0][1].length() > gameState[0][3].length()) {
                    System.out.println("You have placed a card!\n----------------------");
                } else {
                    if (Arboretum.isPlacementValid(gameState, inforOfPlacement)) {
                        String card = inforOfPlacement.substring(0, 2);
                        gameState[0][1] += inforOfPlacement;
                        gameState[1][1] = gameState[1][1].replace(card, "");
                    } else {
                        System.out.println("Please choose a valid position!\n----------------------");
                    }
                    display(gameState);
                }
            }
        });

        // discard a card to own discard area
        discard.setOnAction(e -> {
            if (gameState[1][1].length() == 7 * 2 + 1) {
                System.out.println("You have discard a card!\n----------------------");
            } else {
                String inforOfDiscard = discardTextField.getText();
                gameState[0][2] += inforOfDiscard;
                gameState[1][1] = gameState[1][1].replace(inforOfDiscard, "");
                display(gameState);
            }
        });

        // end button
        // check if the game is over
        // help computer to complete draw, place and discard opreation.
        end.setOnAction(e -> {
            if (gameState[1][0].length() == 0) {
                String winner = getWinner(gameState);
                System.out.println("The game is over!\nThe game winner is : " + winner + ".\n----------------------");
            } else {
                if (gameState[1][1].length() != 7 * 2 + 1) {
                    System.out.println("You need to discard your cards to 7!\n----------------------");
                } else if (gameState[0][3].length() == gameState[0][1].length()) {
                    System.out.println("It is your turn!\n----------------------");
                } else {
                    // change into player B
                    gameState[0][0] = "B";

                    // draw a card (two times) (intelligent drawing)
                    for (int i = 0; i < 2; i++) {
                        String robotDrawLocation = Arboretum.chooseDrawLocation(gameState);

                        if (robotDrawLocation.equals("D")) {
                            String drawCard = Arboretum.drawFromDeck(gameState[1][0]);
                            ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[1][0], gameState[1][2], drawCard, "B");
                            gameState[1][0] = afterchanging.get(0);
                            gameState[1][2] = afterchanging.get(1);
                        } else {
                            if (robotDrawLocation.equals(gameState[0][2].substring(gameState[0][2].length() - 2, gameState[0][2].length()))) {
                                String drawCard = robotDrawLocation;
                                ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[0][2], gameState[1][2], drawCard, "B");
                                gameState[0][2] = afterchanging.get(0);
                                gameState[1][2] = afterchanging.get(1);
                            } else {
                                String drawCard = robotDrawLocation;
                                ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[0][4], gameState[1][2], drawCard, "B");
                                gameState[0][4] = afterchanging.get(0);
                                gameState[1][2] = afterchanging.get(1);
                            }
                        }
                    }

                    // place and discard
                    String[] moveString = Arboretum.generateMove(gameState);

                    String robotPlacement = moveString[0];
                    String robotDiscard = moveString[1];

                    gameState[0][3] += robotPlacement;
                    gameState[1][2] = gameState[1][2].replace(robotPlacement.substring(0, 2), "");

                    gameState[0][4] += robotDiscard;
                    gameState[1][2] = gameState[1][2].replace(robotDiscard, "");

                    display(gameState);

                    // change into player A
                    gameState[0][0] = "A";
                }
            }
        });

        // put all buttons and text boxes in the bottom of the window
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(drawFromDeck, drawFromDCA, drawFromDCB);
        hbox1.setLayoutX(50);
        hbox1.setLayoutY(658);
        hbox1.setSpacing(10);

        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(cardPlacement, cardPlacementTextField, place);
        hbox2.setLayoutX(480);
        hbox2.setLayoutY(658);
        hbox2.setSpacing(10);

        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(discardCard, discardTextField, discard);
        hbox3.setLayoutX(800);
        hbox3.setLayoutY(658);
        hbox3.setSpacing(10);

        HBox hbox4 = new HBox();
        hbox4.getChildren().add(end);
        hbox4.setLayoutX(1100);
        hbox4.setLayoutY(658);

        // add all controls
        controls.getChildren().add(hbox1);
        controls.getChildren().add(hbox2);
        controls.getChildren().add(hbox3);
        controls.getChildren().add(hbox4);
    }


    /**
     * Task 18 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    // create a solo game
    public void soloMakeControl(String[][] gameState) {

        Label cardPlacement = new Label("Card Placement");
        cardPlacementTextField = new TextField();
        cardPlacementTextField.setPrefWidth(100);

        Label discardCard = new Label("Discard");
        discardTextField = new TextField();
        discardTextField.setPrefWidth(100);

        Button drawFromDeck = new Button("Draw From Deck");
        Button drawFromDCA = new Button("Draw From Discard A");
        Button drawFromDCB = new Button("Draw From Discard B");

        Button place = new Button("Place");
        Button discard = new Button("Discard");

        Button end = new Button("End my turn");

        // draw a card from Deck
        drawFromDeck.setOnAction(e -> {

            String handCards = "";
            if (gameState[0][0].equals("A")) {
                handCards = gameState[1][1];
            } else {
                handCards = gameState[1][2];
            }

            if (handCards.length() >= 9 * 2 + 1) {
                System.out.println("You cannot draw a card!\nPlease place one card and discard one card!\n----------------------");
            } else if (handCards.length() < 7 * 2 + 1) {
                System.out.println("You need to draw a card!!\nPlease place one card and discard one card!\n----------------------");
            } else {
                String drawCard = Arboretum.drawFromDeck(gameState[1][0]);
                if (drawCard.equals("")) {
                    System.out.println("game over");
                } else {
                    ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[1][0], handCards, drawCard, gameState[0][0]);

                    gameState[1][0] = afterchanging.get(0);

                    if (gameState[0][0].equals("A")) {
                        gameState[1][1] = afterchanging.get(1);
                    } else {
                        gameState[1][2] = afterchanging.get(1);
                    }

                }
                display(gameState);
            }
        });

        // draw a card from Discard Area of player A
        drawFromDCA.setOnAction(e -> {
            String handCards = "";
            if (gameState[0][0].equals("A")) {
                handCards = gameState[1][1];
            } else {
                handCards = gameState[1][2];
            }

            if (handCards.length() >= 9 * 2 + 1) {
                System.out.println("You cannot draw a card!\nPlease place one card and discard one card!\n----------------------");
            } else if (handCards.length() < 7 * 2 + 1) {
                System.out.println("You need to draw a card!!\nPlease place one card and discard one card!\n----------------------");
            } else {
                if (gameState[0][2].length() == 1) {
                    System.out.println("There is no cards in your discard area!\n----------------------");
                } else {
                    int cardsNum = (gameState[0][2].length() - 1) / 2;
                    String drawCard = gameState[0][2].substring(2 * cardsNum - 1, 2 * cardsNum + 1);

                    ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[0][2], handCards, drawCard, gameState[0][0]);

                    gameState[0][2] = afterchanging.get(0);

                    if (gameState[0][0].equals("A")) {
                        gameState[1][1] = afterchanging.get(1);
                    } else {
                        gameState[1][2] = afterchanging.get(1);
                    }
                }
                display(gameState);
            }

        });

        // draw a card from Discard Area of player B
        drawFromDCB.setOnAction(e -> {
            String handCards = "";
            if (gameState[0][0].equals("A")) {
                handCards = gameState[1][1];
            } else {
                handCards = gameState[1][2];
            }

            if (handCards.length() >= 9 * 2 + 1) {
                System.out.println("You cannot draw a card!\nPlease place one card and discard one card!\n----------------------");
            } else if (handCards.length() < 7 * 2 + 1) {
                System.out.println("You need to draw a card!!\nPlease place one card and discard one card!\n----------------------");
            } else {
                if (gameState[0][4].length() == 1) {
                    System.out.println("There is no cards in Player B's discard area!\n----------------------");
                } else {
                    int cardsNum = (gameState[0][4].length() - 1) / 2;
                    String drawCard = gameState[0][4].substring(2 * cardsNum - 1, 2 * cardsNum + 1);

                    ArrayList<String> afterchanging = changeStateAfterDrawCards(gameState[0][4], handCards, drawCard, gameState[0][0]);

                    gameState[0][4] = afterchanging.get(0);
                    if (gameState[0][0].equals("A")) {
                        gameState[1][1] = afterchanging.get(1);
                    } else {
                        gameState[1][2] = afterchanging.get(1);
                    }
                }
                display(gameState);
            }
        });

        // place a card in arboretum
        place.setOnAction(e -> {
            String handCards = "";
            if (gameState[0][0].equals("A")) {
                handCards = gameState[1][1];
            } else {
                handCards = gameState[1][2];
            }

            if (handCards.length() == 7 * 2 + 1) {
                System.out.println("You have placed a card! Or you forget to draw!\n----------------------");
            } else {
                String inforOfPlacement = cardPlacementTextField.getText();
                if (gameState[0][0].equals("A") && gameState[0][1].length() > gameState[0][3].length() || gameState[0][0].equals("B") && gameState[0][1].length() < gameState[0][3].length()) {
                    System.out.println("You have placed a card!\n----------------------");
                } else {
                    if (Arboretum.isPlacementValid(gameState, inforOfPlacement)) {
                        String card = inforOfPlacement.substring(0, 2);
                        if (gameState[0][0].equals("A")) {
                            gameState[0][1] += inforOfPlacement;
                            gameState[1][1] = gameState[1][1].replace(card, "");
                        } else {
                            gameState[0][3] += inforOfPlacement;
                            gameState[1][2] = gameState[1][2].replace(card, "");
                        }
                    } else {
                        System.out.println("Please choose a valid position!\n----------------------");
                    }
                    display(gameState);
                }
            }
        });

        // discard a card to own discard area
        discard.setOnAction(e -> {
            String handCards = "";
            if (gameState[0][0].equals("A")) {
                handCards = gameState[1][1];
            } else {
                handCards = gameState[1][2];
            }

            if (handCards.length() == 7 * 2 + 1) {
                System.out.println("You have discard a card!\n----------------------");
            } else {
                String inforOfDiscard = discardTextField.getText();
                if (gameState[0][0].equals("A")) {
                    gameState[0][2] += inforOfDiscard;
                    gameState[1][1] = gameState[1][1].replace(inforOfDiscard, "");
                } else {
                    gameState[0][4] += inforOfDiscard;
                    gameState[1][2] = gameState[1][2].replace(inforOfDiscard, "");
                }
                display(gameState);
            }
        });

        // end button
        // check if the game is over
        // help computer to complete draw, place and discard opreation.
        end.setOnAction(e -> {
            if (gameState[1][0].length() == 0) {
                String winner = getWinner(gameState);
                System.out.println("The game is over!\nThe game winner is : " + winner + ".\n----------------------");
            } else {
                String handCards = "";
                if (gameState[0][0].equals("A")) {
                    handCards = gameState[1][1];
                } else {
                    handCards = gameState[1][2];
                }

                if (handCards.length() != 7 * 2 + 1) {
                    System.out.println("You need to discard your cards to 7!\n----------------------");
                } else if (gameState[0][0].equals("A") && gameState[0][3].length() == gameState[0][1].length() || gameState[0][0].equals("B") && gameState[0][3].length() < gameState[0][1].length()) {
                    System.out.println("It is your turn!\n----------------------");
                } else {
                    if (gameState[0][0].equals("A")) {
                        gameState[0][0] = "B";
                    } else {
                        gameState[0][0] = "A";
                    }

                    System.out.println("Now, it is your turn: " + gameState[0][0]);
                    display(gameState);
                }
            }
        });

        // put all buttons and text boxes in the bottom of the window
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(drawFromDeck, drawFromDCA, drawFromDCB);
        hbox1.setLayoutX(50);
        hbox1.setLayoutY(658);
        hbox1.setSpacing(10);

        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(cardPlacement, cardPlacementTextField, place);
        hbox2.setLayoutX(480);
        hbox2.setLayoutY(658);
        hbox2.setSpacing(10);

        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(discardCard, discardTextField, discard);
        hbox3.setLayoutX(800);
        hbox3.setLayoutY(658);
        hbox3.setSpacing(10);

        HBox hbox4 = new HBox();
        hbox4.getChildren().add(end);
        hbox4.setLayoutX(1100);
        hbox4.setLayoutY(658);

        // add all controls
        controls.getChildren().add(hbox1);
        controls.getChildren().add(hbox2);
        controls.getChildren().add(hbox3);
        controls.getChildren().add(hbox4);
    }


    // after drawing a card, to change the game state
    public static ArrayList<String> changeStateAfterDrawCards(String targetAreaCards, String handCards, String drawCard, String whoseTurn) {

        ArrayList<String> changeStateAfterDrawCards = new ArrayList<String>();

        // target area card will reduce one
        targetAreaCards = targetAreaCards.replace(drawCard, "");

        // sort the new hand cards
        ArrayList<String> handCardsList = new ArrayList<String>();
        String newHandCardsState = whoseTurn;
        int len = (handCards.length() - 1) / 2;

        for (int i = 0; i < len; i++) {
            handCardsList.add(handCards.substring(1 + 2 * i, 3 + 2 * i));
        }

        handCardsList.add(drawCard);
        handCardsList.sort(Comparator.naturalOrder());

        for (int i = 0; i < len + 1; i++) {
            newHandCardsState += handCardsList.get(i);
        }

        handCards = newHandCardsState;

        changeStateAfterDrawCards.add(targetAreaCards);
        changeStateAfterDrawCards.add(handCards);

        return changeStateAfterDrawCards;
    }


    // get the winner according the gameState
    public static String getWinner(String[][] gameState) {
        int scoresSumA = 0;
        int scoresSumB = 0;

        String winner = "";

        ArrayList<Character> players = new ArrayList<Character>(2);
        players.add('A');
        players.add('B');

        ArrayList<Character> speices = new ArrayList<Character>(2);
        players.add('a');
        players.add('b');
        players.add('c');
        players.add('d');
        players.add('j');
        players.add('m');

        ArrayList<Integer> playerAScoreInEverySpecies = new ArrayList<Integer>();
        ArrayList<Integer> playerBScoreInEverySpecies = new ArrayList<Integer>();

        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < speices.size(); j++) {
                Boolean StateInThisSpeice = Arboretum.canScore(gameState, players.get(i), speices.get(j));
                if (players.get(i) == 'A' && StateInThisSpeice == true) {
                    int scoreA = Arboretum.getHighestViablePathScore(gameState, 'A', speices.get(j));
                    playerAScoreInEverySpecies.add(scoreA);
                    scoresSumA += scoreA;
                } else if (players.get(i) == 'B' && StateInThisSpeice == true) {
                    int scoreB = Arboretum.getHighestViablePathScore(gameState, 'B', speices.get(j));
                    playerBScoreInEverySpecies.add(scoreB);
                    scoresSumB += scoreB;
                }
            }
        }

        if (scoresSumA > scoresSumB) {
            winner = "A";
        } else if (scoresSumB > scoresSumA) {
            winner = "B";
        } else {
            if (playerAScoreInEverySpecies.size() > playerBScoreInEverySpecies.size()) {
                winner = "A";
            } else if (playerAScoreInEverySpecies.size() < playerBScoreInEverySpecies.size()) {
                winner = "B";
            } else {
                winner = "No Winner";
            }
        }

        return winner;
    }


    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 11: Implement a basic playable Arboretum game in JavaFX that only allows cards to be placed in
        //   valid places
        // FIXME Task 16: Implement a computer opponent so that a human can play your game against the computer.
        // FIXME Task 18: Implement variant(s).
        stage.setTitle("Arboretum");
        // Group root = new Group();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        String[][] gameState = initialGameState();

        root.getChildren().add(controls);

        display(gameState);

        ///////////////////////////////////////////////////////////////
        // change the game model to experience different game model. //
        ///////////////////////////////////////////////////////////////
        String game_model = "computer"; // you can set one of the game models (computer / solo)

        if (game_model.equals("computer")) {
            makeControl(gameState);
        } else if (game_model.equals("solo")) {
            soloMakeControl(gameState); // Task 18: solo game

        }

        stage.setScene(scene);
        stage.show();
    }
}
