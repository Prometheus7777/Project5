import java.util.ArrayList;

/**
 * The King class extends the Piece class and is one of the two types of pieces used in checkers,
 * King is only implemented when a Man reaches the back rank of the board
 */
public class King extends Piece {

    public King (Piece other){
        super(other.getType() + "King");
    }

    /**
     * moveSpaces calculates the spaces a Piece could move to
     * @return a list of all the possible spaces that this Piece could move to
     */
    public ArrayList<String> moveSpaces(String toMove, String moveTo){
        //TODO: Implement this method
        return new ArrayList<>();
    }

    /**
     * moveSpaces calculates the spaces a Piece could move to,
     * and then removes previously calculated spaces that are impossible to move to,
     * for example, spaces already containing a piece
     * @return a list of all the possible spaces that this Piece could move to
     */
//    public ArrayList<String> moveSpaces(ArrayList<String> occupiedSpaces){
//        return new ArrayList<String>();
//    }
}
