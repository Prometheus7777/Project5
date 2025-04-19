import java.util.ArrayList;

/**
 * The Piece class is abstract because each piece has a certain type, a Man or King if checkers is being played,
 * or one of a Pawn, Rook, Knight, Bishop, Queen, or King if chess is being played
 * The Piece class supports moveSpaces methods which are unique for the type of piece being moved
 */
public abstract class Piece {
    /**
     * name is the identifier for each specific Piece object
     */

    private boolean white;

    private boolean empty;

    public Piece(boolean white, boolean empty){
        this.white = white;
        this.empty = empty;
    }

    public Piece(Piece other){
        this.white = other.white;
        this.empty = other.empty;
    }

    public boolean getWhite(){
        return white;
    }

    public boolean isEmpty(){
        return empty;
    }

    public abstract boolean getMan();
    public abstract boolean getKing();

    /**
     * moveSpaces calculates the spaces a Piece could move to
     * @return a list of all the possible spaces that this Piece could move to
     */
    public abstract ArrayList<String> moveSpaces(String toMove);
    public abstract ArrayList<String> moveSpaces(String toMove, String moveTo);


    /**
     * moveSpaces calculates the spaces a Piece could move to,
     * and then removes previously calculated spaces that are impossible to move to, for example,
     * spaces already containing a piece when playing checkers, or spaces blocked by a piece when playing chess
     * @return a list of all the possible spaces that this Piece could move to
     */
    //public abstract ArrayList<String> moveSpaces(ArrayList<Integer> occupiedSpaces);
}
