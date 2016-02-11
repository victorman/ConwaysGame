package se.frand.conway;

import java.awt.*;
import java.awt.event.*;
import java.lang.management.*;

import javax.swing.*;


public class LifeGame {
	
	private static final int STEP_TIME = 150;
	public static final int CONTROL_BAR_HEIGHT = 50;
	private static final String TITLE = "Conway's Game of Life by victor";

	public static void main(String[] args) {
		JFrame frame = setupJFrame(args);
		
		final JButton runButton = new JButton("Run");
		final JButton stopButton = new JButton("Stop");
		stopButton.setEnabled(false);
		final JButton resetButton = new JButton("Reset");
		
		JPanel controlPanel = new JPanel();
		final JLabel stepsLabel = new JLabel("0");
		controlPanel.add(stepsLabel);
		controlPanel.setSize(frame.getWidth(), CONTROL_BAR_HEIGHT);
		controlPanel.add(runButton);
		controlPanel.add(stopButton);
		controlPanel.add(resetButton);
		frame.add(controlPanel, BorderLayout.NORTH);
		
		final Life game = setupGame(args);
		final int cellSize = Integer.valueOf(args[0]);
		final LifeComponent gameComponent = new LifeComponent(game);
		frame.add(gameComponent, BorderLayout.CENTER);
		GameMouseListener listener = new GameMouseListener(game, gameComponent, cellSize);
		frame.addMouseListener((MouseListener) listener);
		frame.addMouseMotionListener((MouseMotionListener) listener);
		
		// set up a runnable to control the game
		final Runnable gameRunnable = new Runnable() {
			@Override
			public void run() {
				while(!Thread.interrupted()) {
					game.step();
					stepsLabel.setText(""+game.getSteps());
					gameComponent.redraw();
					try {
						Thread.sleep(STEP_TIME);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				resetButton.setEnabled(false);
				// go
				game.start(gameRunnable);
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				resetButton.setEnabled(true);
				// halt
				game.stop();
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.reset();
				stepsLabel.setText(""+game.getSteps());
				gameComponent.redraw();
			}
		});
		
		// lastly become visible
		frame.setVisible(true);
	}

	private static Life setupGame(String[] args) {
		int width, height;
		width = Integer.valueOf(args[1]);
		height = Integer.valueOf(args[2]);
		return new Life(width, height);
	}

	private static JFrame setupJFrame(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int cellSize, width, height;
		cellSize = width = height = 0;
		try {
			cellSize = Integer.valueOf(args[0]);
			width = Integer.valueOf(args[1]);
			height = Integer.valueOf(args[2]);
		} catch(NumberFormatException e) {
			System.err.println("Inputs not formatted as integers");
			System.exit(1);
		} catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Please supply the following integer arguments: cellSize gridWidth gridHeight");
			System.exit(1);
		}
		
		frame.setSize(width * cellSize, height * cellSize + CONTROL_BAR_HEIGHT);
		return frame;
	}

}
