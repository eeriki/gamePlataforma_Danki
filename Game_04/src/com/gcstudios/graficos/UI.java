package com.gcstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI{
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(30, 30, 300, 20);
		g.setColor(Color.green);
		g.fillRect(30, 30, (Game.player.life)*3, 20);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.setColor(Color.white);
		g.drawString(Game.player.life + "/" + Game.player.maxLife, 125, 50);
		g.drawString("Moedas: " + Player.coins + "/" + Player.maxCoins, (Game.HEIGHT * Game.SCALE) - 50, 50);
	}
	
}
