package environment;

import edu.upc.moo.gui.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Arrays;
import java.lang.Math;

public class Map {
	
	public int width = 25; // columns
	public int length = 25; // rows
	final int CELL_WIDTH = 20;

	final int WIDTH = width * CELL_WIDTH;
	final int HEIGHT = length * CELL_WIDTH;

	final double OBSTACLE_PRCTG = 20; // percentage of blocks which will be
	// obstacles
	final double SEGREGATION = 0; // percentage of segregation (dispersion of
	// the blocks in groups)

	public Block[][] blocks = new Block[width][length];
	public Ventana v;
	public ArrayList<Integer[]> obstacleCoordinates = new ArrayList<Integer[]>();

	int x;
	int y;

	public Map() {
		super();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				blocks[i][j] = new Block(i,j,TypeOfBlock.FREE);
			}
		}
		for (int i = 0; i < width; i++) {
			blocks[i][0].obstacle = true;
			blocks[i][0].type = TypeOfBlock.WALL;
			blocks[i][length - 1].obstacle = true;
			blocks[i][length - 1].type=TypeOfBlock.WALL;
			Integer[] limitdown = { i, 0 };
			Integer[] limitup = { i, length -1};
			this.obstacleCoordinates.add(limitup);
			this.obstacleCoordinates.add(limitdown);
		}
		for (int i = 0; i < length; i++) {
			blocks[0][i].obstacle = true;
			blocks[0][i].type=TypeOfBlock.WALL;
			blocks[width - 1][i].obstacle = true;
			blocks[width - 1][i].type=TypeOfBlock.WALL;
			Integer[] limitleft = { 0, i };
			Integer[] limitright = { width-1, i };
			this.obstacleCoordinates.add(limitleft);
			this.obstacleCoordinates.add(limitright);
		}
		blocks[width - 1][length - 1].obstacle = true;
		blocks[width - 1][length - 1].type=TypeOfBlock.WALL;
		Integer[] corner = { width, length };
		this.obstacleCoordinates.add(corner);

		this.v = new Ventana("AI-Robot", WIDTH, HEIGHT);
		v.actualizaFotograma();
		v.setColorFondo(Color.black);
		// v.setCamara((X_CELLS+2)/2 -0.5, (Y_CELLS+2)/2 -1, X_CELLS+2);
		// //-15.5,10
		v.setCamara((width + 2) / 2 - 0.5, (length + 2) / 2 - 1, Math.min(length + 2, width + 2));
		// v.setDibujaCoordenadas(true);
		obstacleSet();
		//mazeSet();
		v.actualizaFotograma();

		System.out.println("n� obstacles " + obstacleCoordinates.size());

	}

	public void mazeSet() {
		MazeGenerator maze = new MazeGenerator(this);
		maze.generate();
		maze.show();
	}

	public void obstacleSet() {
		int nObstacles = (int) (OBSTACLE_PRCTG / 100.0 * width * length);
		int groupSize = (int) ((1 - nObstacles) * (SEGREGATION / 100) + nObstacles);
		int o = 0;
		while (o < nObstacles) {
			int size = Math.min(groupSize, nObstacles - o);
			o = o + createsObst(size);

		}
		paintObst();
	}

	public int createsObst(int groupSize) {
		int count = 0;
		Integer[] array = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };

		x = (int) ((width - 1) * Math.random());
		y = (int) ((length - 1) * Math.random());
		while (count < groupSize) {
			if (blocks[x][y].obstacle == false) {
				blocks[x][y].obstacle = true;
				blocks[x][y].type = TypeOfBlock.WALL;
				count++;
				Integer[] pos = { x, y };
				obstacleCoordinates.add(pos); // adds the obstacles coordinates
				// to the list

				Collections.shuffle(Arrays.asList(array)); // randomly shuffles
				// array which
				// determines the
				// order in which
				// the neighbour
				// cells will be
				// searched
				boolean placeFound = lookAround(array);
				if (placeFound == false)
					return count; // if the new obstacle is completely
				// surrounded by obstacles
			} else
				return count;
		}
		return count;
	}

	private boolean lookAround(Integer[] array) throws ArrayIndexOutOfBoundsException { // searchs
		// for
		// neighbour
		// without
		// obstacle
		// and
		// sets
		// x,y
		// to
		// that
		// neighbour
		for (int i = 1; i <= 8; i++) {
			Integer n = array[i - 1];
			// 1 2 3 In each of the cases first we check if there exists such
			// cell (not in the border) and then if it is an obstacle
			// 8 * 4
			// 7 6 5
			try {
				switch (n) {
				case 1:
					if (/* x!=0 && y!=Y_CELLS && */ blocks[x - 1][y + 1].obstacle == false) {
						x = x - 1;
						y = y + 1;
						return true;
					}
				case 2:
					if (/* y!=Y_CELLS && */ blocks[x][y + 1].obstacle == false) {
						y = y + 1;
						return true;
					}
				case 3:
					if (/* x!=X_CELLS && y!=Y_CELLS && */ blocks[x + 1][y + 1].obstacle == false) {
						x = x + 1;
						y = y + 1;
						return true;
					}
				case 4:
					if (/* x!=X_CELLS && */ blocks[x + 1][y].obstacle == false) {
						x = x + 1;
						return true;
					}
				case 5:
					if (/* x!=X_CELLS && y!=0 && */ blocks[x + 1][y - 1].obstacle == false) {
						x = x + 1;
						y = y - 1;
						return true;
					}
				case 6:
					if (/* y!=0 && */ blocks[x][y - 1].obstacle == false) {
						y = y - 1;
						return true;
					}
				case 7:
					if (/* x!=0 && y!=0 && */ blocks[x - 1][y - 1].obstacle == false) {
						x = x - 1;
						y = y - 1;
						return true;
					}
				case 8:
					if (/* x!=0 && */ blocks[x - 1][y].obstacle == false) {
						x = x - 1;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return false;

	}

	public void paintObst() {
		for (Integer[] pos : obstacleCoordinates) {
			v.dibujaRectangulo(pos[0], pos[1], 1, 1, Color.blue);
		}
	}
	
	public void paintVisited() {
		
		for (int i=0;i<visitedBlocks.size();i++) {
			v.dibujaRectangulo(visitedBlocks.get(i)[0], visitedBlocks.get(i)[1], 1, 1, Color.green);
		}
	}
	
	public ArrayList<Integer[]> visitedBlocks = new ArrayList<Integer[]>();
	
	public void paintAgent() {
		v.dibujaRectangulo(xOfAgent, yOfAgent, 1, 1, Color.red);
	}


	public class Block {
		boolean obstacle = false; // true means that the cell is an obstacle
		TypeOfBlock type = TypeOfBlock.FREE;
		char flag; // B = busy; V = visited and free; N= NOT visited and free;
		
		public boolean visited = false;
		
		public Block() {
			super();
		}
		
		
		int x;
		int y;
		
		public Block(int x, int y,TypeOfBlock type) {
			this.x=x;
			this.y=y;
			this.type=type;
		}
	}

	public enum TypeOfBlock {
		WALL, FREE
	}
	
	
//	public int width; fixed
//	public int length; fixed
//	
	public int xOfAgent;
	public int yOfAgent;
//	
//	Block[][] blocks;
//	
//	public Map(int width, int length) {
//		if(width <= 0 || length <= 0){
//			try {
//				throw new Exception();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		this.length = length;
//		this.width = width;
//		blocks = new Block[width][length];
//		
//		for(int i=1;i<width-1;i++){
//			for(int j=1;j<length-1;j++){
//				blocks[i][j] = new Block(i, j, Block.TypeOfBlock.FREE);
//			}
//		}
//		
//		for(int i=0;i<width;i++){
//			blocks[i][0] = new Block(i, 0, Block.TypeOfBlock.WALL);
//			blocks[i][length-1] = new Block(i, length-1, Block.TypeOfBlock.WALL);
//		}
//		
//		for(int i=1;i<length-1;i++){
//			blocks[0][i] = new Block(0, i, Block.TypeOfBlock.WALL);
//			blocks[width-1][i] = new Block(width-1, i, Block.TypeOfBlock.WALL);
//		}
//		
//		for(int i=2;i<width-2 && i<length-2;i++){
//			blocks[i][i] = new Block(i, i, Block.TypeOfBlock.WALL);
//		}
//		
//		for(int i=2, j=width-3;i<length-2;i++,j--){
//			blocks[i][j] = new Block(i, i, Block.TypeOfBlock.WALL);
//		}
//	}
//	
//	public void printOutMap(){
//		System.out.println("--------------------------------");
//		for(int j=length-1;j>=0;j--){
//			for(int i=0;i<width;i++){
//				if(i==xOfAgent && j == yOfAgent){
//					System.out.print("AGNT"+" ");
//				}else{
//					System.out.print(blocks[i][j].type+" ");
//				}
//			}
//			System.out.println("\n");
//		}
//		System.out.println("--------------------------------");
//	}
//	
	public TypeOfBlock getRightBlock(int x, int y){
		return blocks[x+1][y].type;
	}
	
	public TypeOfBlock getLeftBlock(int x, int y){
		return blocks[x-1][y].type;
	}
	
	public TypeOfBlock getUpBlock(int x, int y){
		return blocks[x][y+1].type;
	}
	
	public TypeOfBlock getDownBlock(int x, int y){
		return blocks[x][y-1].type;
	}
	
	public TypeOfBlock getBlock(int x, int y){
		return blocks[x][y].type;
	}
}
