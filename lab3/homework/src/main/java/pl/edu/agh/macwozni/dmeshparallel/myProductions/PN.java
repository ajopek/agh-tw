package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class PN extends AbstractProduction<Vertex> {
    public PN(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        Vertex t1 = new Vertex();
        if(s.getTop() != null) {
            System.out.println("Cannot apply PW on vertex that has top neighbour");
            System.exit(1);
        } else {
            s.setTop(t1);
        }
        return t1;
    }
}
