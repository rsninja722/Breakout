package utils;
import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

@Invariant({"speed >= 0", "direction >= 0", "direction < 360", "speedX = round(speed * cos(toRadians(direction)))", "speedY = round(speed * sin(toRadians(direction)))"})
public class Velocity {
    private Speed speed, speedX, speedY;
    private Direction direction;

    // No precondition in this case
    @Requires({})
    @Ensures({"speed == 0", "speedX == 0", "speedY == 0", "direction == 0"})
    // initializes all properties to 0 as default values
    public Velocity() { //constructor
    	this.speed = new Speed(0);
        this.direction = new Direction(0);
        this.speedX = new Speed(0);
        this.speedY = new Speed(0);
    }

    @Requires({"speed >= 0", "direction >= 0", "direction < 360"})
    @Ensures({"this.speed == speed", "this.direction == direction", "this.speedX = round(speed * cos(toRadians(direction)))", "this.speedY = round(speed * sin(toRadians(direction)))"})
    // initializes all properties based on the given argument
    public Velocity(Speed speed, Direction direction) { //constructor
        this.speed = speed;
        this.direction = direction;

        double sx = Math.round(speed.getValue() * Math.cos(Math.toRadians(direction.getDegrees())));
        double sy = Math.round(speed.getValue() * Math.sin(Math.toRadians(direction.getDegrees())));

        this.speedX = new Speed(sx);
        this.speedY = new Speed(sy);
    }

    @Requires("speed != null && direction != null")
    @Ensures({"speed >= 0", "result instanceof Speed"})
    // returns the value of speed
    public Speed getSpeed() {
    	return this.speed;
    }

    @Requires("speed != null && direction != null")
    @Ensures({"speedX = round(speed * cos(toRadians(direction)))", "result instanceof Speed"})
    // returns the value of speedX
    public Speed getSpeedX() { // get speed in X direction
    	return this.speedX;
    }

    @Requires("speed != null && direction != null")
    @Ensures({"speedY = round(speed * sin(toRadians(direction)))", "result instanceof Speed"})
    // returns the value of speedY
    public Speed getSpeedY() { // get speed in Y direction
    	return this.speedY;
    }

    @Requires("speed != null && direction != null")
    @Ensures({"direction >= 0", "direction < 360", "result instanceof Direction"})
    // returns the value of direction
    public Direction getDirection() {
    	return this.direction;
    }

    @Requires({"speed >= 0"})
    @Ensures({"this.speed == speed", "speedX = round(speed * cos(toRadians(direction)))", "speedY = round(speed * sin(toRadians(direction)))", "direction == old(direction)"}) 
    // speed value is re-assigned, so speedX and speedY are recomputed, but direction remains the same
    public void setSpeed(Speed speed) {
        this.speed = speed;

        double sx = Math.round(speed.getValue() * Math.cos(Math.toRadians(direction.getDegrees())));
        double sy = Math.round(speed.getValue() * Math.sin(Math.toRadians(direction.getDegrees())));

        this.speedX = new Speed(sx);
        this.speedY = new Speed(sy);
    }

    @Requires({"direction >= 0", "direction < 360"})
    @Ensures({"this.direction == direction", "speedX = round(speed * cos(toRadians(direction)))", "speedY = round(speed * sin(toRadians(direction)))", "speed == old(speed)"})
    // direction value is re-assigned, so speedX and speedY are recomputed, but speed remains the same
    public void setDirection(Direction direction) {
        this.direction = direction;

        double sx = Math.round(speed.getValue() * Math.cos(Math.toRadians(direction.getDegrees())));
        double sy = Math.round(speed.getValue() * Math.sin(Math.toRadians(direction.getDegrees())));

        this.speedX = new Speed(sx);
        this.speedY = new Speed(sy);
    }

    @Requires("speed != null && direction != null")
    @Ensures({"speed == old(speed)", "speedX == -speedX", "speedY = -speedY", "direction = (direction + 180) % 360"})
    // keeps the same speed magnitude, but reverse both speedX and speedY and update the direction accordingly
    public void reverse() { // reverse the direction of the puck!
        int newDirection = (direction.getDegrees() + 180) % 360;
        this.direction = new Direction(newDirection);

        this.speedX = new Speed(-this.speedX.getValue());
        this.speedY = new Speed(-this.speedY.getValue());
    }

    @Requires("speed != null && direction != null")
    @Ensures({"speed == old(speed)", "speedX == -speedX", "speedY = old(speedY)", "direction = (180 - direction) % 360"})
    // keeps the same speed magnitude, only reverse speedX and update the direction accordingly
    public void reverseX() { // reverse the direction of the puck in x-axis
        int newDirection = (180 - direction.getDegrees()) % 360;
        if (newDirection < 0) newDirection += 360;

        this.direction = new Direction(newDirection);

        this.speedX = new Speed(-this.speedX.getValue());
    }

    @Requires("speed != null && direction != null")
    @Ensures({"speed == old(speed)", "speedX == old(speedX)", "speedY = -speedY", "direction = (360 - direction) % 360"})
    // keeps the same speed magnitude, only reverse speedY and update the direction accordingly
    public void reverseY() { // reverse direction of the puck in the y-axis
    	int newDirection = (360 - direction.getDegrees()) % 360;

        this.direction = new Direction(newDirection);

        this.speedY = new Speed(-this.speedY.getValue());
    }
}