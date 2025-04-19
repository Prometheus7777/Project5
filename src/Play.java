import java.util.*;

public class Play {
    private Deque<Board> movesList;
    private Deque<Board> undos;

    /**
     * Initializes the undos and redos "Stacks"
     */
    public Play(){
        movesList = new LinkedList<>();
        undos = new LinkedList<>();
    }

    public void playCheckers(){
        Board checkers = new Board(false);
        Scanner in = new Scanner(System.in);
        boolean playing = true;
        boolean first = true;

        checkers.printBoard();
        movesList.push(checkers);
        while (playing){
            if (first){
                System.out.println("Enter in the board space of the piece you want to move: ");
            }
            else {
                System.out.println("Enter in the board space of the piece you want to move, \"undo\" to undo the last move, or \"redo\" to redo the last undo: ");
            }
            String toMove = in.next();

            if ((toMove.equalsIgnoreCase("undo")) && !first){
                if (!movesList.isEmpty()){
                    undos.push(movesList.pop());
                    if (!movesList.isEmpty()){
                        movesList.getFirst().printBoard();
                    }
                    else {
                        movesList.push(undos.pop());
                        System.out.println("Illegal move!");
                        movesList.getFirst().printBoard();
                    }
                }
            }
            else if ((toMove.equalsIgnoreCase("redo")) && !first){
                if (!undos.isEmpty()){
                    movesList.push(undos.pop());
                    movesList.getFirst().printBoard();
                }
                else {
                    System.out.println("Illegal move!");
                    movesList.getFirst().printBoard();
                }
            }
            else {
                boolean validInput = true;
                System.out.println("Enter in the board space of the space you want to move to: ");
                String moveTo = in.next();
                if (first){
                    try {
                        movesList.push(checkers.movePiece(toMove, moveTo));
                        first = false;
                        movesList.getFirst().printBoard();
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Illegal move!");
                        movesList.getFirst().printBoard();
                    }
                }
                else {
                    try {
                        movesList.push(movesList.getFirst().movePiece(toMove, moveTo));
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Illegal move!");
                        validInput = false;
                    }
                    if (validInput){
                        undos.clear();
                    }
                    movesList.getFirst().printBoard();
                }
            }
        }

        /*
        Board checkers = new Board(false);
        Scanner in = new Scanner(System.in);
        boolean playing = true;
        boolean undo = false;
        boolean first = true;

        checkers.printBoard();
        while (playing){
            if (first){
                System.out.println("Enter in the board space of the piece you want to move: ");
            }
            else if (!undo){
                System.out.println("Enter in the board space of the piece you want to move, or \"undo\" to undo the last move: ");
            }
            else {
                System.out.println("Enter in the board space of the piece you want to move, \"undo\" to undo the last move, or \"redo\" to redo the last undo: ");
            }
            String toMove = in.next();

            if ((toMove.equalsIgnoreCase("undo")) && !first){
                if (!movesList.isEmpty()){
                    undos.push(movesList.pop());
                    undos.getFirst().printBoard();
                    undo = true;
                }
            }
            else if ((toMove.equalsIgnoreCase("redo")) && !first){
                if (!undos.isEmpty()){
                    movesList.push(undos.pop());
                    movesList.getFirst().printBoard();
                    if (undos.isEmpty()){
                        undo = false;
                    }
                }
            }
            else {
                boolean validInput = true;
                System.out.println("Enter in the board space of the space you want to move to: ");
                String moveTo = in.next();
                if (first){
                    try {
                        movesList.push(checkers.movePiece(toMove, moveTo));
                        first = false;
                        movesList.getFirst().printBoard();
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Invalid input!");
                    }
                }
                else {
                    try {
                        movesList.push(movesList.getFirst().movePiece(toMove, moveTo));
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Invalid input!");
                        validInput = false;
                    }
                    if (validInput){
                        undos.clear();
                        undo = false;
                    }
                    movesList.getFirst().printBoard();
                }
            }
        }
         */
    }

    public void playChess(){

    }
}
