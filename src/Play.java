import java.util.*;

public class Play {
    private Deque<Board> undos;
    private Deque<Board> redos;

    /**
     * Initializes the undos and redos "Stacks"
     */
    public Play(){
        undos = new LinkedList<>();
        redos = new LinkedList<>();
    }

    public void playCheckers(){
        Board checkers = new Board(false);
        Scanner in = new Scanner(System.in);
        //TODO: Make a loop that asks for new moves, adds the boards to the stacks based on inputs,
        //and eventually checks for game end
        boolean playing = true;

        checkers.printBoard();
        while (playing){
            System.out.println("Enter in the board space of the piece you want to move: ");
            String toMove = in.next();
            System.out.println("Enter in the board space of the space you want to move to: ");
            String moveTo = in.next();
            undos.add(checkers.movePiece(toMove, moveTo));
            undos.getLast().printBoard();
        }
        //Testing the static methods
//        for (int i = 1; i < 9; i++) {
//            for (int j = 1; j < 9; j++) {
//                System.out.println(Board.toBoardSpace(i, j));
//            }
//        }
//        for (int i = 1; i < 9; i++) {
//            for (int j = 1; j < 9; j++) {
//                String boardSpace = Board.toBoardSpace(i, j);
//                int[] coordinates = Board.toCoordinates(boardSpace);
//                System.out.print(coordinates[0]);
//                System.out.println(coordinates[1]);
//            }
//        }
    }

    public void playChess(){

    }
}
