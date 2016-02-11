package se.frand.conway;


public class Life {
	private int[][] grid;
	private int width;
	private int height;
	private int steps;
	
	public Life(int width, int height) {
		steps = 0;
		this.width = width;
		this.height = height;
		grid = new int[width][height];
		for(int i = 0; i<width; i++) {
			for(int j=0; j<height; j++) {
				grid[i][j] = -1;
			}
		}
	}
	
	/*
	 * rules for every grid cell:
	 * 0 or 1 neighbors: set to -1
	 * >= 4 neighbors: set to -1
	 * 2 or 3: age by 1
	 */
	public void step() {
		steps++;
		
		int[][] nextStep = new int[width][height];
		for(int i = 0; i<width; i++) {
			for(int j=0; j<height; j++) {
				int neighbors = 0;
				
				if(i-1 >= 0 && j-1 >= 0 && grid[i-1][j-1] >= 0) 	neighbors++;
				if(i-1 >= 0 && grid[i-1][j] >= 0) 					neighbors++;
				if(i-1 >= 0 && j+1 < height && grid[i-1][j+1] >= 0) neighbors++;
				if(j-1 >= 0 && grid[i][j-1] >= 0) 					neighbors++;
				if(j+1 < height && grid[i][j+1] >= 0) 				neighbors++;
				if(i+1 < width && j-1 >= 0 && grid[i+1][j-1] >= 0) 	neighbors++;
				if(i+1 < width && grid[i+1][j] >= 0)				neighbors++;
				if(i+1 < width && j+1 < height && grid[i+1][j+1] >= 0) neighbors++;
				
				//System.out.printf("%d neighbors\n", neighbors);
					
				if((grid[i][j] >= 0 && neighbors > 1 && neighbors < 4) ||
						(grid[i][j] < 0 && neighbors == 3))
					nextStep[i][j] = grid[i][j]+1;
				else
					nextStep[i][j] = -1;
			}
		}
		
		grid = nextStep;
	}
	
	public void toggle(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return;
		
		if(grid[x][y] < 0)
			grid[x][y] = 0;
		else
			grid[x][y] = -1;
	}

	public boolean isLiving(int x, int y) {
		return grid[x][y] > -1;
	}
	
	public int getAge(int x, int y) {
		return grid[x][y];
	}
	
	public int getSteps() {
		return steps;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}
