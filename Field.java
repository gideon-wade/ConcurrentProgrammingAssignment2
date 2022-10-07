//Prototype implementation of Field class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Sep 26, 2022

import java.util.ArrayList;
import java.util.List;

public class Field {
    private Semaphore[][] sems = new Semaphore[11][12];

    public Field() {
        for (int i = 0; i < sems.length; i++) {
            for (int j = 0; j < sems[i].length; j++) {
                sems[i][j] = new Semaphore(1);
            }
        }
    }

    /* Block until car no. may safely enter tile at pos */
    public void enter(int no, Pos pos) throws InterruptedException {
        sems[pos.row][pos.col].P();
    }

    /* Release tile at position pos */
    public void leave(Pos pos) {
        sems[pos.row][pos.col].V();
    }

}
