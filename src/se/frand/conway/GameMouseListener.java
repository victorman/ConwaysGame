package se.frand.conway;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameMouseListener implements MouseListener, MouseMotionListener {
	
	private boolean mouseDown;
	private int addState;
	private Life game;
	private LifeComponent gameComponent;
	private int cellSize;
	
	public GameMouseListener(Life game, LifeComponent gameComponent, int cellSize) {
		mouseDown = false;
		addState = -1;
		this.cellSize = cellSize;
		this.gameComponent = gameComponent;
		this.game = game;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = e.getPoint();
	    int y = (int)((point.getY() - LifeGame.CONTROL_BAR_HEIGHT) / (double)cellSize);
	    int x = (int)(point.getX() / (double)cellSize);
		//System.out.printf("%d %d %f %f\n",x, y, point.getX(), point.getY());
		if(game.getAge(x, y) != game.setState(addState, x ,y)) {
		    System.out.printf("drawing state: %d\n", addState);
			gameComponent.redraw();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Point point = e.getPoint();
	    int y = (int)((point.getY() - LifeGame.CONTROL_BAR_HEIGHT) / (double)cellSize);
	    int x = (int)(point.getX() / (double)cellSize);
		//System.out.printf("%d %d %f %f\n",x, y, point.getX(), point.getY());
		addState = game.toggle(x, y);
		System.out.printf("drawing state: %d\n", addState);
		gameComponent.redraw();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		addState = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
