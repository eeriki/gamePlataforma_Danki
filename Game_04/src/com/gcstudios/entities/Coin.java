package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class Coin extends Entity{
	
	public int frames;
	public int index, maxIndex = 9;
	
	public static BufferedImage[] coinSprite;
	
	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		coinSprite = new BufferedImage[10];
		
		for(int i = 0; i < 10; i++) {
			coinSprite[i] = Game.spritesheet.getSprite(i*16, 80, 16, 16);
		}
	}
	
	public void tick() {
		frames++;
		if(frames == 5) {
			index++;
			frames = 0;
			if(index == maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(coinSprite[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
