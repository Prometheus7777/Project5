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

        Piece blankSpace = new Man("");
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                String key = toBoardSpace(i, j);
                gameBoard.put(key, new Man(blankSpace));
            }
        }
        if (!chess){
            Piece whiteMan = new Man("wMan");
            for (int i = 1; i < 4; i += 2) {
                for (int j = 1; j < 8; j += 2) {
                    String key = toBoardSpace(i, j);
                    gameBoard.replace(key, new Man(whiteMan));
                }
            }
            for (int i = 2; i < 9; i += 2) {
                String key = toBoardSpace(2, i);
                gameBoard.replace(key, new Man(whiteMan));
            }

            Piece blackMan = new Man("bMan");
            for (int i = 6; i < 9; i += 2) {
                for (int j = 2; j < 9; j += 2) {
                    String key = toBoardSpace(i, j);
                    gameBoard.replace(key, new Man(blackMan));
                }
            }
            for (int i = 1; i < 8; i += 2) {
                String key = toBoardSpace(7, i);
                gameBoard.replace(key, new Man(blackMan));
            }
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

    public Board(Board other){
        this.chess = other.chess;
        gameBoard = new HashMap<>(other.gameBoard);
        for (String boardSpace : other.gameBoard.keySet()){
            gameBoard.put(boardSpace, new Man(other.gameBoard.get(boardSpace)));
        }
    }

    /**
     * movePiece changes some values in gameBoard to reflect a move made by a player
     * @param toMove is the space of Piece object being moved
     * @param moveTo is the board space the Piece object is being moved to
     */
    public Board movePiece(String toMove, String moveTo){
        //TODO: Implement this method
        Piece toMovePiece = new Man(gameBoard.get(toMove));
        gameBoard.replace(toMove, new Man(""));
        gameBoard.replace(moveTo, toMovePiece);
        return new Board(this);
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
        for (int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {
                String tempSpace = toBoardSpace(i, j);
                if (!isOccupied(tempSpace)){
                    System.out.print("|_");
                }
                else {
                    if (gameBoard.get(tempSpace).getColor().equals("w")){
                        if (gameBoard.get(tempSpace).getMan()){
                            System.out.print("|K");
                        }
                        else {
                            System.out.print("|" + (char) '\u03B8');
                        }
                    }
                    else {
                        if (gameBoard.get(tempSpace).getMan()){
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
