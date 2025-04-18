import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tester {

    @Test
    public void moveSpacesMan(){
        Man newMan = new Man("wOne");
        ArrayList<String> toMoveSpaces = newMan.moveSpaces("c3");
        for (String temp : toMoveSpaces){
            System.out.println(temp);
        }
        assertEquals("d4", toMoveSpaces.get(0));
        assertEquals("e5", toMoveSpaces.get(1));
        assertEquals("f6", toMoveSpaces.get(2));
        assertEquals("g7", toMoveSpaces.get(3));
        assertEquals("h8", toMoveSpaces.get(4));
        assertEquals("b5", toMoveSpaces.get(5));
        assertEquals("a6", toMoveSpaces.get(6));
    }

    @Test
    public void moveSpacesKing(){

    }

}
