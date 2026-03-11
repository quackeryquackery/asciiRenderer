package org.example;

public class Points {

    double x;
    double y;
    double z;

    // ease of access so i can just put an int in and not have to cast everything beforehand
    public Points(int x, int y, int z) {
        this.x = (double) x;
        this.y = (double) y;
        this.z = (double) z;
    }

    public Points(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // update x, y, and z coords with new x, y, and z coords
    public void update(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public double dot(Points a) {
        return x * a.getX() + y * a.getY() + z * a.getZ();
    }

    public Points deltaTo(Points a) {
        return new Points(a.getX() - x, a.getY() - y, a.getZ() - z);
    }

    public double distance(double x, double y, double z) {
        double dx = x - this.x;
        double dy = y - this.y;
        double dz = z - this.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceToPoint(Points a) {
        double dx = x - a.getX();
        double dy = y - a.getY();
        double dz = z - a.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void rotate(double yaw, double pitch, double roll) {
        double tempX = x;
        double tempY = y;
        double tempZ = z;
        x = 
        tempX * (Math.cos(pitch) * Math.cos(roll)) +
        tempY * (Math.sin(yaw) * Math.sin(pitch) * Math.cos(roll) - Math.cos(yaw) * Math.sin(roll)) +
        tempZ * (Math.cos(yaw) * Math.sin(pitch) * Math.cos(roll) + Math.sin(yaw) * Math.sin(roll));
        y = 
        tempX * (Math.cos(pitch) * Math.sin(roll)) +
        tempY * (Math.sin(yaw) * Math.sin(pitch) * Math.sin(roll) + Math.cos(yaw) * Math.cos(roll)) +
        tempZ * (Math.cos(yaw) * Math.sin(pitch) * Math.sin(roll) - Math.sin(yaw) * Math.cos(roll));
        z = 
        tempX * (-Math.sin(pitch)) + 
        tempY * (Math.sin(yaw) * Math.cos(pitch)) + 
        tempZ * (Math.cos(yaw) * Math.cos(pitch));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
