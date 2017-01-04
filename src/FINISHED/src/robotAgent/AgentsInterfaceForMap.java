package robotAgent;

import environment.Map;

public class AgentsInterfaceForMap {
	Map map;
	int xOfAgent;
	int yOfAgent;
	
	public AgentsInterfaceForMap(Map map, int x, int y) {
		this.map = map;
		this.xOfAgent = x;
		this.yOfAgent = y;
		map.blocks[x][y].visited=true;
		Integer[] pos = { x, y };
		map.visitedBlocks.add(pos);
		map.xOfAgent=x;
		map.yOfAgent=y;
	}
	
	public void goRight(){
		this.xOfAgent++;
		map.xOfAgent++;
		map.blocks[xOfAgent][yOfAgent].visited=true;
		Integer[] pos = { xOfAgent, yOfAgent };
		map.visitedBlocks.add(pos);
	}
	
	public void goLeft(){
		this.xOfAgent--;
		map.xOfAgent--;
		map.blocks[xOfAgent][yOfAgent].visited=true;
		Integer[] pos = { xOfAgent, yOfAgent };
		map.visitedBlocks.add(pos);
	}
	
	public void goUp(){
		this.yOfAgent++;
		map.yOfAgent++;
		map.blocks[xOfAgent][yOfAgent].visited=true;
		Integer[] pos = { xOfAgent, yOfAgent };
		map.visitedBlocks.add(pos);
	}
	
	public void goDown(){
		this.yOfAgent--;
		map.yOfAgent--;
		map.blocks[xOfAgent][yOfAgent].visited=true;
		Integer[] pos = { xOfAgent, yOfAgent };
		map.visitedBlocks.add(pos);

	}	
	
	public Map.TypeOfBlock seeRight(){
		return map.getRightBlock(xOfAgent, yOfAgent);
	}
	public Map.TypeOfBlock seeLeft(){
		return map.getLeftBlock(xOfAgent, yOfAgent);
	}
	public Map.TypeOfBlock seeUp(){
		return map.getUpBlock(xOfAgent, yOfAgent);
	}
	public Map.TypeOfBlock seeDown(){
		return map.getDownBlock(xOfAgent, yOfAgent);
	}
	public Map.TypeOfBlock seeCurrent(){
		return map.getBlock(xOfAgent, yOfAgent);
	}
	
	public int getWidth(){
		return map.width;
	}
	
	public int getLength(){
		return map.length;
	}
}
