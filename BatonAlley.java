//Skeleton implementation of an Alley class  using passing-the-baton
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Sep 26, 2022

public class BatonAlley extends Alley {

    int nu, nd; //Number of cars going up/down
    Semaphore e, up, down;
    int du,dd; //Number of waiting cars up/down

    protected BatonAlley() {
         nu = 0; nd = 0;
         e = new Semaphore(1);
         up = new Semaphore(0);
         down = new Semaphore(0);
         du = 0; dd = 0;
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        if (no < 5) {
            e.P();
            nd++;
            if(nu > 0){
                dd++;
                e.V();
                down.P();
            }
            nd++;
            signal();
            //nd--; //Atomic?
            e.P();
            nd--;
            signal();
        } else {
            e.P();
            nu++;
            if(nd > 0) {
                du++;
                e.V();
                up.P();
            }
            nu++;
            signal();
            //nu--; //Atomic?
            e.P();
            //nu--;
            signal();
        }
     }

     public void signal() throws InterruptedException {
        e.P();
         if(nu == 0 && dd > 0){
             dd--;
             down.V();
         }else if(nd == 0 && du > 0) {
             du--;
             up.V();
         }else{
             e.V();
         }
     }
    /* Register that car no. has left the alley */
    public void leave(int no){
        if (no < 5) {
            nd--;
        } else {
            nu--;
        }
    }
}
