import java.util.*;

public class Play {
    /**
     * movesList is used as a stack, and keeps a record of the Board objects (moves) made while playing checkers
     */
    private Deque<Board> movesList;

    /**
     * undos is used as a stack, and keeps a record of Board objects (moves) that have been undone
     */
    private Deque<Board> undos;

    /**
     * Initializes the undos and redos as LinkesLists, they are used as stacks
     */
    public Play(){
        movesList = new LinkedList<>();
        undos = new LinkedList<>();
    }

    /**
     * Goes through the logic of playing the checkers game
     */
    public void playCheckers(){
        Board checkers = new Board();
        Scanner in = new Scanner(System.in);
        boolean playing = true;
        boolean first = true;

        checkers.printBoard();
        movesList.push(checkers);
        while (playing){
            //Based on what last happened, output a message to prompt the user for input
            if (first){
                System.out.println("Enter in the board space of the piece you want to move: ");
            }
            else if (!undos.isEmpty()) {
                System.out.println("Enter in the board space of the piece you want to move, \"undo\" to undo the last move, or \"redo\" to redo the last undo: ");
            }
            else {
                System.out.println("Enter in the board space of the piece you want to move, or \"undo\" to undo the last move: ");
            }
            String toMove = in.next();//Reading in the user's input

            //Undoing the most recent move
            if ((toMove.equalsIgnoreCase("undo")) && !first){
                if (!movesList.isEmpty()){
                    undos.push(movesList.pop());//Adding the most recent move to the undos and removing it from the movesList
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
            //Redoing the most recent undo
            else if ((toMove.equalsIgnoreCase("redo")) && !first){
                if (!undos.isEmpty()){
                    movesList.push(undos.pop());//Adding the most recent undo to the movesList and removing it from the undos
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
                String moveTo = in.next();//Getting the board space to move to
                if (first){
                    try {//Checking for valid input
                        movesList.push(checkers.movePiece(toMove, moveTo));//Adding a new Board object to movesList,
                        //based on the user's input and the most recent Board
                        first = false;
                        movesList.getFirst().printBoard();
                    }
                    catch (IllegalArgumentException | NullPointerException e){
                        System.out.println("Illegal move!");
                        movesList.getFirst().printBoard();
                    }
                }
                else {
                    try {//Checking for valid input
                        movesList.push(movesList.getFirst().movePiece(toMove, moveTo));//Adding a new Board object to movesList,
                    }
                    //based on the user's input and the most recent Board
                    catch (IllegalArgumentException | NullPointerException e){
                        System.out.println("Illegal move!");
                        validInput = false;
                    }
                    if (validInput){
                        undos.clear();//Clearing the undos list if a new move has been made
                    }
                    movesList.getFirst().printBoard();
                    if (movesList.getFirst().gameOver()){//If the game is over print out a message
                        playing = false;//And end the while loop
                        if (movesList.getFirst().getWhite()){
                            System.out.println("Game over! Black is victorious!");
                        }
                        else {
                            System.out.println("Game over! White is victorious!");
                        }
                    }
                }
            }
        }
    }
}
