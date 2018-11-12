package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.myProductions.*;
import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.mesh.GraphDrawer;
import pl.edu.agh.macwozni.dmeshparallel.parallelism.BlockRunner;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class Executor extends Thread {
    
    private final BlockRunner runner;
    
    public Executor(BlockRunner _runner){
        this.runner = _runner;
    }

    @Override
    public void run() {
        int N = 10;

        PDrawer drawer = new GraphDrawer();
        //PI
        Vertex s = new Vertex();
        boolean left = true;
        Vertex current = s;

        //PW
        for (int j = 0; j < N-1; j++) {
            PW pw = new PW(current, drawer);
            this.runner.addThread(pw);
            //start threads
            this.runner.startAll();
            current = current.getLeft();
        }

        left = false;

        for(int i = 0; i < N-1; i++){
            PN pn = new PN(current, drawer);
            this.runner.addThread(pn);
            //start threads
            this.runner.startAll();
            current = current.getTop();

            if(left) {
                //PW
                for (int j = 1; j < N; j++) {
                    PW pw = new PW(current, drawer);
                    this.runner.addThread(pw);
                    //start threads
                    this.runner.startAll();
                    current = current.getLeft();
                }
            } else {
                //PE
                for (int k = 1; k < N; k++) {
                    PE pe = new PE(current, drawer);
                    this.runner.addThread(pe);
                    //start threads
                    this.runner.startAll();
                    current = current.getRight();
                }
            }

            left = !left;
        }
        drawer.draw(current);
    }
}
