package pl.edu.agh.macwozni.dmeshparallel.mesh;

import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class GraphDrawer implements PDrawer<Vertex> {

    @Override
    public void draw(Vertex v) {
        System.out.println("Graph");
        Vertex current = v;
        StringBuilder graph = new StringBuilder();
        boolean end = false;
        while (!end) {
            int numOfVert = 1;
            boolean left = false;
            while(current.getLeft() != null) {
                numOfVert += 1;
                current = current.getLeft();
                left = true;
            }
            if(left) {
                for(int i =0; i < numOfVert; i++){
                    graph.append("[]");
                    if(i != numOfVert - 1) graph.append("--");
                }
                if(current.getBottom() != null) {
                    graph.append("\n | \n");
                    current = current.getBottom();
                } else {
                    end = true;
                }
            } else {
                while(current.getRight() != null) {
                    current = current.getRight();
                    numOfVert += 1;
                }
                for(int i =0; i < numOfVert; i++){
                    graph.append("[]");
                    if(i != numOfVert - 1) graph.append("--");
                }
                if(current.getBottom() != null) {
                    graph.append("\n");
                    int numOfSpaces = numOfVert * 4 - 4;
                    for(int i =0; i<numOfSpaces; i++) {
                        graph.append(" ");
                    }
                    graph.append("|\n");
                    current = current.getBottom();
                } else {
                    end = true;
                }
            }
        }
        System.out.println(graph.toString());
    }
}
