//Skeleton implementation of an Alley class  using passing-the-baton
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Sep 26, 2022

public class BatonAlley extends Alley {
    int nUp, nDown; //Number of cars going up/down
    Semaphore e, upSem, downSem;
    int delayedUp, delayedDown; //Number of waiting cars up/down

    protected BatonAlley() {
         nUp = 0; nDown = 0;
         e = new Semaphore(1);
         upSem = new Semaphore(0);
         downSem = new Semaphore(0);
         delayedUp = 0; delayedDown = 0;
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        if (no < 5) {
            e.P();
            if(nUp > 0){
                delayedDown++;
                e.V();
                downSem.P();
            }
            nDown++;

            if(delayedDown > 0 && nUp == 0) {
                delayedDown--;
                downSem.V();
            } else {
                e.V();
            }
        } else {
            e.P();
            if(nDown > 0) {
                delayedUp++;
                e.V();
                upSem.P();
            }
            nUp++;
            e.V();
        }
     }
    /* Register that car no. has left the alley */
    public void leave(int no)  {
        try {
            if (no < 5) {
                e.P();
                nDown--;
                if(delayedUp > 0 && nDown == 0 && nUp == 0) {
                    delayedUp--;
                    upSem.V();
                } else{
                    e.V();
                }
            } else {
                e.P();
                nUp--;
                if(delayedDown > 0 && nUp == 0){
                    delayedDown--;
                    downSem.V();
                } else{
                    e.V();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
