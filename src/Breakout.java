import game.*;
import game.audio.Sounds;
import game.drawing.Draw;
import game.physics.Rect;
import utils.Constants;
import entities.Ball;
import entities.Brick;

import java.awt.Color;
import java.util.ArrayList;

public class Breakout extends GameJava {
	private final Object drawLock = new Object();
	
	ArrayList<Brick> bricks = new ArrayList<Brick>();

	Ball ball;
	
	Rect paddle;
	
	int lives = 3;
	int level = 1;
	int score = 0;
	int lastCount = 0;
	
	boolean waiting = true;
	
	boolean gameOver = false;
	
	void restart(int level) {
		bricks = new ArrayList<Brick>();
		// create bricks
		for (int i=0;i<level;i++) {
	        for (int x=0;x<13;x++) {
	        	for (int y=0;y<7;y++) {
	        		bricks.add(new Brick(53+x*94, 90+y*29, y));
	        	}
	        }
		}
        lastCount = bricks.size();
		
        ball = new Ball();
        
        waiting = true;
	}
	
    public Breakout() {
        super(Constants.GAME_W, Constants.GAME_H, 60, 60);
        frameTitle = "Breakout";
        allowFullScreen = false;
        resizable = false;
      
        restart(1);
        
        paddle = new Rect(Constants.GAME_W/2, Constants.GAME_H-50, 98, 21);
        
        LoopManager.startLoops(this);
	}
	
	public static void main(String[] args) {
        new Breakout();   
    }
	
    @Override
    public void update() {
    	if(Input.mouseClick(0)) {
    		Sounds.play("wall");
			waiting = false;			
			if (gameOver == true) {
				lives = 3;
		    	level = 1;
		    	score = 0;
		    	gameOver = false;
			}
		}
    	if (waiting) {
    		return;
    	}
    	
    	paddle.x = Input.mousePos.x;
    	paddle.x = Math.max(Math.min(paddle.x, Constants.GAME_W-paddle.halfW), paddle.halfW);
    	
    	// called at the set frame rate
    	synchronized (drawLock) {
    		if (ball.update(bricks, paddle)) {
    			Sounds.play("fail");
    			ball = new Ball();
    			waiting = true;
    			if(--lives <= 0) {
    				gameOver = true;
    		        
    				restart(1);
    			}
    		}
    		
    		if (bricks.size() != lastCount) {
    			score += (lastCount - bricks.size()) * 100;
    			lastCount = bricks.size();
    			Sounds.play("brick");
    		}
    		
    		if (bricks.size() == 0) {
    			restart (++level);
    		}
		}
    }	
    
	@Override
	public void draw() {
        // called at the set update rate
		Draw.image("background",Constants.GAME_W/2, Constants.GAME_H/2,0,1.5);
		
		synchronized (drawLock) {
			for (int i = 0; i < bricks.size(); i++) {
				bricks.get(i).draw();
			}
			
			ball.draw();
			
			Draw.image("paddle", (int)paddle.x, (int)paddle.y);
		}
	}
    
    @Override
    public void absoluteDraw() {
        // called immediately after draw, all drawing is the same but without the camera affecting anything
    	if (waiting) {
    		Draw.setColor(Color.white);
    		Draw.setFontSize(8);
    		Draw.text("Click to Start", Constants.GAME_W/2-300, Constants.GAME_H/2);
    	}
    	if (gameOver) {
    		Draw.setColor(Color.white);
    		Draw.setFontSize(8);
    		Draw.text("GAME OVER!", Constants.GAME_W/2-200, Constants.GAME_H/2-100);
    	}
    	Draw.setFontSize(2);
    	Draw.text("Lives: " + Integer.toString(lives), 10, 20);
    	Draw.text("Level: " + Integer.toString(level), 150, 20);
    	Draw.text("Score: " + Integer.toString(score), 300, 20);
    }
}