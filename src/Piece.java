import java.util.ArrayList;

/**
 * The Piece class is abstract because each piece has a certain type, a Man or King if checkers is being played,
 * or one of a Pawn, Rook, Knight, Bishop, Queen, or King if chess is being played
 * The Piece class supports moveSpaces methods which are unique for the type of piece being moved
 */
public abstract class Piece {
    /**
     * white is a boolean showing whether or not the piece object is white
     */
    private boolean white;

    /**
     * empty is a boolean showing whether or not the piece object is on a board space
     */
    private boolean empty;

    public Piece(boolean white, boolean empty){
        this.white = white;
        this.empty = empty;
    }

    public Piece(Piece other){
        this.white = other.white;
        this.empty = other.empty;
    }

    /**
     * @return the boolean value of white
     */
    public boolean getWhite(){
        return white;
    }

    /**
     * @return the boolean value of empty
     */
    public boolean isEmpty(){
        return empty;
    }

    /**
     * @return whether or not the specific object is a king, depending on the subclass
     */
    public abstract boolean getKing();

    /**
     * moveSpaces calculates the spaces a Piece could move to
     * @return a list of all the possible spaces that this Piece could move to
     */
    public abstract ArrayList<String> moveSpaces(String toMove);
}
