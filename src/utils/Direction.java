package utils;
public class Direction {
    private int degrees;

    public Direction(int degrees) {
        this.degrees = ((degrees % 360) + 360) % 360; // normalize
    }

    public int getDegrees() { return degrees; }
}