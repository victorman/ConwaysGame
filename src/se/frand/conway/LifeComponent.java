package se.frand.conway;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

public class LifeComponent extends JComponent {

	private static final int AGE_RATE = 3;
	private static final long serialVersionUID = 6823443613749624771L;
	private Life game;
	
	public LifeComponent(Life game) {
		this.game = game;
	}
	
	public synchronized void paintComponent(Graphics g){
		if(game == null) return;
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double width = (double) getWidth() / (double) game.getWidth();
		double height = (double) getHeight() / (double) game.getHeight();
		
		Rectangle2D cell;
		for(int x=0; x<game.getWidth(); x++) {
			for(int y=0; y<game.getHeight(); y++) {
				cell = new Rectangle2D.Double(width * x, height * y, width, height);
				if(game.isLiving(x, y)){
					int red = redValue(game.getAge(x,y));
					g2.setColor(new Color(red , 0, 0));
					g2.fill(cell);
				}
				g2.setColor(Color.LIGHT_GRAY);
				g2.draw(cell);
			}
		}
	}

	private int redValue(int cellAge) {
		int age = (int)Math.sqrt((double)(cellAge * cellAge));
		age*=AGE_RATE;
		return Math.max(100, 255 - age);
	}

	public void redraw() {
		repaint();
	}

}
