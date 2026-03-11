package org.example;

import java.util.ArrayList;

public abstract class Animator {

    protected int rows;
    protected int cols;
    protected double[][] d;
    protected final double HOVERLRATIO = 2.2000000454130646;
    
    // set of shaders: choose one, comment the rest

    // String shadeString = " .·◦◌○◎◍◉●";
    String shadeString = " .,-:+*#%@";
    // String shadeString = " ▏▎▍▌▋▊▉█";
    // String shadeString = " ▁▂▃▄▅▆▇█";
    // String shadeString = " ░▒▓█";

    char[] shades = shadeString.toCharArray();
    int variety = shades.length;

    // vertex math: have a bunch of points, do vertex calculations on them, nearby lines get freaked, yaya

    protected Animator() {

    }

    protected Animator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        d = new double[rows][cols];
    }

    protected void flip(double[][] two) {
        int x = two.length;
        int y = two[0].length;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                two[i][j] = 1 - two[i][j];
            }
        }
    }

    // this is the updating method that happens each tick
    protected void frame(char[][] t) {
        update(d);
        shade(d, t);
    }

    // assumes rows of three = rows of two and cols of three = cols of two
    // we can make changes to three to two if we want the structure to be opaque or darker in the back
    protected void threeToTwo(double[][][] three, double[][] two) {
        int x = three.length;
        int y = three[0].length;
        int z = three[0][0].length;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                two[i][j] = 0;
                for (int k = 0; k < z; k++) {
                    two[i][j] += three[i][j][k] / (1 + k);
                }
                two[i][j] *= z;
            }
        }
    }

    // goes from double[][] to char
    protected void shade(double[][] d, char[][] t) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double curr = d[y][x];
                t[y][x] = shades[0];
                for (int k = variety - 1; k > 0; k--) {
                    if (curr > (double) k / variety) {
                        t[y][x] = shades[k];
                        break;
                    }
                }
            }
        }
    }

    protected void updatePoints(ArrayList<Points> pointList, double yaw, double pitch, double roll) {
        for (Points curr : pointList) {
            curr.rotate(yaw, pitch, roll);
        }
    }

    public void setGradient(String a) {
        shadeString = a;
        shades = a.toCharArray();
        variety = shades.length;
    }

    // update varies between subclasses, must be overwritten
    protected abstract void update(double[][] d);
}
