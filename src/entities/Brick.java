package entities;
import game.physics.Rect;
import game.drawing.*;

public class Brick {
	
	Rect rect;
	int color;
	
	
	public Brick(double sx, double sy, int color) {
		this.rect = new Rect(sx,sy,94,29);
		this.color = color;
	}
	
	public void draw() {
		Draw.image("b".concat(Integer.toString(this.color)), (int)Math.round(this.rect.x), (int)Math.round(this.rect.y));
	}
}
