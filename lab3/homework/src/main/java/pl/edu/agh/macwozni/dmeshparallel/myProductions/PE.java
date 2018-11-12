package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class PE extends AbstractProduction<Vertex> {
    public PE(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex s) {
        Vertex t1 = new Vertex();
        if(s.getRight() != null) {
            System.out.println("Cannot apply PE on vertex that has right neighbour");
            System.exit(1);
        } else {
            s.setRight(t1);
        }
        return t1;
    }
}
