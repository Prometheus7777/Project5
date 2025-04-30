import java.util.ArrayList;

/**
 * The Man class extends the Piece class and is one of the two types of pieces used in checkers
 */
public class Man extends Piece {

    public Man(boolean white, boolean empty){
        super(white, empty);
    }

    public Man(Piece other){
        super(other);
    }

    /**
     * @return false because any Man object is never a King
     */
    public boolean getKing(){
        return false;
    }

    /**
     * moveSpaces calculates the spaces a Man could move to if there are no possible jumps
     * @return a list of all the possible spaces that this Man could move to
     */
    public ArrayList<String> moveSpaces(String toMove){
        ArrayList<ArrayList<Integer>> spaces = new ArrayList<>();
        ArrayList<String> spacesToMove = new ArrayList<>();
        int[] tempCoords = Board.toCoordinates(toMove);
        int x = tempCoords[0];
        int y = tempCoords[1];
        if (this.getWhite()){
            if ((((x + 1) <= 8) && ((x + 1) >= 1)) && (((y + 1) <= 8) && ((y + 1) >= 1))){
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(x + 1);
                coordinates.add(y + 1);
                spaces.add(coordinates);
            }
            if ((((x - 1) <= 8) && ((x - 1) >= 1)) && (((y + 1) <= 8) && ((y + 1) >= 1))){
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(x - 1);
                coordinates.add(y + 1);
                spaces.add(coordinates);
            }
        }
        else {
            if ((((x - 1) <= 8) && ((x - 1) >= 1)) && (((y - 1) <= 8) && ((y - 1) >= 1))){
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(x - 1);
                coordinates.add(y - 1);
                spaces.add(coordinates);
            }
            if ((((x + 1) <= 8) && ((x + 1) >= 1)) && (((y - 1) <= 8) && ((y - 1) >= 1))){
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(x + 1);
                coordinates.add(y - 1);
                spaces.add(coordinates);
            }
        }
        for (ArrayList<Integer> coords : spaces){
            spacesToMove.add(Board.toBoardSpace(coords.get(0), coords.get(1)));
        }
        return spacesToMove;
    }
}
