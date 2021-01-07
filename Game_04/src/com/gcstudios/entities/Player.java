package com.gcstudios.entities;


//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{
	
	public int life = 100;
	public final int maxLife = 100;

	public static boolean right, left, jump;
	public static boolean isJumping;
	public static int jumpFrames;
	public static int jumpMax = 46;
	public static int gravity = 2;
	
	public static int index = 0, maxIndex = 3;
	public static BufferedImage rightPlayer[];
	public static BufferedImage leftPlayer[];
	public static boolean moved;
	public static int frames = 0, maxFrames = 14;
	
	public static BufferedImage jumpSpriteL = Game.spritesheet.getSprite(0, 112, 16, 16);
	public static BufferedImage jumpSpriteR = Game.spritesheet.getSprite(16, 112, 16, 16);
	
	public static int dR = 0, dL = 1, d;
	
	public static int coins;
	public static int maxCoins;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite((i*16), 128, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite((i*16), 144, 16, 16);
		}
	}
	
	public void tick(){
		depth = 2;
		updateCamera();
		pickCoin();
		
		moved = false;
		
		if(jump) { 
			if(!World.isFree(this.getX(), this.getY() + 1)) {
				isJumping = true;
			} else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y-=gravity;
				jumpFrames+=2;
				if(jumpFrames > jumpMax) {
						jump = false;
						isJumping = false;
						jumpFrames = 0;
				}
			} else {
				jump = false;
				isJumping = false;
				jumpFrames = 0;
			}
		}else if(!isJumping && World.isFree((int)x, (int)(y+gravity))) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this, e)) {
						isJumping = true;
						((Enemy)e).life--;
						if(((Enemy) e).life == 0) {
							Game.entities.remove(e);
							break;
						}
					}
				}
			}
		}
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			d = dR;
			x+=speed;
			moved = true;
			
		} else if(left && World.isFree((int)(x-speed), (int)y)) {
			d = dL;
			x-=speed;
			moved = true;	
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		} else {
			index = 0;
		}
		
		
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX()- (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void pickCoin() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Coin) {
				if(Entity.isColidding(this, e)) {
					coins++;
					Game.entities.remove(e);
				}
			}
		}
	}
	

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if(d == dR) {
			if(!isJumping)
				g2.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else
				g2.drawImage(jumpSpriteR, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(d == dL){
			if(!isJumping)
				g2.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else
				g2.drawImage(jumpSpriteL, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
