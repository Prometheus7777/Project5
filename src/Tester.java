import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tester {
    public static void main(String[] args){
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                System.out.print("|_");
//            }
//            System.out.print("|\n");
//        }

        Board checkers = new Board(false);
        checkers.printBoard();
    }

    @Test
    public void moveSpacesMan(){
        Man newMan = new Man("wOne");
        ArrayList<String> toMoveSpaces1 = newMan.moveSpaces("c3");
        assertEquals("d4", toMoveSpaces1.get(0));
        assertEquals("b4", toMoveSpaces1.get(1));

        ArrayList<String> toMoveSpaces2 = newMan.moveSpaces("a1");
        assertEquals("b2", toMoveSpaces2.get(0));

        ArrayList<String> toMoveSpaces3 = newMan.moveSpaces("h8");
        assertEquals(0, toMoveSpaces3.size());

        ArrayList<String> toMoveSpaces4 = newMan.moveSpaces("h7");
        assertEquals("g8", toMoveSpaces4.get(0));

        Man secondMan = new Man("bOne");
        ArrayList<String> toMoveSpaces5 = secondMan.moveSpaces("c3");
        assertEquals("b2", toMoveSpaces5.get(0));
        assertEquals("d2", toMoveSpaces5.get(1));

        ArrayList<String> toMoveSpaces6 = secondMan.moveSpaces("a1");
        assertEquals(0, toMoveSpaces6.size());

        ArrayList<String> toMoveSpaces7 = secondMan.moveSpaces("h8");
        assertEquals("g7", toMoveSpaces7.get(0));

        ArrayList<String> toMoveSpaces8 = secondMan.moveSpaces("h7");
        assertEquals("g6", toMoveSpaces8.get(0));
    }

    @Test
    public void moveSpacesKing(){
        Man newMan = new Man("wOne");
        King newKing = new King(newMan);

        ArrayList<String> toMoveSpaces1 = newKing.moveSpaces("c3");
        assertEquals("d4", toMoveSpaces1.get(0));
        assertEquals("b4", toMoveSpaces1.get(1));
        assertEquals("b2", toMoveSpaces1.get(2));
        assertEquals("d2", toMoveSpaces1.get(3));

        ArrayList<String> toMoveSpaces2 = newKing.moveSpaces("a1");
        assertEquals("b2", toMoveSpaces2.get(0));

        ArrayList<String> toMoveSpaces3 = newKing.moveSpaces("d8");
        assertEquals("c7", toMoveSpaces3.get(0));
        assertEquals("e7", toMoveSpaces3.get(1));
    }

}
