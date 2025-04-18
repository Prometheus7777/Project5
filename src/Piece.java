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
    private String name;

    public Piece(String name){
        this.name = name;
    }

    public Piece(Piece other){
        this.name = other.name;
    }

    /**
     * @return name member variable
     */
    public String getName(){
        return name;
    }

    public String getColor(){
        StringBuilder sb = new StringBuilder();
        sb.append(name.charAt(0));
        return sb.toString();
    }

    public boolean getMan(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            sb.append(name.charAt(i));
        }
        sb.delete(0, 0);
        if (sb.toString().equals("Man")){
            return true;
        }
        return false;
    }

    /**
     * moveSpaces calculates the spaces a Piece could move to
     * @return a list of all the possible spaces that this Piece could move to
     */
    public abstract ArrayList<String> moveSpaces(String toMove, String moveTo);

    /**
     * moveSpaces calculates the spaces a Piece could move to,
     * and then removes previously calculated spaces that are impossible to move to, for example,
     * spaces already containing a piece when playing checkers, or spaces blocked by a piece when playing chess
     * @return a list of all the possible spaces that this Piece could move to
     */
    //public abstract ArrayList<String> moveSpaces(ArrayList<Integer> occupiedSpaces);
}
