package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private static final int GRID_SIZE = 50;
    private static final int GRID_DIMENSION = 10;
    private static final int WINDOW_XOFFSET = 10;
    private static final int WINDOW_YOFFSET = 30;
    private static final int TEXTBOX_WIDTH = 120;

    private final Group root = new Group();
    private final Group controls = new Group();

    private TextField turnIDTextField;
    private TextField aArboretumTextField;
    private TextField bArboretumTextField;
    private TextField aDiscardTextField;
    private TextField bDiscardTextField;
    private TextField deckTextField;
    private TextField aHandTextField;
    private TextField bHandTextField;

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param gameState TASK 6
     */

    /**
     * Task 6 Authorship details:
     *
     * Author: Ziyang Chen
     * UID: u6908560
     */
    void displayState(String[][] gameState) {

        root.getChildren().clear();
        root.getChildren().add(controls);

        String[] shareState = gameState[0];
        String[] hiddenState = gameState[1];

        // whose turn
        String whoseTurn = shareState[0];

        String ArboretumCards = "";
        String discardCards = "";
        String handCards = "";
        String deckCards = hiddenState[0];

        if (whoseTurn.equals("A")) {
            ArboretumCards = shareState[1].substring(1, shareState[1].length());
            discardCards = shareState[2].substring(1, shareState[2].length());
            handCards = hiddenState[1].substring(1, hiddenState[1].length());
        } else {
            ArboretumCards = shareState[3].substring(1, shareState[3].length());
            discardCards = shareState[4].substring(1, shareState[4].length());
            handCards = hiddenState[2].substring(1, hiddenState[2].length());
        }

        // arboretum number
        int arboretumNum = ArboretumCards.length() / 8;

        // discard cards number
        int discardNum = discardCards.length() / 2;

        // hand number
        int handNum = handCards.length() / 2;

        // deck cards number
        int deckNum = deckCards.length() / 2;


        // for showing the game
        double cardWidth = 60;
        double cardHeight = 70;

        // player
        var playerText = new Text("Player " + whoseTurn);
        playerText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        playerText.setFill(Color.BLACK);
        playerText.setLayoutX(500);
        playerText.setLayoutY(30);
        root.getChildren().add(playerText);

        // deck show
        var deckText = new Text("Deck");
        deckText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        deckText.setFill(Color.BLACK);
        deckText.setLayoutX(35);
        deckText.setLayoutY(45);
        root.getChildren().add(deckText);

        if (deckNum >= 1) {
            var deckArea = new Rectangle(cardWidth, cardHeight);
            deckArea.setLayoutX(30);
            deckArea.setLayoutY(50);
            root.getChildren().add(deckArea);
        }

        // discard show
        var discardText = new Text("Discard");
        discardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        discardText.setFill(Color.BLACK);
        discardText.setLayoutX(961);
        discardText.setLayoutY(45);
        root.getChildren().add(discardText);

        if (discardNum == 1) {
            var discardAreaCard1 = new Rectangle(cardWidth, cardHeight);
            discardAreaCard1.setLayoutX(970);
            discardAreaCard1.setLayoutY(50);
            discardAreaCard1.setOpacity(0.3);
            root.getChildren().add(discardAreaCard1);

            String discardText1 = discardCards.substring(2 * (discardNum - 1), 2 * discardNum);

            var discardCardText1 = new Text(discardText1);
            discardCardText1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
            discardCardText1.setFill(Color.BLACK);
            discardCardText1.setLayoutX(987);
            discardCardText1.setLayoutY(73);
            root.getChildren().add(discardCardText1);
        }

        if (discardNum >= 2) {
            var discardAreaCard1 = new Rectangle(cardWidth, 30);
            discardAreaCard1.setLayoutX(970);
            discardAreaCard1.setLayoutY(50);
            discardAreaCard1.setOpacity(0.3);
            root.getChildren().add(discardAreaCard1);

            var discardAreaCard2 = new Rectangle(cardWidth, cardHeight);
            discardAreaCard2.setLayoutX(970);
            discardAreaCard2.setLayoutY(80);
            discardAreaCard2.setOpacity(0.5);
            root.getChildren().add(discardAreaCard2);

            String discardText1 = discardCards.substring(2 * (discardNum - 2), 2 * (discardNum - 1));
            String discardText2 = discardCards.substring(2 * (discardNum - 1), 2 * discardNum);

            var discardCardText1 = new Text(discardText1);
            discardCardText1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
            discardCardText1.setFill(Color.BLACK);
            discardCardText1.setLayoutX(987);
            discardCardText1.setLayoutY(73);
            root.getChildren().add(discardCardText1);

            var discardCardText2 = new Text(discardText2);
            discardCardText2.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
            discardCardText2.setFill(Color.BLACK);
            discardCardText2.setLayoutX(987);
            discardCardText2.setLayoutY(120);
            root.getChildren().add(discardCardText2);
        }

        // hand show
        var handText = new Text("Hand");
        handText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        handText.setFill(Color.BLACK);
        handText.setLayoutX(40);
        handText.setLayoutY(650);
        root.getChildren().add(handText);

        for (int i = 0; i < handNum; i++) {
            var handAreaCard = new Rectangle(cardWidth, cardHeight);
            handAreaCard.setLayoutX(108 + ((960 / handNum) - cardWidth) / 2 + i * (960 / handNum));
            handAreaCard.setLayoutY(625);
            handAreaCard.setOpacity(0.5);
            root.getChildren().add(handAreaCard);

            String handCard = handCards.substring(2 * i, 2 * i + 2);

            var handCardText = new Text(handCard);
            handCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
            handCardText.setFill(Color.BLACK);
            handCardText.setLayoutX(108 + ((960 / handNum) - cardWidth) / 2 + i * (960 / handNum) + cardWidth / 3.3);
            handCardText.setLayoutY(625 + cardHeight / 1.8);
            root.getChildren().add(handCardText);
        }

        // arboretum show
        for (int i = 0; i < arboretumNum; i++) {

            String piece = ArboretumCards.substring(8 * i, 8 * i + 8);
            String ArboretumCard = piece.substring(0, 2);
            String position = piece.substring(2, 8);

            if (position.equals("C00C00")) {
                var rect = new Rectangle(cardWidth, cardHeight);
                rect.setLayoutX(1080 / 2 - cardWidth / 2);
                rect.setLayoutY(280);
                rect.setOpacity(0.5);
                root.getChildren().add(rect);

                var ArboretumCardText = new Text(ArboretumCard);
                ArboretumCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
                ArboretumCardText.setFill(Color.BLACK);
                ArboretumCardText.setLayoutX(1080 / 2 - cardWidth / 2 + cardWidth / 3.3);
                ArboretumCardText.setLayoutY(280 + cardHeight / 1.8);
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
                rect.setLayoutX(1080 / 2 - cardWidth / 2 + shiftDisX);
                rect.setLayoutY(280 + shiftDisY);
                rect.setOpacity(0.5);
                root.getChildren().add(rect);

                var ArboretumCardText = new Text(ArboretumCard);
                ArboretumCardText.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
                ArboretumCardText.setFill(Color.BLACK);
                ArboretumCardText.setLayoutX(1080 / 2 - cardWidth / 2 + cardWidth / 3.3 + shiftDisX);
                ArboretumCardText.setLayoutY(280 + cardHeight / 1.8 + shiftDisY);
                root.getChildren().add(ArboretumCardText);
            }
        }

        // FIXME Task 6: implement the simple state viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Player Turn ID");
        turnIDTextField = new TextField();
        turnIDTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aArboretum = new Label("Player A Arboretum:");
        aArboretumTextField = new TextField();
        aArboretumTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aDiscard = new Label("Player A Discard:");
        aDiscardTextField = new TextField();
        aDiscardTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bArboretum = new Label("Player B Arboretum:");
        bArboretumTextField = new TextField();
        bArboretumTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bDiscard = new Label("Player B Discard:");
        bDiscardTextField = new TextField();
        bDiscardTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label deck = new Label("Deck:");
        deckTextField = new TextField();
        deckTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aHand = new Label("Player A Hand:");
        aHandTextField = new TextField();
        aHandTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bHand = new Label("Player B Discard:");
        bHandTextField = new TextField();
        bHandTextField = new TextField();
        bHandTextField.setPrefWidth(TEXTBOX_WIDTH);

        Button displayState = new Button("Display State");
        displayState.setOnAction(e -> {
            String[] sharedState = {turnIDTextField.getText(), aArboretumTextField.getText(),
                    aDiscardTextField.getText(), bArboretumTextField.getText(), bDiscardTextField.getText()};
            String[] hiddenState = {deckTextField.getText(), aHandTextField.getText(), bHandTextField.getText()};
            displayState(new String[][]{sharedState, hiddenState});
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(boardLabel, turnIDTextField, aArboretum, aArboretumTextField, aDiscard,
                aDiscardTextField, bArboretum, bArboretumTextField, bDiscard, bDiscardTextField, deck, deckTextField,
                aHand, aHandTextField, bHand, bHandTextField, displayState);
        vbox.setSpacing(10);
        vbox.setLayoutX(10.4 * (GRID_SIZE) + (2 * WINDOW_XOFFSET) + (GRID_DIMENSION * GRID_SIZE) + (0.5 * GRID_SIZE));
        vbox.setLayoutY(WINDOW_YOFFSET);

        controls.getChildren().add(vbox);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Arboretum Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

