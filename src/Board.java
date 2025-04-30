import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * The Board class uses Maps and Stacks of the Piece class and subclasses to run a checkers game,
 * or a chess game, depending on how a Board object is created.
 */
public class Board {
    /**
     * gameBoard keeps track of Piece objects based on String locations
     */
    private Map<String, Piece> gameBoard;

    /**
     * white keeps track of the player's turn
     */
    private boolean white;
    private JFrame frame;
    private JPanel panel;

    public Board(){
        white = true;
        gameBoard = new HashMap<>();

        Piece blankSpace = new Man(true, true);
        //Setting up gameBoard with 64 blank spaces
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                String key = toBoardSpace(i, j);
                gameBoard.put(key, new Man(blankSpace));
            }
        }
        Piece whiteMan = new Man(true, false);
        //Changing some to white pieces
        for (int i = 1; i < 4; i += 2) {
            for (int j = 1; j < 8; j += 2) {
                String key = toBoardSpace(j, i);
                gameBoard.replace(key, new Man(whiteMan));
            }
        }
        for (int i = 2; i < 9; i += 2) {
            String key = toBoardSpace(i, 2);
            gameBoard.replace(key, new Man(whiteMan));
        }

        Piece blackMan = new Man(false, false);
        //Setting some to black pieces
        for (int i = 6; i < 9; i += 2) {
            for (int j = 2; j < 9; j += 2) {
                String key = toBoardSpace(j, i);
                gameBoard.replace(key, new Man(blackMan));
            }
        }
        for (int i = 1; i < 8; i += 2) {
            String key = toBoardSpace(i, 7);
            gameBoard.replace(key, new Man(blackMan));
        }
        frame = new JFrame("GUI Test");
        panel = new JPanel(); panel.setLayout(new GridLayout(8,8));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640,640);
    }

    /**
     * copy constructor for a Board object
     * @param other is the Board object being copied
     */
    public Board(Board other){
        this.white = other.white;
        this.gameBoard = new HashMap<>();
        for (String boardSpace : other.gameBoard.keySet()){
            if (other.gameBoard.get(boardSpace).getKing()){
                this.gameBoard.put(boardSpace, new King(other.gameBoard.get(boardSpace)));
            }
            else{
                this.gameBoard.put(boardSpace, new Man(other.gameBoard.get(boardSpace)));
            }
        }
        this.frame = other.frame;
        this.panel = other.panel;
    }

    /**
     * @return the boolean value of white
     */
    public boolean getWhite(){
        return white;
    }

    /**
     * movePiece changes some values in gameBoard to reflect a move made by a player
     * @param toMove is the space of the Piece object being moved
     * @param moveTo is the space the Piece object is being moved to
     */
    public Board movePiece(String toMove, String moveTo) throws IllegalArgumentException {
        //Testing the coordinates initially, if they are invalid toCoordinates will throw an IllegalArgumentException
        int[] coordsTestToMove = toCoordinates(toMove);
        int[] coordsTestMoveTo = toCoordinates(moveTo);
        //If the color of the piece is wrong, throw an IllegalArgumentException
        if (white != gameBoard.get(toMove).getWhite()){
            throw new IllegalArgumentException();
        }
        //These next if statements go over the different possibilities for moves
        if (isOccupied(toMove) && !isOccupied(moveTo)){
            //Different cases for if the spaces are adjacent or not, i.e. if a jump or normal move is being attempted
            if (!areAdjacent(toMove, moveTo)){
                //If a jump is being attempted and there are no jumps possible, throw an IllegalArgumentException
                if (!checkForJumps()){
                    throw new IllegalArgumentException();
                }
                //Check for a jump between the two spaces input by the user, the reason both checkForJump
                //and checkForJumps in general are used is because of certain exception that are thrown
                //This is controlled so that only an IllegalArgumentException is thrown
                if (checkForJump(toMove, moveTo)){
                    //The jump is confirmed as valid,
                    //but more information must be determined to find out what edits must be made to the new Board object
                    //Different things will happen based on
                    if (promoting(toMove, moveTo)){//If the piece is promoting
                        Piece toMovePiece = new Man(gameBoard.get(toMove));
                        gameBoard.replace(toMove, new Man(true, true));
                        gameBoard.replace(moveTo, new King(toMovePiece));
                        Piece removed = new Man(gameBoard.get(between(toMove, moveTo)));
                        gameBoard.replace(between(toMove, moveTo), new Man(true, true));
                        if (!checkForJumpsAroundSpace(moveTo)){//And if the piece has a double jump
                            white = !white;
                            Board toReturn = new Board(this);
                            gameBoard.replace(toMove, toMovePiece);
                            gameBoard.replace(moveTo, new Man(true, true));
                            gameBoard.replace(between(toMove, moveTo), new Man(removed));
                            white = !white;
                            return toReturn;
                        }
                        else {//And the piece does not have a double jump
                            Board toReturn = new Board(this);
                            gameBoard.replace(toMove, toMovePiece);
                            gameBoard.replace(moveTo, new Man(true, true));
                            gameBoard.replace(between(toMove, moveTo), new Man(removed));
                            return toReturn;
                        }
                    }
                    else if (gameBoard.get(toMove).getKing()){//If the piece is a King
                        Piece toMovePiece = new King(gameBoard.get(toMove));
                        gameBoard.replace(toMove, new Man(true, true));
                        gameBoard.replace(moveTo, new King(toMovePiece));
                        Piece removed = new Man(gameBoard.get(between(toMove, moveTo)));
                        gameBoard.replace(between(toMove, moveTo), new Man(true, true));
                        if (!checkForJumpsAroundSpace(moveTo)){//And the piece has a double jump
                            white = !white;
                            Board toReturn = new Board(this);
                            gameBoard.replace(toMove, toMovePiece);
                            gameBoard.replace(moveTo, new Man(true, true));
                            gameBoard.replace(between(toMove, moveTo), new Man(removed));
                            white = !white;
                            return toReturn;
                        }
                        else {//And the piece does not have a double jump
                            Board toReturn = new Board(this);
                            gameBoard.replace(toMove, toMovePiece);
                            gameBoard.replace(moveTo, new Man(true, true));
                            gameBoard.replace(between(toMove, moveTo), new Man(removed));
                            return toReturn;
                        }
                    }
                    else {//If the piece is a Man and not promoting
                        Piece toMovePiece = new Man(gameBoard.get(toMove));
                        gameBoard.replace(toMove, new Man(true, true));
                        gameBoard.replace(moveTo, toMovePiece);
                        Piece removed = new Man(gameBoard.get(between(toMove, moveTo)));
                        gameBoard.replace(between(toMove, moveTo), new Man(true, true));
                        if (!checkForJumpsAroundSpace(moveTo)){//And the piece has a double jump
                            white = !white;
                            Board toReturn = new Board(this);
                            gameBoard.replace(toMove, toMovePiece);
                            gameBoard.replace(moveTo, new Man(true, true));
                            gameBoard.replace(between(toMove, moveTo), new Man(removed));
                            white = !white;
                            return toReturn;
                        }
                        else {//And the piece does not have a double jump
                            Board toReturn = new Board(this);
                            gameBoard.replace(toMove, toMovePiece);
                            gameBoard.replace(moveTo, new Man(true, true));
                            gameBoard.replace(between(toMove, moveTo), new Man(removed));
                            return toReturn;
                        }
                    }
                }
                else {//If the jump between toMove and moveTo is not possible
                    throw new IllegalArgumentException();
                }
            }
            else {
                //If the spaces are adjacent but there are jumps possible, throw an IllegalArgumentException, as jumps are forced
                if (checkForJumps()){
                throw new IllegalArgumentException();
                }
                ArrayList<String> spacesToMove = gameBoard.get(toMove).moveSpaces(toMove);
                if (spacesToMove.contains(moveTo)){
                    //The move is confirmed as valid,
                    //but more information must be determined to find out what edits must be made to the new Board object
                    //Different things will happen based on
                    if (promoting(toMove, moveTo)){//If the piece is promoting
                        Piece toMovePiece = new Man(gameBoard.get(toMove));
                        gameBoard.replace(toMove, new Man(true, true));
                        gameBoard.replace(moveTo, new King(toMovePiece));
                        white = !white;
                        Board toReturn = new Board(this);
                        gameBoard.replace(toMove, toMovePiece);
                        gameBoard.replace(moveTo, new Man(true, true));
                        white = !white;
                        return toReturn;
                    }
                    else if (gameBoard.get(toMove).getKing()){//If the piece is a King
                        Piece toMovePiece = new King(gameBoard.get(toMove));
                        gameBoard.replace(toMove, new Man(true, true));
                        gameBoard.replace(moveTo, toMovePiece);
                        white = !white;
                        Board toReturn = new Board(this);
                        gameBoard.replace(toMove, toMovePiece);
                        gameBoard.replace(moveTo, new Man(true, true));
                        white = !white;
                        return toReturn;
                    }
                    else {//If the piece is a Man and not promoting
                        Piece toMovePiece = new Man(gameBoard.get(toMove));
                        gameBoard.replace(toMove, new Man(true, true));
                        gameBoard.replace(moveTo, toMovePiece);
                        white = !white;
                        Board toReturn = new Board(this);
                        gameBoard.replace(toMove, toMovePiece);
                        gameBoard.replace(moveTo, new Man(true, true));
                        white = !white;
                        return toReturn;
                    }
                }
                //If the move is not valid, throw an IllegalArgumentException
                else {
                    throw new IllegalArgumentException();
                }
            }
        }
        //If the space the piece is moving from is empty or the space it is moving to is occupied, throw an IllegalArgumentException
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * isOccupied checks a space in gaeBoard and says if it contains a piece or not
     * @return boolean depending on if the space is occupied
     */
    public boolean isOccupied(String boardSpace){
        boolean occupied = true;
        for (String tempBoardSpace : gameBoard.keySet()){
            if (tempBoardSpace.equalsIgnoreCase(boardSpace)){
                if (gameBoard.get(boardSpace).isEmpty()){
                    occupied = false;
                }
            }
        }
        return occupied;
    }

    /**
     * @param toMove is the space of the Piece object being moved
     * @param moveTo is the space the Piece object is being moved to
     * @return whether or not the toMove and moveTo spaces are next ot each other
     * @throws IllegalArgumentException if there are out of bounds coordinates
     */
    public boolean areAdjacent(String toMove, String moveTo) throws IllegalArgumentException{
        int[] coordsToMove = toCoordinates(toMove);
        int[] coordsMoveTo = toCoordinates(moveTo);
        if ((coordsToMove[0] != coordsMoveTo[0] + 1) && (coordsToMove[0] != coordsMoveTo[0] - 1) && (coordsToMove[1] != coordsMoveTo[1] + 1) && (coordsToMove[1] != coordsMoveTo[1] - 1)){
            return false;
        }
        return true;
    }

    /**
     * @return whether or not there are jumps possible on the Board object
     * @throws IllegalArgumentException if there are out of bounds coordinates
     */
    public boolean checkForJumps() throws IllegalArgumentException{
        for (String boardSpace : gameBoard.keySet()){
            int[] coords = toCoordinates(boardSpace);
            if (!gameBoard.get(boardSpace).isEmpty()){
                if ((coords[0] + 2 <= 8) && (coords[1] + 2 <= 8)){
                    if (checkForJump(toBoardSpace(coords[0], coords[1]), toBoardSpace(coords[0] + 2, coords[1] + 2))){
                        if (white == gameBoard.get(toBoardSpace(coords[0], coords[1])).getWhite()){
                            return true;
                        }
                    }
                }
                if ((coords[0] + 2 <= 8) && (coords[1] - 2 >= 1)){
                    if (checkForJump(toBoardSpace(coords[0], coords[1]), toBoardSpace(coords[0] + 2, coords[1] - 2))){
                        if (white == gameBoard.get(toBoardSpace(coords[0], coords[1])).getWhite()){
                            return true;
                        }
                    }
                }
                if ((coords[0] - 2 >= 1) && (coords[1] + 2 <= 8)){
                    if (checkForJump(toBoardSpace(coords[0], coords[1]), toBoardSpace(coords[0] - 2, coords[1] + 2))){
                        if (white == gameBoard.get(toBoardSpace(coords[0], coords[1])).getWhite()){
                            return true;
                        }
                    }
                }
                if ((coords[0] - 2 >= 1) && (coords[1] -2 >= 1)){
                    if (checkForJump(toBoardSpace(coords[0], coords[1]), toBoardSpace(coords[0] - 2, coords[1] - 2))){
                        if (white == gameBoard.get(toBoardSpace(coords[0], coords[1])).getWhite()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param toMove the space where jumps are checked around
     * @return whether or not there are any jumps found around toMove
     */
    public boolean checkForJumpsAroundSpace(String toMove){
        boolean found = false;
        int[] toMoveCoords = toCoordinates(toMove);
        if ((toMoveCoords[0] + 2 <= 8) && (toMoveCoords[1] + 2 <= 8)){
            if (checkForJump(toMove, toBoardSpace(toMoveCoords[0] + 2, toMoveCoords[1] + 2))){
                found = true;
            }
        }
        if ((toMoveCoords[0] + 2 <= 8) && (toMoveCoords[1] - 2 >= 1)){
            if (checkForJump(toMove, toBoardSpace(toMoveCoords[0] + 2, toMoveCoords[1] - 2))){
                found = true;
            }
        }
        if ((toMoveCoords[0] - 2 >= 1) && (toMoveCoords[1] + 2 <= 8)){
            if (checkForJump(toMove, toBoardSpace(toMoveCoords[0] - 2, toMoveCoords[1] + 2))){
                found = true;
            }
        }
        if ((toMoveCoords[0] - 2 >= 1) && (toMoveCoords[1] - 2 >= 1)){
            if (checkForJump(toMove, toBoardSpace(toMoveCoords[0] - 2, toMoveCoords[1] - 2))){
                found = true;
            }
        }
        return found;
    }

    /**
     * @param toMove is the space of the Piece object being moved
     * @param moveTo is the space the Piece object is being moved to
     * @return whether or not there is a legal jump between toMove and moveTo
     * @throws IllegalArgumentException if there are out of bounds coordinates
     */
    public boolean checkForJump(String toMove, String moveTo) throws IllegalArgumentException{
        String spaceBetween = between(toMove, moveTo);
        if (!gameBoard.get(moveTo).isEmpty()){
            return false;
        }
        if (!gameBoard.get(spaceBetween).isEmpty()){
            if (!gameBoard.get(toMove).getKing()){
                if (gameBoard.get(toMove).getWhite() && (!gameBoard.get(spaceBetween).getWhite())){
                    int[] coordsToMove = toCoordinates(toMove);
                    int[] coordsBetween = toCoordinates(spaceBetween);
                    if (coordsToMove[1] < coordsBetween[1]){
                        return true;
                    }
                    return false;
                }
                else if (!gameBoard.get(toMove).getWhite() && (gameBoard.get(spaceBetween).getWhite())){
                    int[] coordsToMove = toCoordinates(toMove);
                    int[] coordsBetween = toCoordinates(spaceBetween);
                    if (coordsToMove[1] > coordsBetween[1]){
                        return true;
                    }
                    return false;
                }
                else {
                    return false;
                }
            }
            else {
                if (gameBoard.get(toMove).getWhite() && (!gameBoard.get(spaceBetween).getWhite())){
                    return true;
                }
                else if (!gameBoard.get(toMove).getWhite() && (gameBoard.get(spaceBetween).getWhite())){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }

    /**
     * @param toMove is the space of the Piece object being moved
     * @param moveTo is the space the Piece object is being moved to
     * @return whether or not the piece is promoting
     */
    public boolean promoting(String toMove, String moveTo){
        int[] coords = toCoordinates(moveTo);
        if (((coords[1] == 8) && (gameBoard.get(toMove).getWhite()) && !(gameBoard.get(toMove).getKing())) || (coords[1] == 1) && !(gameBoard.get(toMove).getWhite()) && !(gameBoard.get(toMove).getKing())){
            return true;
        }
        return false;
    }

    /**
     * printBoard goes through the gameBoard map and prints out a board based on the values in gameBoard
     */
    public void printBoard(){
        panel.removeAll();
        for(int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {
                String tempSpace = toBoardSpace(j, i);
                boolean temp = gameBoard.get(tempSpace).getWhite();
                char mk;
                if (!isOccupied(tempSpace)) {
                    mk = '_';
                } else {
                    if (gameBoard.get(tempSpace).getKing()) {
                        mk = 'K';
                    } else {
                        mk = '\u03B8';
                    }
                }
                panel.add(new space((i-1) * 80, (j-1) * 80, temp, mk));
            }
        }
        frame.add(panel);
        frame.setVisible(true);
        panel.setVisible(true);
        for (int i = 8; i >= 1; i--) {
            System.out.print(i + " ");
            for (int j = 1; j <= 8; j++) {
                String tempSpace = toBoardSpace(j, i);
                if (!isOccupied(tempSpace)){
                    System.out.print("|_");
                }
                else {
                    if (gameBoard.get(tempSpace).getWhite()){
                        if (gameBoard.get(tempSpace).getKing()){
                            System.out.print("|K");
                        }
                        else {
                            System.out.print("|" + (char) '\u03B8');
                        }
                    }
                    else {
                        if (gameBoard.get(tempSpace).getKing()){
                            System.out.print("|k");
                        }
                        else {
                            System.out.print("|o");
                        }
                    }
                }
            }
            System.out.print("|\n");
        }
        System.out.println("   a b c d e f g h");
    }

    /**
     * @param x is the x coordinate
     * @param y is the y coordinate
     * @return the String for the boardSpace the corresponds to the coordinates x and y
     * @throws IllegalArgumentException if x or y is out of bounds
     */
    public static String toBoardSpace(int x, int y) throws IllegalArgumentException{
        if ((x < 1) || (x > 8) || (y < 1) || (y > 8)){
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        char space = (char) (x + 96);
        sb.append(space);
        sb.append(y);
        return sb.toString();
    }

    /**
     * @param boardSpace the String for the boardSpace the corresponds to the coordinates x and y
     * @return an int[] array of length 2 with the coordinates x and y
     * @throws IllegalArgumentException if boardSpace does not fit the proper format
     */
    public static int[] toCoordinates(String boardSpace) throws IllegalArgumentException{
        if (boardSpace.length() < 2){
            throw new IllegalArgumentException();
        }
        int[] coords = new int[2];
        coords[0] = (boardSpace.charAt(0) - 96);
        coords[1] = (boardSpace.charAt(1) - 48);
        if ((coords[0] < 1) || (coords[0] > 8) || (coords[1] < 1) || (coords[1] > 8)){
            throw new IllegalArgumentException();
        }
        return coords;
    }

    /**
     * @param toMove is the space of the Piece object being moved
     * @param moveTo is the space the Piece object is being moved to
     * @return the board space between toMove and moveTo
     * @throws IllegalArgumentException if there are out of bounds coordinates
     */
    public static String between(String toMove, String moveTo) throws IllegalArgumentException{
        int[] coordsToMove = toCoordinates(toMove);
        int[] coordsMoveTo = toCoordinates(moveTo);
        int xDif = coordsToMove[0] - coordsMoveTo[0];
        int yDif = coordsToMove[1] - coordsMoveTo[1];
        if ((xDif == 2) && (yDif == 2)){
            return toBoardSpace(coordsToMove[0] - 1, coordsToMove[1] - 1);
        }
        else if ((xDif == -2) && (yDif == 2)){
            return toBoardSpace(coordsToMove[0] + 1, coordsToMove[1] - 1);
        }
        else if ((xDif == 2) && (yDif == -2)){
            return toBoardSpace(coordsToMove[0] - 1, coordsToMove[1] + 1);
        }
        else if ((xDif == -2) && (yDif == -2)){
            return toBoardSpace(coordsToMove[0] + 1, coordsToMove[1] + 1);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * gameOver checks the state of the gameBoard to see if the requirements for ending the game have been reached
     * @return whether or not the game has ended
     */
    public boolean gameOver(){
        boolean white = false;
        boolean initial = true;
        for (String boardSpace : gameBoard.keySet()){
            if (!initial){
                if (!gameBoard.get(boardSpace).isEmpty()){
                    if (white != gameBoard.get(boardSpace).getWhite()){
                        return false;
                    }
                }
            }
            else {
                if (!gameBoard.get(boardSpace).isEmpty()){
                    white = gameBoard.get(boardSpace).getWhite();
                    initial = false;
                }
            }
        }
        return true;
    }

    private class space extends JComponent {
        private int x;
        private int y;
        private boolean color;
        private char mk;

        public space(int x, int y, boolean color, char type) {
            this.x = x;
            this.y = y;
            this.color = color;
            mk = type; //man or king
            setPreferredSize(new Dimension(80,80));

        }
        @Override
        protected void paintComponent(Graphics g){
            if((x/80)%2 == 1 ^ (y/80)%2 == 1) {
                g.setColor(Color.white);
            }
            else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.fillRect(0,0,80,80);
            super.paintComponent(g);
            if(color)
                g.setColor(Color.BLACK);
            else
                g.setColor(Color.red);
            g.drawString(Character.toString(mk), 40,40);
        }
    }
}
