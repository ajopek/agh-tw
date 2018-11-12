package pl.edu.agh.macwozni.dmeshparallel.mesh;

public class Vertex {
    Vertex left = null;
    Vertex right = null;
    Vertex top = null;
    Vertex bottom = null;

    public Vertex() {
    }

    public Vertex getLeft() {
        return left;
    }

    public void setLeft(Vertex left) {
        this.left = left;
        if(left.getRight() == null) {
            left.setRight(this);
        }
    }

    public Vertex getRight() {
        return right;
    }

    public void setRight(Vertex right) {
        this.right = right;
        if(right.getLeft() == null) {
            right.setLeft(this);
        }
    }

    public Vertex getTop() {
        return top;
    }

    public void setTop(Vertex top) {
        this.top = top;
        if(top.getBottom() == null) {
            top.setBottom(this);
        }
    }

    public Vertex getBottom() {
        return bottom;
    }

    public void setBottom(Vertex bottom) {
        this.bottom = bottom;
    }
}
