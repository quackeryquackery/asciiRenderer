package org.example;

import java.util.ArrayList;

public class ThreeDShape extends Animator {

    ArrayList<Points> pointList;
    ArrayList<Lines> lineList;
    double[][][] threeDim;
    int lower;
    double yaw;
    double pitch;
    double roll;
    int time = 0;
    int flipper;
    int rotater;
    // 30 degrees per second =  1/2 degree per tick
    // 360 degrees = 2pi
    // 1 degree = 2pi/360 = pi/180

    // instnatiates a triangular prism with size half the height or length, whichever is lower
    // later we can make a size variable that defines its size if we want
    public ThreeDShape(int rows, int cols) {
        super(rows, cols);
        lower = Math.min((int) (rows * HOVERLRATIO), cols); // essentially acts as the scaling factor
        threeDim = new double[rows][cols][lower];
        System.out.println(rows + " " + cols + " " + lower);
        pointList = new ArrayList<>();
        lineList = new ArrayList<>();
        flipper = 1;
        rotater = 1;
    }

    // update is where changes happen to the d[][], which will in turn change the display
    // we will test whether a point on the 3d grid is changed by using vertex equations
    @Override
    protected void update(double[][] d) {
        threeDim = new double[rows][cols][lower];

        switch (rotater) {
            case 1 -> rotate();
            case 3 -> rotateSeq();
        }
        // line drawing method: to test whether a double point at x y z is near a line
        // if u = AB and v = AP, where P is the point in question and AB is the line

        // 3 rows:  0 1 2 > -1 0 1
        // 4 rows:  0 1 2 3 > -1.5, -0.5, 0.5, 1.5

        // centers mtrix at origin
        double radius = 1.5;

        double xCenter = (rows - 1.0) / 2;
        double yCenter = (cols - 1.0) / 2;
        double zCenter = (lower - 1.0) / 2;

        for (Lines curr : lineList) {

            Points a = curr.getA();
            Points b = curr.getB();

            double minX = Math.min(a.getX(), b.getX()) - radius;
            double maxX = Math.max(a.getX(), b.getX()) + radius;
            double minY = Math.min(a.getY(), b.getY()) - radius;
            double maxY = Math.max(a.getY(), b.getY()) + radius;
            double minZ = Math.min(a.getZ(), b.getZ()) - radius;
            double maxZ = Math.max(a.getZ(), b.getZ()) + radius;

            int iStart = (int)Math.floor(minX / HOVERLRATIO + xCenter);
            int iEnd   = (int)Math.ceil (maxX / HOVERLRATIO + xCenter);
            int jStart = (int)Math.floor(minY + yCenter);
            int jEnd   = (int)Math.ceil (maxY + yCenter);
            int kStart = (int)Math.floor(minZ + zCenter);
            int kEnd   = (int)Math.ceil (maxZ + zCenter);

            iStart = Math.max(0, iStart);
            iEnd   = Math.min(rows - 1, iEnd);
            jStart = Math.max(0, jStart);
            jEnd   = Math.min(cols - 1, jEnd);
            kStart = Math.max(0, kStart);
            kEnd   = Math.min(lower - 1, kEnd);

            for (int i = iStart; i <= iEnd; i++) {
                double x = (i - xCenter) * HOVERLRATIO;

                for (int j = jStart; j <= jEnd; j++) {
                    double y = j - yCenter;

                    for (int k = kStart; k <= kEnd; k++) {
                        double z = k - zCenter;

                        double value = curr.distanceToPoint(new Points(x, y, z));

                        if (value < radius) {
                            threeDim[i][j][k] += (2 - value) / 8;
                        }
                    }
                }
            }
        }
        threeToTwo(threeDim, d);
        switch (flipper) {
            case 1 -> flip(d);
            case 3 -> flip3(d);
        }
        time++;
    }

    public void flip3(double[][] d) {
        double timeModulated = time % 600;
        if (timeModulated < 270) {
            // d[i][j] = d[i][j]
        }
        else if (timeModulated < 300) {
            int x = d.length;
            int y = d[0].length;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    d[i][j] = 1 * (timeModulated - 270) / 30 - d[i][j] * (timeModulated - 285) / 15;
                }
            }
        }
        else if (timeModulated < 570) {
            flip(d);
            // d[i][j] = 1 - d[i][j]
        }
        else if (timeModulated < 600) {
            int x = d.length;
            int y = d[0].length;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    d[i][j] = 1 * (600 - timeModulated) / 30 - d[i][j] * (585 - timeModulated) / 15;
                }
            }
        }
    }

    public void rotate() {
        yaw = 0.05 * Math.sin(time * Math.PI / 360);
        pitch = 0.05 * Math.sin(Math.sqrt(2) * time * Math.PI / 360);
        roll = 0.05 * Math.cos(Math.sqrt(3) * time * Math.PI / 360);
        updatePoints(pointList, yaw, pitch, roll);
    }

    public void rotateSeq() {
        if (time % 270 < 90) {
            yaw = Math.PI / 90;
            pitch = 0;
            roll = 0;
        }
        else if (time % 270 < 180) {
            yaw = 0;
            pitch = Math.PI / 90;
            roll = 0;
        }
        else if (time % 270 < 270) {
            yaw = 0;
            pitch = 0;
            roll = Math.PI / 90;
        }
        updatePoints(pointList, yaw, pitch, roll);
    }

    public void setRotation(int a) {
        rotater = a;
    }

    public void setFlip(int a) {
        flipper = a;
    }
    // heres where all the shapes are:

    public void tetrahedron() {
        // 4 points, all connected
        int pointsAlready = pointList.size();
        pointList.add(new Points(lower / 4, lower / 4, lower / 4));
        pointList.add(new Points(-lower / 4, -lower / 4, lower / 4));
        pointList.add(new Points(lower / 4, -lower / 4, -lower / 4));
        pointList.add(new Points(-lower / 4, lower / 4, -lower / 4));
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                Lines li = new Lines(pointList.get(pointsAlready + i), pointList.get(pointsAlready + j));
                lineList.add(li);
            }
        }
    }
    
    public void cube() {
        // 8 points, 12 lines
        // each connects to points 1 heming distance away
        // 000 0
        // 001 1
        // 010 2
        // 011 3
        // 100 4
        // 101 5
        // 110 6
        // 111 7
        int pointsAlready = pointList.size();
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                for (int k = -1; k <= 1; k += 2) {
                    pointList.add(new Points(i * lower / 4, j * lower / 4, k * lower / 4));
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 1) {
                lineList.add(new Lines(pointList.get(pointsAlready + i), pointList.get(pointsAlready + i - 1)));
            }
            if (i % 4 >= 2) {
                lineList.add(new Lines(pointList.get(pointsAlready + i), pointList.get(pointsAlready + i - 2)));
            }
            if (i >= 4) {
                lineList.add(new Lines(pointList.get(pointsAlready + i), pointList.get(pointsAlready + i - 4)));
            }
        }
    }

    public void octohedron() {
        // 6 points, 12 lines still
        // -1 -1 0
        // -1 1 0
        // 1 -1 0
        // 1 1 0
        // 1 2, 1 3, 2 4, 3 4
        // bottom two binds to the top four
        // 0 0 1
        // 0 0 -1
        int pointsAlready = pointList.size();
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                pointList.add(new Points(j * lower / 4, k * lower / 4, 0));
            }
        }
        lineList.add(new Lines(pointList.get(pointsAlready + 0), pointList.get(pointsAlready + 1)));
        lineList.add(new Lines(pointList.get(pointsAlready + 0), pointList.get(pointsAlready + 2)));
        lineList.add(new Lines(pointList.get(pointsAlready + 1), pointList.get(pointsAlready + 3)));
        lineList.add(new Lines(pointList.get(pointsAlready + 2), pointList.get(pointsAlready + 3)));
        for (int k = -1; k <= 1; k += 2) {
            Points poi = new Points(0, 0, k * Math.sqrt(2) * lower / 4);
            pointList.add(poi);
            for (int i = 0; i < 4; i++) {
                lineList.add(new Lines(pointList.get(pointsAlready + i), poi));
            }
        }
    }

    public void testShape() {
        int pointsAlready = pointList.size();
        pointList.add(new Points(HOVERLRATIO * rows / 2, cols / 2, 0));
        pointList.add(new Points(HOVERLRATIO * rows / 2, - cols / 2, 0));
        pointList.add(new Points(- HOVERLRATIO * rows / 2, cols / 2, 0));
        pointList.add(new Points(- HOVERLRATIO * rows / 2, - cols / 2, 0));
        for (int i = pointsAlready; i < pointsAlready + 3; i++) {
            lineList.add(new Lines(pointList.get(i), pointList.get(i + 1)));
        }
        lineList.add(new Lines(pointList.get(pointsAlready), pointList.get(pointsAlready + 3)));
    }

    // note: test points are mainly just for testing if the program is properly centered.
    // during rotations, the points are too far that they exit the 3d array
    public void testPoints() {
        int pointsAlready = pointList.size();
        pointList.add(new Points(0, 0, 0));
        pointList.add(new Points(HOVERLRATIO * rows / 2, cols / 2, 0));
        pointList.add(new Points(HOVERLRATIO * rows / 2, - cols / 2, 0));
        pointList.add(new Points(- HOVERLRATIO * rows / 2, cols / 2, 0));
        pointList.add(new Points(- HOVERLRATIO * rows / 2, - cols / 2, 0));
        pointList.add(new Points(0, 0, 0));
        for (int i = pointsAlready; i < pointsAlready + 5; i++) {
            lineList.add(new Lines(pointList.get(i), pointList.get(i)));
        }
    }
}
