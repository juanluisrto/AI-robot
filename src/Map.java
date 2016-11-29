
import edu.upc.moo.gui.*;

import java.awt.Color;
import java.util.ArrayList;

public class Map {

	final int X_CELLS = 30;
	final int Y_CELLS = 20;
	final int CELL_WIDTH = 35;
	
	final int WIDTH = X_CELLS*CELL_WIDTH;
	final int HEIGHT = Y_CELLS*CELL_WIDTH;
	
	final int OBSTACLE_PRCTG = 20; //percentage of blocks which will be obstacles
	final int SEGREGATION = 50;		//percentage of segregation (dispersion of the blocks in groups)
	
	public Cell [][] map = new Cell[X_CELLS][Y_CELLS];
	public Ventana v;
	
	
	public Map() {
		super();
		for (int i = 0; i< X_CELLS; i++){
			for (int j = 0; j< Y_CELLS; j++){
				map[i][j] = new Cell();
				
			}
		}
		this.v = new Ventana("AI-Robot",WIDTH,HEIGHT);
		v.actualizaFotograma();
		v.setColorFondo(Color.black);
		
		v.setCamara(X_CELLS/2 +0.5, Y_CELLS/2, Y_CELLS); //-15.5,10
		v.setDibujaCoordenadas(true);
		v.actualizaFotograma();
		
		
	}

	public void obstacleSet(){
		int nObstacles = OBSTACLE_PRCTG/100*X_CELLS*Y_CELLS;
		int groupSize = (1-nObstacles)*(SEGREGATION/100) + nObstacles;
	}
	
}

class Cell {
	boolean robot_here = false;
	boolean obstacle = false; //true means that the cell is an obstacle
	char flag; // B = busy; V = visited and free; N= NOT visited and free;
	
	public Cell() {
		super();
	}
	
	
	
	
}



