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
        boolean undo = false;
        boolean validInput = true;

        undos.add(checkers);
        undos.getFirst().printBoard();
        while (playing){

            if (!undo){
                System.out.println("Enter in the board space of the piece you want to move, or \"undo\" to undo the last move: ");
            }
            else{
                System.out.println("Enter in the board space of the piece you want to move, \"undo\" to undo the last move, or \"redo\" to redo the last undo: ");
            }
            String toMove = in.next();

            if (toMove.equalsIgnoreCase("undo")){
                if (!undos.isEmpty()){
                    redos.push(undos.pop());
                    redos.getFirst().printBoard();
                    undo = true;
                }
            }
            else if (toMove.equalsIgnoreCase("redo")){
                if (!redos.isEmpty()){
                    undos.push(redos.pop());
                    undos.getFirst().printBoard();
                    if (redos.isEmpty()){
                        undo = false;
                    }
                }
            }
            else {
                System.out.println("Enter in the board space of the space you want to move to: ");
                String moveTo = in.next();
                try {
                    undos.push(undos.getFirst().movePiece(toMove, moveTo));
                }
                catch (IllegalArgumentException e){
                    System.out.println("Invalid input!");
                    validInput = false;
                }
                if (validInput){
                    redos.clear();
                    undo = false;
                }
                undos.getFirst().printBoard();
            }
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
