package robotAgent;

import java.io.UncheckedIOException;
import java.util.ArrayList;

import environment.Block.TypeOfBlock;

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
		checkBlocks();
		chooseState();
		
		//while(!ifWon()){
			if(state1IsValid){
				runState1();
				
			}else if(state2IsValid){
				runState2();
				
			}else{
				runState3();
				
			}
		//}
		agentsInterface.map.printOutMap();
	}
	
	private void checkBlocks(){

		agentsInterface.map.printOutMap();
		
		TypeOfBlock typeOfLeftBlock = agentsInterface.seeLeft(); 
		newWallBeside = false;
		newFreeBlock = false;
		
		if(getAgentsLeftBlock()==null){
			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfLeftBlock==TypeOfBlock.WALL){
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
				state1IsValid = true;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX-1][myY] = new BlockOfAgent(myX-1, myY, type);
		}
		
		TypeOfBlock typeOfRightBlock = agentsInterface.seeRight(); 
		
		if(getAgentsRightBlock()==null){
			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfRightBlock==TypeOfBlock.WALL){
				newWallBeside = true;
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX+1][myY] = new BlockOfAgent(myX+1, myY, type);
		}
		
		TypeOfBlock typeOfUpBlock = agentsInterface.seeUp(); 
		
		if(getAgentsUpBlock()==null){
			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfUpBlock==TypeOfBlock.WALL){
				newWallBeside = true;
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX][myY+1] = new BlockOfAgent(myX, myY+1, type);
		}
		
		TypeOfBlock typeOfDownBlock = agentsInterface.seeDown();
	
		if(getAgentsDownBlock()==null){
			TypeOfAgentsBlock type = TypeOfAgentsBlock.NOTVISITED;
			if(typeOfDownBlock==TypeOfBlock.WALL){
				newWallBeside = true;
				type = TypeOfAgentsBlock.UNCHECKEDWALL;
			}else{
				newFreeBlock = true;
				numberOfUnvisitedBlocks++;
			}
			agentsMap.blocksOfAgent[myX][myY-1] = new BlockOfAgent(myX, myY-1, type);
		}
		
	}
	
	private void chooseState(){
		//state of checking if there is wall beside
		if(newWallBeside){
			state1IsValid = true;
			
		}else if(newFreeBlock){
			state2IsValid = true;
			
		}
	}
	
	private boolean state1IsValid = false;
	private boolean state2IsValid=false;
	
	boolean newWallBeside = false;
	boolean newFreeBlock = false;
	
	private int numberOfUnvisitedBlocks=0;
	
	private boolean ifWon(){
		
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
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}

		oldMoves.add(new Coor(myX,myY,STEP.LEFT));
		myX=myX-1;
		agentsInterface.goLeft();
		checkBlocks();
	}
	
	private void goRight(){
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}

		setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
		oldMoves.add(new Coor(myX,myY,STEP.RIGHT));
		myX=myX+1;
		agentsInterface.goRight();
		checkBlocks();
	}
	
	private void goDown(){
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}

		setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
		oldMoves.add(new Coor(myX,myY,STEP.DOWN));
		myY = myY-1;
		agentsInterface.goDown();
		checkBlocks();
	}

	private void goUp(){
		if(getTypeOfAgentsCurrentBlock()==TypeOfAgentsBlock.NOTVISITED){
			setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
			numberOfUnvisitedBlocks--;
		}

		setTypeOfCurrentBlock(TypeOfAgentsBlock.VISITED);
		oldMoves.add(new Coor(myX,myY,STEP.UP));
		myY = myY+1;
		agentsInterface.goUp();	
		checkBlocks();
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
				System.out.println("PROBLEM THAT AI IS TACKED");
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
		
	}

	private void runState3(){
		
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
