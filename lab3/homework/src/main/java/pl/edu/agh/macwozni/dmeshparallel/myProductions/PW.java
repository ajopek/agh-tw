package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class PW extends AbstractProduction<Vertex> {
    public PW(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        Vertex t1 = new Vertex();
        if(s.getLeft() != null) {
            System.out.println("Cannot apply PW on vertex that has left neighbour");
            System.exit(1);
        } else {
            s.setLeft(t1);
        }
        return t1;
    }
}
