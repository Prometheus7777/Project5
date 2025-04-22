import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
     * chess marks whether or not the Board object is playing chess or checkers
     */
    private boolean chess;

    /**
     * @param chess is whether or not chess is being played, currently this constructor does not support playing chess
     */
    public Board(boolean chess){
        this.chess = chess;
        gameBoard = new HashMap<>();

        Piece blankSpace = new Man(true, true);
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                String key = toBoardSpace(i, j);
                gameBoard.put(key, new Man(blankSpace));
            }
        }
        if (!chess){
            Piece whiteMan = new Man(true, false);
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
        }
    }

    /**
     * copy constructor for a Board object
     * @param other is the Board object being copied
     */
    public Board(Board other){
        this.chess = other.chess;
        this.gameBoard = new HashMap<>();
        for (String boardSpace : other.gameBoard.keySet()){
            this.gameBoard.put(boardSpace, new Man(other.gameBoard.get(boardSpace)));
        }
    }

    /**
     * movePiece changes some values in gameBoard to reflect a move made by a player
     * @param toMove is the space of the Piece object being moved
     * @param moveTo is the space the Piece object is being moved to
     */
    public Board movePiece(String toMove, String moveTo) throws IllegalArgumentException {
        //TODO: Almost done, just need to add promoting to king
        int[] coordsTestToMove = toCoordinates(toMove);
        int[] coordsTestMoveTo = toCoordinates(moveTo);
        if (isOccupied(toMove) && !isOccupied(moveTo)){
            if (!areAdjacent(toMove, moveTo)){
                if (!checkForJumps()){
                    throw new IllegalArgumentException();
                }
                String newToMove  = gameBoard.get(toMove).moveSpaces(toMove, moveTo);
                if (newToMove.equals(moveTo)){
                    Piece toMovePiece = new Man(gameBoard.get(toMove));
                    gameBoard.replace(toMove, new Man(true, true));
                    gameBoard.replace(moveTo, toMovePiece);
                    Piece removed = new Man(gameBoard.get(between(toMove, moveTo)));
                    gameBoard.replace(between(toMove, moveTo), new Man(true, true));
                    Board toReturn = new Board(this);
                    gameBoard.replace(toMove, toMovePiece);
                    gameBoard.replace(moveTo, new Man(true, true));
                    gameBoard.replace(between(toMove, moveTo), new Man(removed));
                    return toReturn;
                }
                else {
                    throw new IllegalArgumentException();
                }
            }
            else {
                if (checkForJumps()){
                    throw new IllegalArgumentException();
                }
                ArrayList<String> spacesToMove = gameBoard.get(toMove).moveSpaces(toMove);
                if (spacesToMove.contains(moveTo)){
                    Piece toMovePiece = new Man(gameBoard.get(toMove));
                    gameBoard.replace(toMove, new Man(true, true));
                    gameBoard.replace(moveTo, toMovePiece);
                    Board toReturn = new Board(this);
                    gameBoard.replace(toMove, toMovePiece);
                    gameBoard.replace(moveTo, new Man(true, true));
                    return toReturn;
                }
                else {
                    throw new IllegalArgumentException();
                }
            }
        }
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
        if (gameBoard.get(boardSpace).isEmpty()){
            occupied = false;
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
        boolean found = false;
        for (String boardSpace : gameBoard.keySet()){
            int[] coords = toCoordinates(boardSpace);
            if ((coords[0] > 2) && (coords[0] < 7) && (coords[1] > 2) && (coords[1] < 7)){
                if (gameBoard.get(boardSpace).getWhite()){
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
                else {
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
            }
            else if ((coords[0] > 2) && (coords[0] < 7) && (coords[1] <= 2)){
                if (gameBoard.get(boardSpace).getWhite()){
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
                else {
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
            }
            else if ((coords[0] > 2) && (coords[0] < 7) && (coords[1] >= 7)){
                if (gameBoard.get(boardSpace).getWhite()){
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
                else {
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
            }
            else if ((coords[0] <= 2) && (coords[1] > 2) && (coords[1] < 7)){
                if (gameBoard.get(boardSpace).getWhite()){
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
                else {
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] + 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] + 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] + 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
            }
            else if ((coords[0] >= 7) && (coords[1] > 2) && (coords[1] < 7)){
                if (gameBoard.get(boardSpace).getWhite()){
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
                else {
                    if (gameBoard.get(boardSpace).getKing()){
                        if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] + 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] + 1)).isEmpty())) {
                            found = true;
                        }
                        else if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                    else {
                        if ((gameBoard.get(toBoardSpace(coords[0] - 2, coords[1] - 2)).isEmpty()) && (gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).getWhite()) && !(gameBoard.get(toBoardSpace(coords[0] - 1, coords[1] - 1)).isEmpty())) {
                            found = true;
                        }
                    }
                }
            }
        }
        return found;
    }

    /**
     * printBoard goes through the gameBoard map and prints out a board based on the values in gameBoard
     */
    public void printBoard(){
        for (int i = 8; i >= 1; i--) {
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
            return "";
        }
    }
}
