package utils;
public class Velocity {
    private Speed speed, speedX, speedY;
    private Direction direction;

    // Default constructor
    public Velocity() {
        this.speed = new Speed(0);
        this.direction = new Direction(0);
        updateComponents();
    }

    // Constructor with speed and direction
    public Velocity(Speed speed, Direction direction) {
        this.speed = speed;
        this.direction = direction;
        updateComponents();
    }

    public Speed getSpeed() {
        return speed;
    }

    public Speed getSpeedX() {
        return speedX;
    }

    public Speed getSpeedY() {
        return speedY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
        updateComponents();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        updateComponents();
    }

    // Reverse entire direction (add 180 degrees)
    public void reverse() {
        direction = new Direction(direction.getDegrees() + 180);
        updateComponents();
    }

    // Reverse only X direction
    public void reverseX() {
        speedX = new Speed(-speedX.getValue());
        updateFromComponents();
    }

    // Reverse only Y direction
    public void reverseY() {
        speedY = new Speed(-speedY.getValue());
        updateFromComponents();
    }

    // --- Helper methods ---

    // Convert speed + direction -> components
    private void updateComponents() {
        double radians = Math.toRadians(direction.getDegrees());
        int x = (int) Math.round(speed.getValue() * Math.cos(radians));
        int y = (int) Math.round(speed.getValue() * Math.sin(radians));

        speedX = new Speed(x);
        speedY = new Speed(y);
    }

    // Convert components -> speed + direction
    private void updateFromComponents() {
        double x = speedX.getValue();
        double y = speedY.getValue();

        double newSpeed = Math.sqrt(x * x + y * y);
        int newDirection = (int) Math.round(Math.toDegrees(Math.atan2(y, x)));

        this.speed = new Speed(newSpeed);
        this.direction = new Direction(newDirection);
    }
}