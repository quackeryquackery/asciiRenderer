package org.example;

import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    /*
    private static final int LENGTH = 1500;
    private static final int HEIGHT = 1000;
    */
    // about 900 x 900 is the highest
    private static final double FPS = 24;
    protected final double HOVERLRATIO = 2.2000000454130646;

    // private static final int FONTSIZE = 20;
    // computer screen is about 1500 x 1000
    // recommended font size about 12-40 for best results, you can go higher for fun, but lower is dangerous
    // for 900 x 900, 12-40
    // after optimization (bounding boxes), 900 x 900 can go down to size 5
    // 900 900 5
    // 400 500 3
    // lower LENGTH x HEIGHT means you can go lower more safely

    @Override
    public void start(Stage primaryStage) {
        int LENGTH;
        int HEIGHT;
        int FONTSIZE;
        int temp = 0;
        try {
            System.out.println("Beginning questioning");
            Scanner sc = new Scanner(System.in);

            boolean moveOn = false;
            while (!moveOn) {
                System.out.println("Choose a length (0 - 1500):");
                temp = -1;
                try {
                    temp = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Please enter an integer only");
                }
                if (temp > 0 && temp <= 1500) {
                    moveOn = true;
                }
                else {
                    System.out.println("Please choose a length between 0 and 1500");
                }
            }
            LENGTH = temp;

            moveOn = false;
            while (!moveOn) {
                System.out.println("Choose a height (0 - 1000):");
                temp = -1;
                try {
                    temp = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Please enter an integer only");
                }
                if (temp > 0 && temp <= 1000) {
                    moveOn = true;
                }
                else {
                    System.out.println("Please choose a height between 0 and 1500");
                }
            }
            HEIGHT = temp;

            moveOn = false;
            while (!moveOn) {
                System.out.println("Choose a font size:");
                temp = Integer.parseInt(sc.nextLine());
                double ratio = HEIGHT * LENGTH / (temp * temp);
                if (ratio <= 20000) {
                    moveOn = true;
                }
                else {
                    System.out.println("Please choose a larger font size for performance");
                }
            }
            FONTSIZE = temp;

            System.out.println("Dimensions complete! Textboxes being constructed.");
            Text sample = new Text("A");
            sample.setFont(Font.loadFont(getClass().getResourceAsStream("/JetBrainsMono.ttf"), FONTSIZE));
            double fontHeight = sample.getLayoutBounds().getHeight();
            double fontLength = sample.getLayoutBounds().getWidth();

            int cols = (int) (LENGTH / fontLength);
            int rows = (int) (HEIGHT / fontHeight);
            double xOffset = (LENGTH - (cols * fontLength)) / 2;
            double yOffset = (HEIGHT - (rows * fontHeight)) / 2;

            // working on canvas !
            Canvas canvas = new Canvas(LENGTH, HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFont(Font.loadFont(getClass().getResourceAsStream("/JetBrainsMono.ttf"), FONTSIZE));
            gc.setFill(Color.WHITE);
            char[][] t = new char[rows][cols];
            Pane root = new Pane(canvas);

            // length and height determined, make shape
            ThreeDShape a = new ThreeDShape(rows, cols);

            moveOn = false;
            while (!moveOn) {
                System.out.println("Choose a shape:");
                System.out.println("(1) cube");
                System.out.println("(2) tetrahedron");
                System.out.println("(3) octohedron");
                System.out.println("(4) test points");
                String curr = sc.nextLine();
                moveOn = true;
                switch (curr) {
                    case "1" -> a.cube();
                    case "2" -> a.tetrahedron();
                    case "3" -> a.octohedron();
                    case "4" -> a.testPoints();
                    default -> {
                        System.out.println("Please just type the number for the shape you want");
                        moveOn = false;
                    }
                }
            }
            moveOn = false;
            while (!moveOn) {
                System.out.println("Choose a rotation:");
                System.out.println("(1) trigonmetric");
                System.out.println("(2) sequential");
                System.out.println("(3) none");
                String curr = sc.nextLine();
                moveOn = true;
                switch (curr) {
                    case "1" -> a.setRotation(1);
                    case "2" -> a.setRotation(3);
                    case "3" -> a.setRotation(2);
                    default -> {
                        System.out.println("Please just type the number for the shape you want");
                        moveOn = false;
                    }
                }
            }
            moveOn = false;
            while (!moveOn) {
                System.out.println("Choose the coloring:");
                System.out.println("(1) black on white"); 
                System.out.println("(2) white on black");
                System.out.println("(3) alternating");
                String curr = sc.nextLine();
                moveOn = true;
                switch (curr) {
                    case "1" -> a.setFlip(1);
                    case "2" -> a.setFlip(2);
                    case "3" -> a.setFlip(3);
                    default -> {
                        System.out.println("Please just type the number for the shape you want");
                        moveOn = false;
                    }
                }
            }
            moveOn = false;
            while (!moveOn) {
                System.out.println("Choose the gradient:");

                System.out.println("(1) \" .,-:+*#%@\"");
                System.out.println("(2) \" .·◦◌○◎◍◉●\"");
                System.out.println("(3) \" ░▒▓█\"");
                System.out.println("(4) \" ▁▂▃▄▅▆▇█\"");
                System.out.println("(5) \" ▏▎▍▌▋▊▉█\"");
                System.out.println("(6) \" .\\'`^\\\",:;Il!i><~+_-?][}{1)(|\\\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$\"");
                String curr = sc.nextLine();
                moveOn = true;
                switch (curr) {
                    case "1" -> a.setGradient(" .,-:+*#%@");
                    case "2" -> a.setGradient(" .·◦◌○◎◍◉●");
                    case "3" -> a.setGradient(" ░▒▓█");
                    case "4" -> a.setGradient(" ▁▂▃▄▅▆▇█");
                    case "5" -> a.setGradient(" ▏▎▍▌▋▊▉█");
                    case "6" -> a.setGradient(" .\'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$");
                    default -> {
                        System.out.println("Please just type the number for the shape you want");
                        moveOn = false;
                    }
                }
            }
            sc.close();
            System.out.println("Shape made.");


            
            // title bar height is 28 pixels
            // font is about 21.12 height by 9.6 length

            System.out.println("Scene set.");
            Scene scene = new Scene(root, LENGTH, HEIGHT);
            Color col = Color.web("#1e1e1e"); // white
            // set background color
            scene.setFill(col);
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true); // remove this later
            primaryStage.show();
            
            // begin asking questions
            
            // begin animation !
            System.out.println("Starting");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / FPS), e -> {
                a.frame(t);
                gc.clearRect(0, 0, LENGTH, HEIGHT);
                render(gc, t, rows, fontHeight, xOffset, yOffset, FONTSIZE);
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void render(GraphicsContext gc, char[][] t, int rows, double fontHeight, double xOffset, double yOffset, int FONTSIZE) {
        for (int i = 0; i < rows; i++) {
            gc.fillText(new String(t[i]), xOffset, i * fontHeight + yOffset + FONTSIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
