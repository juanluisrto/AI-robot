package robotAgent;

import java.util.ArrayList;
import environment.Map.*;


public class Agent {
	AgentsInterfaceForMap agentsInterface;
	MapOfAgent agentsMap;
	int myX;
	int myY;
	
	public Agent(AgentsInterfaceForMap agentsInterface) {
		this.agentsInterface = agentsInterface;
		agentsMap = new MapOfAgent(agentsInterface);
	}
	
	public void solve(){

//		agentsInterface.map.printOutMap();
		//steps for first move
		checkBlocks();
		setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
		//this metjod will only set true or false on statXIsValid
		chooseState();
		
		while(!ifWon()){
			
			System.out.println("*check states again*");
			if(state1IsValid){
				System.out.println("state1 is runing");
				runState1();
			}else if(state2IsValid){
				System.out.println("state2 is runing");
				runState2();
			}else{
				System.out.println("state3 is runing");
				runState3();
			}
			checkBlocks();
			chooseState();
		}
		System.out.println("*WON!*");
	}
	
	private void checkBlocks(){
		
		TypeOfBlock typeOfLeftBlock = agentsInterface.seeLeft(); 
		newWallBeside = false;
		newFreeBlock = false;
		
		if(getAgentsLeftBlock()==null){
			System.out.println("left is null");

			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfLeftBlock==TypeOfBlock.WALL){
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
				state1IsValid = true;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX-1][myY] = new BlockOfAgent(myX-1, myY, type);
		}else{
			if(getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.NOTVISITED){
				newFreeBlock = true;
			}else if(getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
				newWallBeside = true;
			}
		}
		
		TypeOfBlock typeOfRightBlock = agentsInterface.seeRight(); 
		
		if(getAgentsRightBlock()==null){
			System.out.println("right is null");

			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfRightBlock==TypeOfBlock.WALL){
				newWallBeside = true;
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX+1][myY] = new BlockOfAgent(myX+1, myY, type);
		}else{
			if(getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.NOTVISITED){
				newFreeBlock = true;
			}else if(getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
				newWallBeside = true;
			}
		}
		
		TypeOfBlock typeOfUpBlock = agentsInterface.seeUp(); 
		
		if(getAgentsUpBlock()==null){
			System.out.println("up is null");
			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfUpBlock==TypeOfBlock.WALL){
				newWallBeside = true;
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX][myY+1] = new BlockOfAgent(myX, myY+1, type);
		}else{
			if(getTypeOfAgentsUpBlock()==TypeOfAgentsBlock.NOTVISITED){
				newFreeBlock = true;
			}else if(getTypeOfAgentsUpBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
				newWallBeside = true;
			}
		}
		
		TypeOfBlock typeOfDownBlock = agentsInterface.seeDown();
	
		if(getAgentsDownBlock()==null){
			System.out.println("down is null");
			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfDownBlock==TypeOfBlock.WALL){
				newWallBeside = true;
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX][myY-1] = new BlockOfAgent(myX, myY-1, type);
		}else{
			if(getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.NOTVISITED){
				newFreeBlock = true;
			}else if(getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
				newWallBeside = true;
			}
		}
	}
	
	private void chooseState(){
		//state of checking if there is wall beside
		if(newWallBeside){
			state1IsValid = true;
		}else if(newFreeBlock){
			state1IsValid = false;
			state2IsValid = true;
		}else{
			state1IsValid = false;
			state2IsValid = false;
		}
	}
	
	private boolean state1IsValid = false;
	private boolean state2IsValid=false;
	
	boolean newWallBeside = false;
	boolean newFreeBlock = false;
	
	private int numberOfUnvisitedBlocks=0;
	
	private boolean ifWon(){
		return numberOfUnvisitedBlocks <=0;
	}
	
	//the types of the blocks
	private TypeOfAgentsBlock getTypeOfAgentsLeftBlock(){
		return agentsMap.blocksOfAgent[myX-1][myY].myType;
	}
	
	private TypeOfAgentsBlock getTypeOfAgentsRightBlock(){
		return agentsMap.blocksOfAgent[myX+1][myY].myType;
	}
	
	private TypeOfAgentsBlock getTypeOfAgentsDownBlock(){
		return agentsMap.blocksOfAgent[myX][myY-1].myType;
	}
	
	private TypeOfAgentsBlock getTypeOfAgentsUpBlock(){
		return agentsMap.blocksOfAgent[myX][myY+1].myType;
	}
	
	private TypeOfAgentsBlock getTypeOfAgentsCurrentBlock(){
		return agentsMap.blocksOfAgent[myX][myY].myType;
	}
	
	//the blocks
	private BlockOfAgent getAgentsLeftBlock(){
		return agentsMap.blocksOfAgent[myX-1][myY];
	}
	
	private BlockOfAgent getAgentsRightBlock(){
		return agentsMap.blocksOfAgent[myX+1][myY];
	}
	
	private BlockOfAgent getAgentsDownBlock(){
		return agentsMap.blocksOfAgent[myX][myY-1];
	}
	
	private BlockOfAgent getAgentsUpBlock(){
		return agentsMap.blocksOfAgent[myX][myY+1];
	}
	
	//make steps
	private void goLeft(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myX=myX-1;
		agentsInterface.goLeft();
		checkBlocks();
		
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}
		oldMoves.add(new Coor(myX,myY,STEP.LEFT));

//		agentsInterface.map.printOutMap();
	}
	
	private void goRight(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myX=myX+1;
		agentsInterface.goRight();
		checkBlocks();
	
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}
		oldMoves.add(new Coor(myX,myY,STEP.RIGHT));

//		agentsInterface.map.printOutMap();
	}
	
	private void goDown(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myY = myY-1;
		agentsInterface.goDown();
		checkBlocks();
		
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}
		oldMoves.add(new Coor(myX,myY,STEP.DOWN));

//		agentsInterface.map.printOutMap();
	}

	private void goUp(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myY = myY+1;
		agentsInterface.goUp();	
		checkBlocks();
		
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}
		oldMoves.add(new Coor(myX,myY,STEP.UP));

//		agentsInterface.map.printOutMap();
	}
	
	//list of steps;
	ArrayList<Coor> oldMoves = new ArrayList<>();
	
	//when there is wall beside me and i'm in new area
	private void runState1(){
		BlockOfAgent startBlock = agentsMap.blocksOfAgent[myX][myY];
		boolean keepRuning = true;
		
		boolean prevIsUp = false;
		boolean prevIsDown = false;
		boolean prevIsLeft = false;
		boolean prevIsRight = false;
		
		boolean firstTime = true;
		
		while(keepRuning){

			boolean canGoLeft = true;
			boolean canGoRight = true;
			boolean canGoDown = true;
			boolean canGoUp = true;
			
			if(getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.UNCHECKEDWALL || getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.CHECKEDWALL){
				canGoLeft = false;
			}
			if(getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.UNCHECKEDWALL || getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.CHECKEDWALL){
				canGoRight = false;
			}
			if(getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.UNCHECKEDWALL || getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.CHECKEDWALL){
				canGoDown = false;
			}
			if(getTypeOfAgentsUpBlock()==TypeOfAgentsBlock.UNCHECKEDWALL || getTypeOfAgentsUpBlock()==TypeOfAgentsBlock.CHECKEDWALL){
				canGoUp = false;
			}

			if(firstTime){
				if(getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
					setTypeOfLeftAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
					if(canGoDown){
						prevIsDown = true;
					}else{
						setTypeOfDownAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoRight){
							prevIsRight = true;
						}else{
							setTypeOfRightAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							prevIsUp = true;
						}
					}
				}
				if(getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
					setTypeOfRightAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
					if(canGoUp){
						prevIsUp = true;
					}else{
						setTypeOfUpAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoLeft){
							prevIsLeft = true;
						}else{
							setTypeOfLeftAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							prevIsDown = true;
						}
					}
				}
				if(getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
					setTypeOfDownAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
					if(canGoRight){
						prevIsRight = true;
					}else{
						setTypeOfRightAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoUp){
							prevIsUp = true;
						}else{
							setTypeOfUpAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							prevIsLeft = true;
						}
					}
				}
				if(getTypeOfAgentsUpBlock()==TypeOfAgentsBlock.UNCHECKEDWALL){
					setTypeOfUpAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
					if(canGoLeft){
						prevIsLeft = true;
					}else{
						setTypeOfLeftAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoDown){
							prevIsDown = true;
						}else{
							setTypeOfDownAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							prevIsRight = true;
						}
					}
				}
				
				firstTime=false;
			}
			if(prevIsUp){
				if(canGoRight){
					goRight();
					prevIsUp = false;
					prevIsDown=false;
					prevIsLeft=false;
					prevIsRight=true;
				}else{
					setTypeOfRightAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
					if(canGoUp){
						goUp();
						prevIsUp = true;
						prevIsDown=false;
						prevIsLeft=false;
						prevIsRight=false;
					}else {
						setTypeOfUpAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoLeft){
							goLeft();
							prevIsUp = false;
							prevIsDown=false;
							prevIsLeft=true;
							prevIsRight=false;
						}else{
							setTypeOfLeftAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							goDown();
							prevIsUp = false;
							prevIsDown=true;
							prevIsLeft=false;
							prevIsRight=false;
						}
					}
				}
			}else if(prevIsRight){
				if(canGoDown){
					goDown();
					prevIsUp = false;
					prevIsDown=true;
					prevIsLeft=false;
					prevIsRight=false;
				}else{
					setTypeOfDownAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
					if(canGoRight){
						goRight();
						prevIsUp = false;
						prevIsDown=false;
						prevIsLeft=false;
						prevIsRight=true;
					}else{
						setTypeOfRightAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoUp){
							goUp();
							prevIsUp = true;
							prevIsDown=false;
							prevIsLeft=false;
							prevIsRight=false;
						}else{
							setTypeOfUpAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							goLeft();
							prevIsUp = false;
							prevIsDown=false;
							prevIsLeft=true;
							prevIsRight=false;
						}
					}
				}
			}else if(prevIsDown){
				if(canGoLeft){
					goLeft();
					prevIsUp = false;
					prevIsDown=false;
					prevIsLeft=true;
					prevIsRight=false;
				}else{
					if(canGoDown){
						setTypeOfLeftAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						goDown();
						prevIsUp = false;
						prevIsDown=true;
						prevIsLeft=false;
						prevIsRight=false;
					}else{
						setTypeOfDownAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoRight){
							goRight();
							prevIsUp = false;
							prevIsDown=false;
							prevIsLeft=false;
							prevIsRight=true;
						}else{
							setTypeOfRightAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							goUp();
							prevIsUp = true;
							prevIsDown=false;
							prevIsLeft=false;
							prevIsRight=false;
						}
					}
				}
			}else if(prevIsLeft){
				if(canGoUp){
					goUp();
					prevIsUp = true;
					prevIsDown=false;
					prevIsLeft=false;
					prevIsRight=false;
				}else{
					if(canGoLeft){
						setTypeOfUpAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						goLeft();
						prevIsUp = false;
						prevIsDown=false;
						prevIsLeft=true;
						prevIsRight=false;
					}else{
						setTypeOfLeftAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
						if(canGoDown){
							goDown();
							prevIsUp = false;
							prevIsDown=true;
							prevIsLeft=false;
							prevIsRight=false;
						}else{
							setTypeOfDownAgentBlock(TypeOfAgentsBlock.CHECKEDWALL);
							goRight();
							prevIsUp = false;
							prevIsDown=false;
							prevIsLeft=false;
							prevIsRight=true;
						}
					}
				}
			}
			else{
				System.out.println("PROBLEM THAT AI IS TRACKED");
				keepRuning=false;
			}
			if((startBlock.xOfBlock == myX && startBlock.yOfBlock == myY)){
				keepRuning=false;
			}
		}
	}

	public void setTypeOfCurrentBlock(TypeOfAgentsBlock type){
		agentsMap.blocksOfAgent[myX][myY].myType = type;
	}
	
	public void setTypeOfLeftAgentBlock(TypeOfAgentsBlock type){
		agentsMap.blocksOfAgent[myX-1][myY].myType = type;
	}
	
	public void setTypeOfRightAgentBlock(TypeOfAgentsBlock type){
		agentsMap.blocksOfAgent[myX+1][myY].myType = type;
	}

	public void setTypeOfDownAgentBlock(TypeOfAgentsBlock type){
		agentsMap.blocksOfAgent[myX][myY-1].myType = type;
	}

	public void setTypeOfUpAgentBlock(TypeOfAgentsBlock type){
		agentsMap.blocksOfAgent[myX][myY+1].myType = type;
	}

	
	private void runState2(){
		if(getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.NOTVISITED){
			goDown();
			if(getTypeOfAgentsDownBlock()!=TypeOfAgentsBlock.NOTVISITED){

				if(getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.NOTVISITED){
					goLeft();

				}else if(getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.NOTVISITED){
					goRight();
				}else{
					goUp();
				}
			}
		}else{
			if(getTypeOfAgentsLeftBlock()==TypeOfAgentsBlock.NOTVISITED){
				goLeft();
			}else if(getTypeOfAgentsRightBlock()==TypeOfAgentsBlock.NOTVISITED){
				goRight();
			}else if(getTypeOfAgentsUpBlock()==TypeOfAgentsBlock.NOTVISITED){
				goUp();
			}else if(getTypeOfAgentsDownBlock()==TypeOfAgentsBlock.NOTVISITED){
				goDown();
			}else{
				System.out.println("state 2 is invaled!");
			}
		}
	}

	private void runState3(){

		runSteps(fixShortCut(getNewPath()));
		runState2();
	}
	
	
	private void runSteps(ArrayList<Coor> path){
		for(int i=0;i<path.size();i++){
			if(path.get(i).step==STEP.LEFT){
				goRight();
				oldMoves.remove(oldMoves.size()-1);
			}else if(path.get(i).step==STEP.RIGHT){
				goLeft();
				oldMoves.remove(oldMoves.size()-1);
			}else if(path.get(i).step==STEP.DOWN){
				goUp();
				oldMoves.remove(oldMoves.size()-1);
			}else if(path.get(i).step==STEP.UP){
				goDown();
				oldMoves.remove(oldMoves.size()-1);
			}
		}
	}
	
	private ArrayList<Coor> fixShortCut(ArrayList<Coor> path){
		
		if(path.size()<=3){
			return path;
		}
		
		for(int i=0;i<path.size()-1;i++){
			for(int j=path.size()-1;j-i>=3;j--){
				if(j<path.size()-1 && areAlike(path.get(i),path.get(j),i,j)){
					System.out.println("found shortcut!");
					removeAmount(path,i,j);
				}
				
				if(j<path.size()-1 && closeToEachOther(path.get(i),path.get(j),i,j)){
					System.out.println("found shortcut beside!");
					for(int k=0;k<path.size();k++){
						System.out.println(path.get(k).x+" "+path.get(k).y+" "+path.get(k).step);
					}
					
					removeLessAmount(path,i,j);
					
					System.out.println("removed");
					for(int k=0;k<path.size();k++){
						System.out.println(path.get(k).x+" "+path.get(k).y+" "+path.get(k).step);
					}
				}
			}
		}
		return path;
	}
	
	private boolean areAlike(Coor a, Coor b, int i, int j){
		return (j-i>=4) && (a.x ==b.x && a.y==b.y);
	}
	
	private boolean closeToEachOther(Coor a, Coor b, int i, int j){
		return (j-i>=3) && ((a.x+1 == b.x && a.y == b.y) || (a.x-1 == b.x && a.y == b.y) || (a.x == b.x && a.y+1 == b.y) || (a.x == b.x && a.y-1 == b.y));
	}
	
	private void removeAmount(ArrayList<Coor> coors,int start, int end){
		for(int i = start; i<end; i++){
			coors.remove(start);
		}
		
	}
	
	private void removeLessAmount(ArrayList<Coor> coors,int start, int end){
		for(int i = start+1; i<end; i++){
			coors.remove(start+1);
		}

		fixDirection(coors,start);
	}
	
	private void fixDirection(ArrayList<Coor> coors,int start){
		if(coors.get(start).x < coors.get(start+1).x){
			coors.get(start).step=STEP.LEFT;
		}
		
		if(coors.get(start).x > coors.get(start+1).x){
			coors.get(start).step=STEP.RIGHT;
		}

		if(coors.get(start).y < coors.get(start+1).y){
			coors.get(start).step=STEP.DOWN;
		}

		if(coors.get(start).y > coors.get(start+1).y){
			coors.get(start).step=STEP.UP;
		}
	}
	
	private ArrayList<Coor> getNewPath(){
		ArrayList<Coor> newPath = new ArrayList<>();
		for(int i=oldMoves.size()-1;i>=0;i--){
			if(getLeftBlockOf(oldMoves.get(i).x,oldMoves.get(i).y).myType==TypeOfAgentsBlock.NOTVISITED){
				return newPath;
			}
			if(getRightBlockOf(oldMoves.get(i).x,oldMoves.get(i).y).myType==TypeOfAgentsBlock.NOTVISITED){
				return newPath;
			}
			if(getUpBlockOf(oldMoves.get(i).x,oldMoves.get(i).y).myType==TypeOfAgentsBlock.NOTVISITED){
				return newPath;
			}
			if(getDownBlockOf(oldMoves.get(i).x,oldMoves.get(i).y).myType==TypeOfAgentsBlock.NOTVISITED){
				return newPath;
			}
			newPath.add(oldMoves.remove(i));
		}
		
		try {
			throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public BlockOfAgent getLeftBlockOf(int x, int y){
		return agentsMap.blocksOfAgent[x-1][y];
	}
	
	public BlockOfAgent getRightBlockOf(int x, int y){
		return agentsMap.blocksOfAgent[x+1][y];
	}
	
	public BlockOfAgent getUpBlockOf(int x, int y){
		return agentsMap.blocksOfAgent[x][y+1];
	}
	
	public BlockOfAgent getDownBlockOf(int x, int y){
		return agentsMap.blocksOfAgent[x][y-1];
	}
	
	private TypeOfAgentsBlock getTypeOfBlockWithAutoLimit(int x, int y){
		if(x>=agentsMap.myWidth){
			x=agentsMap.myWidth-1;
		}else if(x<0){
			x=0;
		}
		if(y>=agentsMap.myLength){
			y=agentsMap.myLength-1;
		}else if(y<0){
			y=0;
		}
		return agentsMap.blocksOfAgent[x][y].myType;
	}
	
	private TypeOfAgentsBlock getTypeOfBlock(int x, int y){
		return agentsMap.blocksOfAgent[x][y].myType;
	}

	public class Coor{
		STEP step;
		int x,y;
		public Coor(int x, int y, STEP step) {
			this.x = x;
			this.y = y;
			this.step = step;
		}
	}
	
	enum STEP{
		UP,DOWN,LEFT,RIGHT
	}
	
	public class MapOfAgent{
		public int myWidth;
		public int myLength;
		public BlockOfAgent[][] blocksOfAgent;
		
		public MapOfAgent(AgentsInterfaceForMap agentsInterface) {
			myWidth = agentsInterface.getWidth()*2;
			myLength = agentsInterface.getLength()*2;
			blocksOfAgent = new BlockOfAgent[myWidth][myLength];
			myX=myWidth/2;
			myY=myLength/2;
			blocksOfAgent[myX][myY] = new BlockOfAgent(myX, myY, TypeOfAgentsBlock.VISITED);
		}
	}
	
	public class BlockOfAgent{
		public int xOfBlock;
		public int yOfBlock;
		public TypeOfAgentsBlock myType;

		public BlockOfAgent(int xOfBlock, int yOfBlock, TypeOfAgentsBlock type) {
			this.xOfBlock = xOfBlock;
			this.yOfBlock = yOfBlock;
			this.myType = type;
		}
	}
	
	enum TypeOfAgentsBlock{
		VISITED,NOTVISITED,UNCHECKEDWALL,CHECKEDWALL
	}
}
