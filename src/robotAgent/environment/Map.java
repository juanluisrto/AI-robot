package environment;

public class Map {
	
	public int width;
	public int length;
	
	public int xOfAgent;
	public int yOfAgent;
	
	Block[][] blocks;
	
	public Map(int width, int length) {
		if(width <= 0 || length <= 0){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.length = length;
		this.width = width;
		blocks = new Block[width][length];
		
		for(int i=1;i<width-1;i++){
			for(int j=1;j<length-1;j++){
				blocks[i][j] = new Block(i, j, Block.TypeOfBlock.FREE);
			}
		}
		
		for(int i=0;i<width;i++){
			blocks[i][0] = new Block(i, 0, Block.TypeOfBlock.WALL);
			blocks[i][length-1] = new Block(i, length-1, Block.TypeOfBlock.WALL);
		}
		
		for(int i=1;i<length-1;i++){
			blocks[0][i] = new Block(0, i, Block.TypeOfBlock.WALL);
			blocks[width-1][i] = new Block(width-1, i, Block.TypeOfBlock.WALL);
		}
		
		for(int i=1;i<width-1 && i<length-1;i++){
			blocks[i+1][i] = new Block(i, i, Block.TypeOfBlock.WALL);
		}
	}
	
	public void printOutMap(){
		System.out.println("--------------------------------");
		for(int j=length-1;j>=0;j--){
			for(int i=0;i<width;i++){
				if(i==xOfAgent && j == yOfAgent){
					System.out.print("AGNT"+" ");
				}else{
					System.out.print(blocks[i][j].type+" ");
				}
			}
			System.out.println("\n");
		}
		System.out.println("--------------------------------");
	}
	
	public Block.TypeOfBlock getRightBlock(int x, int y){
		return blocks[x+1][y].type;
	}
	
	public Block.TypeOfBlock getLeftBlock(int x, int y){
		return blocks[x-1][y].type;
	}
	
	public Block.TypeOfBlock getUpBlock(int x, int y){
		return blocks[x][y+1].type;
	}
	
	public Block.TypeOfBlock getDownBlock(int x, int y){
		return blocks[x][y-1].type;
	}
	
	public Block.TypeOfBlock getBlock(int x, int y){
		return blocks[x][y].type;
	}
}
