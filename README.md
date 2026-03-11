# asciiRenderer
Small program built using Java and JavaFX capable of animating a rotating polygon in ASCII at a desired window size, font size, and various different sets.

Run program with command:
./gradlew run

Choosable by user:
    Objects:
          Cube
          Tetrahedron
          Octohedron
          Test Points
              (Test Points were only meant for debugging and are prone to clip out of bound)
    Rotations:
        Trigonometric
        Sequential
        None
    Coloring:
        Black on White
        White on Black
        Alternating
    ASCII Gradients:
        " .,-:+*#%@"
        " .·◦◌○◎◍◉●"
        " ░▒▓█"
        " ▁▂▃▄▅▆▇█"
        " ▏▎▍▌▋▊▉█"
        " .\'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"

Recommended:

500 x 500, 16, Cube, Trigonometric, Alternating, " .,-:+*#%@"
200 x 200, 14, Cube, Trigonometric, Alternating, " .·◦◌○◎◍◉●"
Technology:
Use of euler angles, matrix algebra, and trigonometric functions to adjust points.
Use of vector algebra to measure the distance from a line to any given point.
Use of bounding boxes to reduce time complexity.
Use of object-oriented principles.
Use of JavaFX to create new window.
Use of Gradle to set up JavaFX properly



Non-functional Specifications:

Window size can be chosen up to 1500 x 1000 pixels.
Font size can be chosen for any side below a certain limit to ensure enough processing power.
  (Prevents the program from running when (height * length / (font size)^2 > 20000)
