package entities;

import java.awt.Color;
import java.util.ArrayList;

import game.audio.Sounds;
import game.drawing.Draw;
import game.physics.Circle;
import game.physics.Physics;
import game.physics.Rect;
import utils.Constants;
import utils.Direction;
import utils.Speed;
import utils.Velocity;

public class Ball {

	Velocity vel;
	
	Circle c;
	
	public Ball() {
		this.c = new Circle(Constants.GAME_W/2, Constants.GAME_H/2-100, 10);
		this.vel = new Velocity(new Speed(13), new Direction(280));
	}
	
	public boolean update(ArrayList<Brick> bricks, Rect paddle) {
		
		this.c.x += vel.getSpeedX().getValue();
		double sign = -Math.signum(this.vel.getSpeedX().getValue());
		// left wall
		if (this.c.x - this.c.r <= 0) {
			this.c.x = this.c.r+0.0001;
			this.vel.reverseX();
			Sounds.play("wall");
		// right wall
		} else if (this.c.x + this.c.r >= Constants.GAME_W) {
			this.c.x = Constants.GAME_W-this.c.r-0.0001;
			this.vel.reverseX();
			Sounds.play("wall");
		} else {
		// bricks	
			for (int i=0;i<bricks.size();i++) {
				Rect r = bricks.get(i).rect;
				if(Physics.circlerect(this.c, r)) {
					this.c.x = r.x + sign * (r.halfW + this.c.r + 0.0001);
					this.vel.reverseX();
					bricks.remove(i);
					break;
				}
			}
		}
		
		this.c.y -= vel.getSpeedY().getValue();
		sign = -Math.signum(this.vel.getSpeedY().getValue());
		// top
		if (this.c.y - this.c.r <= 0) {
			this.c.y = this.c.r+0.0001;
			this.vel.reverseY();
			Sounds.play("wall");
			// bottom
		} else if (this.c.y + this.c.r >= Constants.GAME_H) {
			// return true to be reset
			return true;
		} else {
			// bricks
			for (int i=0;i<bricks.size();i++) {
				Rect r = bricks.get(i).rect;
				if(Physics.circlerect(this.c, r)) {
					this.c.y = r.y + -sign * (r.halfH + this.c.r + 0.0001);
					this.vel.reverseY();
					bricks.remove(i);
					break;
				}
			}
		}
		
		if (Physics.circlerect(this.c, paddle)) {
			// Compute hit position relative to paddle center
		    double relativeIntersect = (this.c.x - paddle.x) / paddle.halfW;

		    // Clamp just in case
		    relativeIntersect = Math.max(-1, Math.min(1, relativeIntersect));

		    double maxBounceAngle = 60;

		    // Compute new angle
		    double bounceAngle = relativeIntersect * maxBounceAngle;
		    
		    // Prevent shallow angles
		    if (Math.abs(bounceAngle) < 10) {
		        bounceAngle = 10 * Math.signum(bounceAngle == 0 ? 1 : bounceAngle);
		    }

		    // Convert to game direction:
		    // 90° = straight up, so we subtract bounceAngle
		    int newDirection = (int)(90 - bounceAngle);

		    // Keep same speed, change direction
		    this.vel.setDirection(new Direction(newDirection));
		    // Speed ramps up to make game difficult		    
		    this.vel.setSpeed(new Speed(this.vel.getSpeed().getValue() + 0.25));
		    Sounds.play("paddle");
		}
		
		return false;
	}
	
	public void draw() {
		Draw.setColor(Color.white);
		Draw.circle(this.c);
	}
}
