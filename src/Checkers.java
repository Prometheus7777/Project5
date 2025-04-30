import java.util.Scanner;

/**
 * The Checkers class create a new Board object with a chess value of false,
 * and then calls the play method on that board object
 */
public class Checkers {
    /**
     * Plays the game of checkers by asking the user for input using text, is a two player game and does not support playing the computer
     */
    public static void main(String[] args){
        Play checkers = new Play();
        Scanner in = new Scanner(System.in);
        boolean running = true;
        System.out.println("Welcome to checkers! In this version of the game you must enter the coordinates of the piece you would like to move, and of the board space you would like to move to.");
        System.out.println("Jumps are always forced, which means if a player can make a jump or double jump, they must take that move.");
        System.out.println("The white pieces are represented by '\u03B8' and 'K', while the black pieces are represented by 'o' and 'k.'");
        System.out.println("Good luck!");
        while (running){
            checkers.playCheckers();
            boolean validInput = false;
            while (!validInput){
                System.out.println("Would you like to play again? Enter yes or no: ");
                String answer = in.next();
                if ((!answer.equalsIgnoreCase("y")) && (!answer.equalsIgnoreCase("yes"))){
                    running = false;
                    validInput = true;
                }
                else if ((!answer.equalsIgnoreCase("n")) && (!answer.equalsIgnoreCase("no"))){
                    System.out.println("Invalid Input!");
                }
                else {
                    validInput = true;
                }
            }
        }
    }
}
