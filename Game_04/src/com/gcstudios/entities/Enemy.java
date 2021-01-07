package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Enemy extends Entity{
	
	public boolean right = true, left = false;
	public BufferedImage[] enWalk;
	public boolean isWalking;
	public int walkFrames, maxWalk = 20, index, maxIndex = 3;
	
	public int life = 2;
	public int dF = 0;
	public int deadfr = 120;
	
	public BufferedImage spritev1 = Game.spritesheet.getSprite(48, 48, 16, 16);	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		enWalk = new BufferedImage[3];
		for(int i = 0; i < 3; i++) {
			enWalk[i] = Game.spritesheet.getSprite((i*16), 48, 16, 16);
		}
	}
	
	public void tick() {
		if(World.isFree((int)this.getX(), (int)(this.getY()+1))) {
			y+=1;
		}
		if(life > 1) {
			if(right && World.isFree((int)(x+speed), (int)y)) {
				x+=speed;
				isWalking = true;
				if(World.isFree((int)(x+16), (int)(y+1))) {
					right = false;
					left = true;
				}
				for(int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
					if(e instanceof Player) {
						if(isColidding(this, e)) {
							if(Entity.rand.nextInt(100) < 20)
								Game.player.life--;
						}
						
					}
				}
			} else {
				right = false;
				left = true;
			}
			if(left && World.isFree((int)(x-speed), (int)y)) {
				x-=speed;
				isWalking = true;
				if(World.isFree((int)(x-16), (int)(y+1))) {
					right = true;
					left = false;
				}
				for(int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
					if(e instanceof Player) {
						if(isColidding(this, e)) {
							if(Entity.rand.nextInt(100) < 20)
								Game.player.life--;
						}
					}
				}
			} else {
				right = true;
				left = false;
			}
			
			if(isWalking) {
				this.walkFrames++;
				if(walkFrames == maxWalk) {
					index++;
					walkFrames = 0;
					if(index == maxIndex) {
						index = 0;
					}
				}
			}
		}
		if(life == 1) {
			dF++;
			if(dF == deadfr) {
				dF = 0;
				life = 2;
			}
		}
	}
	

	public void render(Graphics g) {
		if(life > 1)
			g.drawImage(enWalk[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else
			g.drawImage(spritev1, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
