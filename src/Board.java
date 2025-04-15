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

    public Board(boolean chess){
        this.chess = chess;
        gameBoard = new HashMap<>();

        if (!chess){
            //TODO: I'll fix this constructor
        }
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                Piece blankSpace = new Man(new PieceType(0, true), i ,j);
//                gameBoard.add(blankSpace);
//            }
//        }
//        if (!chess){
//            for (int i = 1; i < 24; i += 2) {
//                Piece newWhitePiece = new Man(new PieceType(2, false), i % 8, i / 8);
//                gameBoard.add(i, newWhitePiece);
//            }
//            for (int i = 41; i < 64; i += 2) {
//                Piece newBlackPiece = new Man(new PieceType(3, false), i % 8, i / 8);
//                gameBoard.add(i, newBlackPiece);
//            }
//        }
    }

    /**
     * movePiece changes some values in gameBoard to reflect a move made by a player
     * @param toMove is the space of Piece object being moved
     * @param moveTo is the board space the Piece object is being moved to
     */
    public void movePiece(String toMove, String moveTo){
        //TODO: Implement this method
    }

    /**
     * isOccupied checks a space in gaeBoard and says if it contains a piece or not
     * @return boolean depending on if the space is occupied
     */
    public boolean isOccupied(String boardSpace){
        boolean occupied = true;
        if (gameBoard.get(boardSpace).getName().equals("")){
            occupied = false;
        }
        return occupied;
    }

    /**
     * play implements the other methods found in this class and uses the member variables to play a game
     * of chess or checkers depending on the value of the member variable chess
     */
    public void printBoard(){
        //TODO: Iterate through gameBoard and print out a board based on that
//        for (int i = gameBoard.size() - 1; i >= 0; i--) {
//            if (gameBoard.get(i).getName().getEmpty()){
//                System.out.println("_");
//            }
//            else {
//                if (gameBoard.get(i).getName().isB()){
//                    System.err.println("o");
//                }
//                else {
//                    System.out.println("o");
//                }
//            }
//        }
    }

    public static String toBoardSpace(int x, int y){
        StringBuilder sb = new StringBuilder();
        char space = (char) (x + 96);
        sb.append(space);
        sb.append(y);
        return sb.toString();
        //97-104
    }

    public static int[] toCoordinates(String boardSpace){
        int[] coords = new int[2];
        coords[0] = (boardSpace.charAt(0) - 96);
        coords[1] = (boardSpace.charAt(1) - 48);

        return coords;
    }
}
