import java.util.ArrayList;

/**
 * The King class extends the Piece class and is one of the two types of pieces used in checkers,
 * King is only implemented when a Man reaches the back rank of the board
 */
public class King extends Piece {

    public King (Piece other){
        super(other);
    }

    public boolean getKing(){
        return true;
    }
    /**
     * moveSpaces calculates the spaces a King could move to if there are no possible jumps
     * @return a list of all the possible spaces that this King could move to
     */
    public ArrayList<String> moveSpaces(String toMove){
        ArrayList<ArrayList> spaces = new ArrayList<>();
        ArrayList<String> spacesToMove = new ArrayList<>();
        int[] tempCoords = Board.toCoordinates(toMove);
        int x = tempCoords[0];
        int y = tempCoords[1];

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

        for (ArrayList<Integer> coords : spaces){
            spacesToMove.add(Board.toBoardSpace(coords.get(0), coords.get(1)));
        }

        return spacesToMove;
    }

    /**
     * moveSpaces calculates the space a King could move to if there is a possible jump
     * @return a list of all the possible spaces that this King could move to
     */
    public String moveSpaces(String toMove, String moveTo){
        //TODO: Implement this method
        int[] coords = Board.toCoordinates(toMove);
        return "";
    }
}
