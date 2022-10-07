//Attempted implementation of Alley class with multiple cars
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Sep 26, 2022

public class MultiAlley1 extends Alley {
    int up, down;
    Semaphore upSem, downSem;

    protected MultiAlley1() {
        up = 0;   down = 0;
        upSem   = new Semaphore(1);
        downSem = new Semaphore(1);
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        System.out.println();
        System.out.println("-------------------");
        System.out.println("Semaphore up: " + upSem.s);
        System.out.println("Semaphore down: " + downSem.s);
        if (no < 5) {
            System.out.println("----- IN IF -----");
            System.out.println("car: " + no);
            System.out.println("up: " + up);
            System.out.println("down: " + down);
            downSem.P();
            Thread.sleep(1000);
            if (down == 0) upSem.P();    // block for up-going cars
            down++;
            downSem.V();
        } else {
            System.out.println("----- IN ELSE -----");
            System.out.println("car: " + no);
            System.out.println("up: " + up);
            System.out.println("down: " + down);
            upSem.P();
            if (up == 0) downSem.P();    // block for down-going cars
            up++;
            upSem.V();
        }

    }

    /* Register that car no. has left the alley */
    public void leave(int no) {
        if (no < 5) {
            down--;
            if (down == 0) upSem.V();     // enable up-going cars again
        } else {
            up--;
            if (up == 0) downSem.V();    // enable down-going cars again
        }
    }

}
