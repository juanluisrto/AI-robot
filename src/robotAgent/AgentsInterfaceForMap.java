package robotAgent;

import environment.Map;
import environment.Block;

public class AgentsInterfaceForMap {
	Map map;
	int xOfAgent;
	int yOfAgent;
	
	public AgentsInterfaceForMap(Map map, int x, int y) {
		this.map = map;
		this.xOfAgent = x;
		this.yOfAgent = y;
		map.xOfAgent=x;
		map.yOfAgent=y;
	}
	
	public void goRight(){
		this.xOfAgent++;
		map.xOfAgent++;
	}
	
	public void goLeft(){
		this.xOfAgent--;
		map.xOfAgent--;
	}
	
	public void goUp(){
		this.yOfAgent++;
		map.yOfAgent++;
	}
	
	public void goDown(){
		this.yOfAgent--;
		map.yOfAgent--;
	}	
	
	public Block.TypeOfBlock seeRight(){
		return map.getRightBlock(xOfAgent, yOfAgent);
	}
	public Block.TypeOfBlock seeLeft(){
		return map.getLeftBlock(xOfAgent, yOfAgent);
	}
	public Block.TypeOfBlock seeUp(){
		return map.getUpBlock(xOfAgent, yOfAgent);
	}
	public Block.TypeOfBlock seeDown(){
		return map.getDownBlock(xOfAgent, yOfAgent);
	}
	public Block.TypeOfBlock seeCurrent(){
		return map.getBlock(xOfAgent, yOfAgent);
	}
	
	public int getWidth(){
		return map.width;
	}
	
	public int getLength(){
		return map.length;
	}
}
