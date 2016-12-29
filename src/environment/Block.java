package environment;

public class Block {
	TypeOfBlock type;
	int x;
	int y;
	
	public Block(int x, int y,TypeOfBlock type) {
		this.x=x;
		this.y=y;
		this.type=type;
	}

	public static enum TypeOfBlock{
		WALL, FREE
	}
}

