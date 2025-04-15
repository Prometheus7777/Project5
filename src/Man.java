import java.util.ArrayList;

/**
 * The Man class extends the Piece class and is one of the two types of pieces used in checkers
 */
public class Man extends Piece {

    public Man(String name){
        super(name);
    }

    public Man(Piece other){
        super(other);
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
