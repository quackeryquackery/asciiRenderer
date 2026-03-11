package org.example;

public class Lines {

    Points a;
    Points b;
    double xDelta;
    double yDelta;
    double zDelta;

    public Lines(Points a, Points b) {
        this.a = a;
        this.b = b;
    }

    public Points returnDelta() {
        xDelta = b.getX() - a.getX();
        yDelta = b.getY() - a.getY();
        zDelta = b.getZ() - a.getZ();
        return new Points(xDelta, yDelta, zDelta);
    }

    public double distance() {
        return Math.sqrt(xDelta * xDelta + yDelta * yDelta + zDelta + zDelta);
    }
    
    public double distanceToPoint(Points curr) {
        Points ap = a.deltaTo(curr);
        Points ab = this.returnDelta();
        if (ab.length() == 0) {
            // System.out.println("ab length is 0");
            return curr.distanceToPoint(b);
        }
        double project = ap.dot(ab) / (ab.length() * ab.length());
        if (project > 1) {
            return curr.distanceToPoint(b);
        }
        else if (project < 0) {
            return curr.distanceToPoint(a);
        }
        return curr.distance(a.getX() + ab.getX() * project, a.getY() + ab.getY() * project, a.getZ() + ab.getZ() * project);
    }

    public Points getA() {
        return a;
    }

    public Points getB() {
        return b;
    }
    // if the point changes, return delta will change too, no need to update Line with the new point coordinates

    @Override
    public String toString() {
        return a.toString() + ", " + b.toString();
    }
}