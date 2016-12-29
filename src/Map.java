
import edu.upc.moo.gui.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.lang.Math;


public class Map {

	final int X_CELLS = 32; //columns
	final int Y_CELLS = 32; //rows
	final int CELL_WIDTH = 20;
	
	final int WIDTH = X_CELLS*CELL_WIDTH;
	final int HEIGHT = Y_CELLS*CELL_WIDTH;
	
	final double OBSTACLE_PRCTG = 20; //percentage of blocks which will be obstacles
	final double SEGREGATION = 0;		//percentage of segregation (dispersion of the blocks in groups)
	
	public Cell [][] map = new Cell[X_CELLS][Y_CELLS];
	public Ventana v;
	public ArrayList <Integer[]> obstacleCoordinates = new ArrayList <Integer[]>();
	
	int x;
	int y;
	
	public Map() {
		super();
		for (int i = 0; i< X_CELLS; i++){
			for (int j = 0; j< Y_CELLS; j++){
				map[i][j] = new Cell();
			}
		}
		for (int i = 0;i < X_CELLS; i++){
			map[i][0].obstacle=true;
			map[i][Y_CELLS-1].obstacle=true;
			Integer[]limitdown={i,0};
			Integer[]limitup={i,Y_CELLS};
			this.obstacleCoordinates.add(limitup);
			this.obstacleCoordinates.add(limitdown);
		}
		for (int i = 0;i < Y_CELLS; i++){
			map[0][i].obstacle=true;
			map[X_CELLS-1][i].obstacle=true;
			Integer[]limitleft={0,i};
			Integer[]limitright={X_CELLS,i};
			this.obstacleCoordinates.add(limitleft);
			this.obstacleCoordinates.add(limitright);
		}
		map[X_CELLS-1][Y_CELLS-1].obstacle=true;
		Integer[]corner={X_CELLS,Y_CELLS};
		this.obstacleCoordinates.add(corner);
		
		this.v = new Ventana("AI-Robot",WIDTH,HEIGHT);
		v.actualizaFotograma();
		v.setColorFondo(Color.black);
		//v.setCamara((X_CELLS+2)/2 -0.5, (Y_CELLS+2)/2 -1, X_CELLS+2); //-15.5,10
		v.setCamara((X_CELLS+2)/2 -0.5, (Y_CELLS+2)/2 -1 ,Math.min(Y_CELLS +2,X_CELLS +2));
		//v.setDibujaCoordenadas(true);		
		//obstacleSet();
		mazeSet();
		v.actualizaFotograma();
		
		System.out.println("nº obstacles " + obstacleCoordinates.size());
		
		
	}
	public void mazeSet(){
		MazeGenerator maze = new MazeGenerator(this);
		maze.generate();
		maze.show();
	}
	public void obstacleSet(){
		int nObstacles = (int) (OBSTACLE_PRCTG/100.0*X_CELLS*Y_CELLS);
		int groupSize = (int) ((1-nObstacles)*(SEGREGATION/100) + nObstacles);
		int o = 0;
		while (o<nObstacles){
			int size = Math.min(groupSize,nObstacles-o);
			o = o + createsObst(size);
			 
		}
		paintObst();
	}
	
	public int createsObst(int groupSize){
		int count = 0;
		Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
		
		 x = (int) ((X_CELLS-1)*Math.random());
		 y = (int) ((Y_CELLS-1)*Math.random());
		while (count<groupSize){
			if (map[x][y].obstacle == false){
				map[x][y].obstacle = true;
				count++;
				Integer[] pos = {x,y};
				obstacleCoordinates.add(pos); //adds the obstacles coordinates to the list
				
				Collections.shuffle(Arrays.asList(array)); //randomly shuffles array which determines the order in which the neighbour cells will be searched
				boolean placeFound = lookAround(array);				
				if (placeFound == false) return count; //if the new obstacle is completely surrounded by obstacles
			}
			else return count;
		}
		return count;
	}
	private boolean lookAround(Integer[] array) throws ArrayIndexOutOfBoundsException{ //searchs for neighbour without obstacle and sets x,y to that neighbour
		for (int i= 1; i<=8; i++){
			Integer n= array[i-1];
			// 1 2 3     In each of the cases first we check if there exists such cell (not in the border) and then if it is an obstacle
			// 8 * 4
			// 7 6 5
		try{	
			switch (n) {
				case 1: if (/*x!=0 && y!=Y_CELLS	     && */  map[x-1][y+1].obstacle == false){ x=x-1; y= y+1; return true;} 
				case 2: if (/*y!=Y_CELLS 				 && */  map[ x ][y+1].obstacle == false){ y= y+1; return true;}		   
				case 3: if (/*x!=X_CELLS && y!=Y_CELLS   && */  map[x+1][y+1].obstacle == false){ x=x+1; y= y+1; return true;} 
				case 4: if (/*x!=X_CELLS 				 && */  map[x+1][ y ].obstacle == false){ x=x+1; return true;}
				case 5: if (/*x!=X_CELLS && y!=0 		 && */  map[x+1][y-1].obstacle == false){ x=x+1; y= y-1; return true;}
				case 6: if (/*y!=0 					     && */  map[ x ][y-1].obstacle == false){y= y-1; return true;}
				case 7: if (/*x!=0 && y!=0			     && */  map[x-1][y-1].obstacle == false){x=x-1; y= y-1; return true;}
				case 8: if (/*x!=0 					     && */  map[x-1][ y ].obstacle == false){x= x-1; return true;}
			}
		}
		catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
		}
		return false;
		
	}
	public void paintObst(){
		for (Integer[] pos : obstacleCoordinates){
			v.dibujaRectangulo(pos[0], pos[1], 1, 1, Color.blue);
		}
	}

class Cell {
	boolean robot_here = false;
	boolean obstacle = false; //true means that the cell is an obstacle
	TypeOfCell type = TypeOfCell.FREE; 
	char flag; // B = busy; V = visited and free; N= NOT visited and free;
	
	public Cell() {
		super();
		}
	
	
	
	
}

	enum TypeOfCell{
		OBS,FREE
	}
}



